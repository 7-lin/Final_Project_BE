package com.fc.final7.global.exception;

public class UnusualAccessRouteException extends BusinessException{


    public UnusualAccessRouteException() {
        super("비정상적인 접근경로 입니다.", ErrorCode.UNUSUAL_ACCESS_ROUTE);
    }
}
