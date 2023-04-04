package com.fc.final7.global.exception;

public class ReviewNotFoundException extends BusinessException{

    public ReviewNotFoundException() {
        super("리뷰를 찾을 수 없습니다.", ErrorCode.REVIEW_NOT_FOUND);
    }
}
