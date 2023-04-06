package com.fc.final7.global.exception;

public class CommentPasswordMismatchException extends BusinessException {

    public CommentPasswordMismatchException() {
        super("댓글 비밀번호가 일치하지 않습니다.", ErrorCode.COMMENT_PASSWORD_MISMATCH);
    }
}
