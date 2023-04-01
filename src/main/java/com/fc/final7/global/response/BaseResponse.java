package com.fc.final7.global.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@NoArgsConstructor
@Getter
@Setter
@ToString

public class BaseResponse<T> {

    private HttpStatus httpStatus;
    private String message;
    private Integer dataSize;
    private T data;

    public BaseResponse(Integer dataSize, String message, T data) {
        this.httpStatus = OK;
        this.dataSize = dataSize;
        this.message = message;
        this.data = data;
    }

    public static <T>BaseResponse<T> of(Integer dataSize, String message, T data){
        return new BaseResponse<>(dataSize, message, data);
    }
}
