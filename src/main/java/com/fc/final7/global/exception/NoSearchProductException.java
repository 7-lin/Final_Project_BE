package com.fc.final7.global.exception;

public class NoSearchProductException extends BusinessException{


    public NoSearchProductException() {
        super("존재하지 않는 상품입니다.", ErrorCode.PRODUCT_NOT_FOUND);
    }
}
