package com.fc.final7.global.exception;


public class ReviewPasswordMismatchException extends BusinessException{

    public ReviewPasswordMismatchException() {
        super("리뷰 비밀번호가 일치하지 않습니다.", ErrorCode.REVIEW_PASSWORD_MISMATCH);
    }
}
