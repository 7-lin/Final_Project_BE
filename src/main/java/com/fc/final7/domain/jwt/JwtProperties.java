package com.fc.final7.domain.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
//@ConfigurationProperties(prefix = "jwt")   // 프로퍼티 설정클래스, 외부 설정파일을 바인딩
public class JwtProperties {

    private String secretKey;

    private Long accessTokenValidTime;

    private Long refreshTokenValidTime;

    private String issuer;

    private String tokenPrefix;

    private String header;

    @Value("${jwt.secret}")
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Value("${jwt.token-validity-in-seconds}")
    public void setAccessTokenValidTime(Long accessTokenValidTime) {
        this.accessTokenValidTime = accessTokenValidTime;
    }

    @Value("${jwt.refresh-token-validity-in-seconds}")
    public void setRefreshTokenValidTime(Long refreshTokenValidTime) {
        this.refreshTokenValidTime = refreshTokenValidTime;
    }

    @Value("${jwt.jwt-issuer}")
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    @Value("${jwt.token-start-with} ")
    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    @Value("${jwt.header}")
    public void setHeader(String header) {
        this.header = header;
    }

}

