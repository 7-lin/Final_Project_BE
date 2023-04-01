package com.fc.final7.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode{

    DUPLICATE_ID(400, "ACCOUNT-001", "계정명이 중복된 경우"),
    ACCOUNT_NOT_FOUND(404, "ACCOUNT-003", "계정을 찾을 수 없는 경우"),
    ROLE_NOT_EXISTS(403, "ACCOUNT-004", "권한이 부족한 경우"),
    TOKEN_NOT_EXISTS(404, "ACCOUNT-005", "해당 key의 인증 토큰이 존재하지 않는 경우"),
    PRODUCT_NOT_FOUND(404, "PRODUCT-001", "존재하지 않는 상품입니다.");


    private final int status;
    private final String code;
    private final String reason;

    ErrorCode(int status, String code, String reason) {
        this.status = status;
        this.code = code;
        this.reason = reason;
    }


}
