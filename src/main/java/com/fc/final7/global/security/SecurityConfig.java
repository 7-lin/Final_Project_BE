package com.fc.final7.global.security;

import com.fc.final7.domain.jwt.JwtProperties;
import com.fc.final7.domain.jwt.JwtProvider;
import com.fc.final7.domain.jwt.filter.JwtFilter;
import com.fc.final7.domain.jwt.handler.JwtAccessDeniedHandler;
import com.fc.final7.domain.jwt.handler.JwtAuthenticationEntryPoint;
import com.fc.final7.domain.member.oauth.service.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private String[] whiteList = {"/", "/**", "/login", "/signUp", "/signUp/checkId", "/sendEmail"};

    private final CustomOauth2UserService customOauth2UserService;
    private final JwtProperties jwtProperties;
    private final JwtProvider jwtProvider;
//    private final Oauth2SuccessHandler oauth2SuccessHandler;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 정적 리소스들
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)

                .and()
                .addFilterBefore(
                        new JwtFilter(jwtProvider, jwtProperties),
                        UsernamePasswordAuthenticationFilter.class
                )
                .authorizeRequests().antMatchers(
                        whiteList
                ).permitAll()
                .anyRequest().authenticated()

                // x-frame-options 공격 방어
                .and()
                .headers()
                .frameOptions().sameOrigin()

                .and()
                .logout()
                .logoutSuccessUrl("/")

                .and()
                .oauth2Login()
//                .successHandler(oauth2SuccessHandler)
//                .loginProcessingUrl("")
                .userInfoEndpoint()
                .userService(customOauth2UserService);



        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("https://gotogether-7lin.netlify.app");
        configuration.addAllowedOrigin("http://127.0.0.1:5173");
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
