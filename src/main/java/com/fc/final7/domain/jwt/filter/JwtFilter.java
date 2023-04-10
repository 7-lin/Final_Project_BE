package com.fc.final7.domain.jwt.filter;

import com.fc.final7.domain.jwt.JwtProperties;
import com.fc.final7.domain.jwt.JwtProvider;
import com.fc.final7.global.exception.ForbiddenToken;
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
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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


        try {
            if (token != null && redisService.getValues(token) != null && redisService.getValues(token).equals("logout")) {
                log.debug("JWT token blacklisted.");
                SecurityContextHolder.clearContext();
                throw new ForbiddenToken();
            } else if (token != null && jwtProvider.validate(token)) {   // 토큰 유효 검증
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Save authentication in SecurityContextHolder.");
            }
        } catch (IncorrectClaimException e) {
            SecurityContextHolder.clearContext();
            log.debug("Invalid JWT token.");
//            response.sendError(403, "토큰 정보가 일치하지 않습니다.");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("토큰 정보가 일치하지 않습니다.");
        } catch (UsernameNotFoundException e) {
            SecurityContextHolder.clearContext();
            log.debug("Can't find user.");
//            response.sendError(403, "해당 회원을 찾을 수 없습니다.");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("403, \"해당 회원을 찾을 수 없습니다.");
        }
        filterChain.doFilter(request, response);
    }

    public String extractBearerToken(HttpServletRequest request) {
        String BearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(BearerToken) && BearerToken.startsWith(jwtProperties.getTokenPrefix())) {
            return BearerToken.substring(jwtProperties.getTokenPrefix().length());
        }
        return null;
    }
}
