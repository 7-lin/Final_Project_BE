package com.fc.final7.domain.jwt;

import com.fc.final7.domain.jwt.dto.TokenDto;
import com.fc.final7.domain.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    private final JwtProperties jwtProperties;

    private final UserDetailsService userDetailsService;
    private final RedisTemplate<String, String> redisTemplate;


    public String createAccessToken(Member member) {
        Claims claims = setTokenClaims(member)
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenValidTime()));

        String accessToken = buildToken(claims);

        String accessTokenKey = "accessToken" + member.getId();
        redisTemplate.opsForValue().set(accessTokenKey, accessToken, jwtProperties.getAccessTokenValidTime(), TimeUnit.SECONDS);
        return accessToken;
    }


    public String createRefreshToken(Member member) {
        Claims claims = setTokenClaims(member)
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenValidTime()));

        String refreshToken = buildToken(claims);

        String refreshTokenKey = "refreshToken" + member.getId();
        redisTemplate.opsForValue().set(refreshTokenKey, refreshToken, jwtProperties.getRefreshTokenValidTime(), TimeUnit.SECONDS);
        return refreshToken;
    }

    public TokenDto issue(Member member) {
        Date date = new Date();
        String accessToken = createAccessToken(member);
        String refreshToken = createRefreshToken(member);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessStartTime(String.valueOf(date))
                .accessExpirationTime(getExpiration(accessToken))
                .refreshExpirationTime(getExpiration(refreshToken))
                .build();
    }


    public TokenDto reIssue(String accessToken, String refreshToken, Member member) {
        // 둘 중 하나가 만료된 경우, 둘 다 재발급해서 업데이트
        boolean validRefreshToken = validRefreshToken(refreshToken);
        boolean validAccessToken = validAccessToken(accessToken);

        if (validAccessToken == false || validRefreshToken == false){
            String createdAccessToken = createAccessToken(member);
            String createdRefreshToken = createRefreshToken(member);
            Date date = new Date();

            return TokenDto.builder()
                    .accessToken(createdAccessToken)
                    .refreshToken(createdRefreshToken)
                    .accessStartTime(String.valueOf(date))
                    .accessExpirationTime(getExpiration(accessToken))
                    .refreshExpirationTime(getExpiration(refreshToken))
                    .build();
        }
        return null;
    }

    public String getRefreshTokenKey(String refreshToken) {
        return jwtProperties.getTokenPrefix() + refreshToken;
    }

    public String getAccessTokenKey(String accessToken) {
        return jwtProperties.getTokenPrefix() + accessToken;
    }

    public boolean validRefreshToken(String refreshToken) {
        String refreshTokenKey = getRefreshTokenKey(refreshToken);
        return redisTemplate.hasKey(refreshTokenKey);  // key가 살아있으면 true
    }

    public boolean validAccessToken(String accessToken) {
        String accessTokenKey = getAccessTokenKey(accessToken);
        return redisTemplate.hasKey(accessTokenKey);
    }

    public void deleteRefreshToken(String refreshToken) {
        String refreshTokenKey = getRefreshTokenKey(refreshToken);
        redisTemplate.delete(refreshTokenKey);
    }

    public void deleteAccessToken(String accessToken) {
        String refreshTokenKey = getAccessTokenKey(accessToken);
        redisTemplate.delete(refreshTokenKey);
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

    public String getExpiration(String token) {
        return String.valueOf(getClaimsFromToken(token).getExpiration());
    }


    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getSubjectFromToken(token));  // 사용자 정보
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());  // 사용자 객체
    }


    private String buildToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .compact();
    }

    public Claims setTokenClaims(Member member) {
        return Jwts.claims()
                .setSubject(member.getEmail())
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date());
    }

}



