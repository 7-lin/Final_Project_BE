package com.fc.final7.global.exception;

public class TokenExpirationException extends BusinessException {


    public TokenExpirationException() {
        super("토큰의 유효기간이 만료되었습니다.", ErrorCode.TOKEN_NOT_EXISTS);
    }

}
