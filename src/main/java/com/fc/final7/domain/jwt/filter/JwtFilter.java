package com.fc.final7.domain.jwt.filter;

import com.fc.final7.domain.jwt.JwtProperties;
import com.fc.final7.domain.jwt.JwtProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Getter
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String token = extractBearerToken(request);
        if (StringUtils.hasText(token)){
            SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(token));  // 현재 사용자 설정
        }
    }

    public String extractBearerToken(HttpServletRequest request){
        String BearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(BearerToken) && BearerToken.startsWith(jwtProperties.getTokenPrefix())){
           return BearerToken.substring(jwtProperties.getTokenPrefix().length());
        }
        return null;
    }
}
