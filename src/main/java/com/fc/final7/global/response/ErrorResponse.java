package com.fc.final7.global.response;

import com.fc.final7.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final int status;
    private final String code;
    private final String reason;
    private List<FieldError> fieldErrors;

    public ErrorResponse(ErrorCode errorCode, List<FieldError> fieldErrors) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.reason = errorCode.getReason();
        this.fieldErrors = fieldErrors;
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.reason = errorCode.getReason();
        this.fieldErrors = new ArrayList<>();
    }

    public static ErrorResponse of(ErrorCode code){
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(ErrorCode code, List<FieldError> errors){
        return new ErrorResponse(code, errors);
    }

    public static ErrorResponse of(ErrorCode code, BindingResult bindingResult){
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError{
        private String field;
        private String value;
        private String reason;

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }


        public static List<FieldError> of(String field, String value, String reason){
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }


        /**
         @Valid 에서 발견된 FieldError를 BindingResult에 담고 여기에 담긴 모든 FieldError 를 객체로 만든 후, 리스트로 만들어서 변환
         한 번의 요청에 대해 여러 개의 필드에서 발생한 에러 정보를 리스트로 묶어서 한 번에 반환하기 위함
         **/
        private static List<FieldError> of(BindingResult bindingResult){
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream().map(
                            error -> new FieldError(
                                    error.getField(),
                                    error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                                    error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }

    }

}
