package com.fc.final7.domain.jwt;

import com.fc.final7.domain.jwt.dto.TokenDto;
import com.fc.final7.domain.member.entity.Member;
import com.fc.final7.domain.member.repository.MemberRepository;
import com.fc.final7.domain.member.service.MemberDetailsServiceImpl;
import com.fc.final7.global.redis.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Getter
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final MemberDetailsServiceImpl memberDetailsService;
    private final RedisService redisService;
    private final MemberRepository memberRepository;

    private static final String EMAIL_KEY = "email";
    private static final String AUTHORITIES_KEY = "ROLE";


    public TokenDto createToken(String email, String authorities) {
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject("access-token")
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date());

        String accessToken = Jwts.builder().setClaims(claims)
                .claim(EMAIL_KEY, email)
                .claim(AUTHORITIES_KEY, authorities)
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenValidTime()))
                .compact();

        String refreshToken = Jwts.builder().
                claim(EMAIL_KEY, email)
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .setExpiration(new Date(now.getTime() + jwtProperties.getRefreshTokenValidTime()))
                .setSubject("refresh-token")
                .compact();

        return new TokenDto(accessToken, refreshToken, String.valueOf(jwtProperties.getAccessTokenValidTime()), String.valueOf(jwtProperties.getRefreshTokenValidTime()));
    }


    @Transactional
    public void saveRefreshToken(String email, String refreshToken) {
        redisService.setValuesWithTimeout("RT : " + email, refreshToken, getTokenExpirationTime(refreshToken), TimeUnit.MICROSECONDS);
    }


    @Transactional
    public TokenDto reissue(String requestAccessTokenInHeader, String requestRefreshToken) {

        String token = resolveToken(requestAccessTokenInHeader);
        String email = getClaimsFromToken(token).get("email", String.class);
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);

        Authentication authentication = getAuthentication(token);

        String refreshTokenInRedis = redisService.getValues("RT : " + member.getEmail());
        if (refreshTokenInRedis == null) { // Redis에 저장되어 있는 RT가 없을 경우
            return null; // -> 재로그인 요청
        }

        // 요청된 RT의 유효성 검사 & Redis에 저장되어 있는 RT와 같은지 비교
       /* if (!validateRefreshToken(requestRefreshToken) || !refreshTokenInRedis.equals(requestRefreshToken)) {
            redisService.deleteValues("RT :" + member.getEmail()); // 탈취 가능성 -> 삭제
            return null; // -> 재로그인 요청
        }*/

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authorities = getAuthorities(authentication);

        // 토큰 재발급 및 Redis 업데이트
        redisService.deleteValues("RT : " + member.getEmail()); // 기존 RT 삭제
        TokenDto tokenDto = createToken(member.getEmail(), authorities);
        saveRefreshToken(member.getEmail(), tokenDto.getRefreshToken());
        return tokenDto;
    }

    public boolean validate(String requestAccessTokenInHeader) {
        String requestAccessToken = resolveToken(requestAccessTokenInHeader);
        return validateAccessToken(requestAccessToken);
    }

    // "Bearer {AT}"에서 {AT} 추출
    public String resolveToken(String requestAccessTokenInHeader) {
        if (requestAccessTokenInHeader != null && requestAccessTokenInHeader.startsWith(jwtProperties.getTokenPrefix())) {
            return requestAccessTokenInHeader.substring(jwtProperties.getTokenPrefix().length());
        }
        return null;
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            return getClaimsFromToken(accessToken)
                    .getExpiration()
                    .before(new Date());   // 만료된 경우 true
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        if (redisService.getValues(getSubjectFromToken(refreshToken)) != null) {
            return true;
        }
        return false;
    }

    @Transactional
    public TokenDto generateToken(String email, String authorities) {
        // RT가 이미 있을 경우
        if (redisService.getValues("RT : " + email) != null) {
            redisService.deleteValues("RT : " + email); // 삭제
        }

        // AT, RT 생성 및 Redis에 RT 저장
        TokenDto tokenDto = createToken(email, authorities);
        saveRefreshToken(email, tokenDto.getRefreshToken());
        return tokenDto;
    }

    // 권한 이름 가져오기
    public String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }


    public String getSubjectFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


/*    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(jwtProperties.getSecretKey())).build()
                .parseClaimsJws(token).getBody();
    }

    public String getSubjectFromToken(String token) {
       return Jwts.parserBuilder()
                .setSigningKey(getKey(jwtProperties.getSecretKey())).build()
                .parseClaimsJwt(token).getBody().getSubject();
    }*/

    public long getTokenExpirationTime(String token) {
        return getClaimsFromToken(token).getExpiration().getTime();
    }

    public Authentication getAuthentication(String token) {
        String email = getClaimsFromToken(token).get(EMAIL_KEY).toString();
        UserDetails userDetails = memberDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());  // 사용자 객체
    }

    public String getPrincipal(String requestAccessToken) {
        return getAuthentication(requestAccessToken).getPrincipal().toString();
    }

}