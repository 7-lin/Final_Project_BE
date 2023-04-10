package com.fc.final7.global.exception;

public class ForbiddenToken extends BusinessException{
    public ForbiddenToken() {
        super("접근이 금지되었습니다", ErrorCode.FORBIDDEN);
    }
}
