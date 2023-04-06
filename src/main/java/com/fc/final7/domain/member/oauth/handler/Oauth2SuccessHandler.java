package com.fc.final7.domain.member.oauth.handler;//package com.fc.final7.domain.member.oauth.handler;
//
//import com.fc.final7.domain.jwt.JwtProvider;
//import com.fc.final7.domain.jwt.dto.TokenDto;
//import com.fc.final7.domain.member.entity.Member;
//import com.fc.final7.global.redis.RedisService;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Date;
//
//@Component
//@RequiredArgsConstructor
//public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    private final JwtProvider jwtProvider;
//    private final RedisService redisService;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        jwtProvider.
//
//        Member member = (Member) authentication.getPrincipal();
//        String accessToken = jwtProvider.createAccessToken(member);
//        String refreshToken = jwtProvider.createRefreshToken(member);
//        Date date = new Date();
//
//        TokenDto tokenDto = TokenDto.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .accessStartTime(String.valueOf(date))
//                .accessExpirationTime(jwtProvider.getExpiration(accessToken))
//                .refreshExpirationTime(jwtProvider.getExpiration(refreshToken))
//                .build();
//
//        response.setHeader("Authorization" , String.valueOf(tokenDto));
//    }
//}
//
