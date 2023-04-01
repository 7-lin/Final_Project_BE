package com.fc.final7.global.exception;

public class DuplicateEmailException extends BusinessException{

    public DuplicateEmailException() {
        super("같은 이메일이 존재합니다", ErrorCode.DUPLICATE_ID);
    }
}
