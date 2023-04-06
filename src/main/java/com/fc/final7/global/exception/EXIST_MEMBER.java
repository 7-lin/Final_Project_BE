package com.fc.final7.global.exception;

public class EXIST_MEMBER extends BusinessException{

    public EXIST_MEMBER() {
        super("동일한 회원이 존재합니다", ErrorCode.EXIST_MEMBER);
    }
}
