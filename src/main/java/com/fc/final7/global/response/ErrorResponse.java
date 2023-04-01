package com.fc.final7.global.response;

import com.fc.final7.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final int status;
    private final String code;
    private final String reason;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.reason = errorCode.getReason();
    }

    public static ErrorResponse of(ErrorCode code){
        return new ErrorResponse(code);
    }
}
