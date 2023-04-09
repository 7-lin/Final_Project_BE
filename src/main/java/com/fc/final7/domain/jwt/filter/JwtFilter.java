package com.fc.final7.domain.jwt.filter;

import com.fc.final7.domain.jwt.JwtProperties;
import com.fc.final7.domain.jwt.JwtProvider;
import com.fc.final7.global.exception.TokenExpirationException;
import com.fc.final7.global.redis.RedisService;
import io.jsonwebtoken.IncorrectClaimException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

import static com.fc.final7.global.exception.ErrorCode.NULL_ACCESS_NOT_LOGIN;

@Slf4j
@RequiredArgsConstructor
@Getter
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = extractBearerToken(request);

       /* if (token != null && redisService.getValues(token).equals("logout")) {
            SecurityContextHolder.clearContext();
            log.debug("JWT token blacklisted.");
            response.sendError(403, "블랙리스트에 등록된 JWT 토큰입니다.");
        }*/

        try {
            if (token != null && jwtProvider.validate(token)) {   // 토큰 유효 검증
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Save authentication in SecurityContextHolder.");
            }
        } catch (IncorrectClaimException e) {
            SecurityContextHolder.clearContext();
            log.debug("Invalid JWT token.");
            response.sendError(403, "토큰 정보가 일치하지 않습니다.");
        } catch (UsernameNotFoundException e) {
            SecurityContextHolder.clearContext();
            log.debug("Can't find user.");
            response.sendError(403, "해당 회원을 찾을 수 없습니다.");
        }
            filterChain.doFilter(request, response);
    }

    public String extractBearerToken(HttpServletRequest request){
        String BearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(BearerToken) && BearerToken.startsWith(jwtProperties.getTokenPrefix())){
           return BearerToken.substring(jwtProperties.getTokenPrefix().length());
        }
        return null;
    }
}
