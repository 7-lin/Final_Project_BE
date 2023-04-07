package com.fc.final7.domain.jwt.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDto {

    // 엑세스 토큰과 리프레쉬 토큰을 만들어서 사용자에게 넘겨주는 클래스
    private String accessToken;
    private String refreshToken;
    private String accessExpirationTime;
    private String refreshExpirationTime;

    public TokenDto(String accessToken, String refreshToken, String accessExpirationTime, String refreshExpirationTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

}
