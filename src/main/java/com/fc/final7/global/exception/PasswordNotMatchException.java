package com.fc.final7.global.exception;

public class PasswordNotMatchException extends BusinessException{

    public PasswordNotMatchException() {
        super("비밀번호가 일치하지 않습니다", ErrorCode.PASSWORD_NOT_MATCHS);
    }
}
