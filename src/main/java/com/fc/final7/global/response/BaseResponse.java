package com.fc.final7.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@ToString

public class BaseResponse<T> {

    private HttpStatus httpStatus;
    private String message;
    private Integer dataSize;
    private T data;
    private T response;

    public BaseResponse(Integer dataSize, String message, T data) {
        this.httpStatus = OK;
        this.dataSize = dataSize;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(String message, Integer dataSize, T data, T response) {
        this.httpStatus = OK;
        this.message = message;
        this.dataSize = dataSize;
        this.data = data;
        this.response = response;
    }

    public static <T>BaseResponse<T> of(Integer dataSize, String message, T data){
        return new BaseResponse<>(dataSize, message, data);
    }

    public static <T> BaseResponse<T> ofToken(String message, Integer dataSize, T data, T response){
        return new BaseResponse<>(message, dataSize, data, response);
    }
}
