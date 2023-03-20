package com.fc.final7.global.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class globalSuccessDTO<T> {

    private HttpStatus httpStatus;
    private String message;
    private Integer dataSize;
    private T data;

    public globalSuccessDTO(Integer dataSize, String message, T data) {
        this.httpStatus = OK;
        this.dataSize = dataSize;
        this.message = message;
        this.data = data;
    }
}
