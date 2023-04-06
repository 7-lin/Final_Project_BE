package com.fc.final7.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode{

    EXIST_MEMBER(400, "EXIST_MEMBER", "동일한 회원이 존재합니다"),
    ACCOUNT_NOT_FOUND(404, "ACCOUNT_NOT_FOUND", "아이디를 찾을 수 없습니다"),
    ROLE_NOT_EXISTS(403, "ROLE_NOT_EXISTS", "접근권한이 없습니다"),
    PASSWORD_NOT_MATCHS(400, "PASSWORD_NOT_MATCHS", "비밀번호를 잘못 입력했습니다."),
    VALIDATION_FIELDS(400, "VALIDATION_FIELDS", "필수 값을 입력해주세요"),

    PATTERN_NOT_MATCH(400, "PATTERN_NOT_MATCH", "정해진 형식에 맞게 입력해주세요"),
    ENTITY_NOT_FOUND(404, "ENTITY_NOT_FOUND", "해당하는 아이디가 존재하지 않습니다"),
    INTERNAL_SERVER_ERROR(500, "SERVER-ERROR", "Server Error"),
    NULL_OR_TYPE_MISS_MATCH(400, "NULL_OR_TYPE_MISS_MATCH", "파라미터가 null 이거나 타입이 맞지 않습니다."),
    NULL_ACCESS_REFRESH_TOKEN(401, "Unauthorized", "토큰 기간이 만료되었습니다."),
    NULL_POINTER_EXCEPTION(500, "NULL_POINTER", "NULL_POINTER_EXCEPTION 오류"),

    PRODUCT_NOT_FOUND(404, "PRODUCT-001", "존재하지 않는 상품입니다."),
    REVIEW_PASSWORD_MISMATCH(401, "REVIEW-001", "리뷰 비밀번호 불일치한 경우"),
    REVIEW_NOT_FOUND(404, "REVIEW-002", "리뷰가 없는 경우"),
    COMMENT_PASSWORD_MISMATCH(401, "COMMENT-001", "댓글 비밀번호 불일치한 경우"),
    UNUSUAL_ACCESS_ROUTE(403, "RESERVATION_001", "허가되지 않는 사람이 예약상세 페이지에 접근한 경우");




    private final int status;
    private final String code;
    private final String reason;

    ErrorCode(int status, String code, String reason) {
        this.status = status;
        this.code = code;
        this.reason = reason;
    }


}
