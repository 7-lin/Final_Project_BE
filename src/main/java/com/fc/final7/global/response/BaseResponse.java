package com.fc.final7.global.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@NoArgsConstructor
@Getter
@Setter
@ToString
<<<<<<< HEAD:src/main/java/com/fc/final7/global/response/BaseResponse.java
public class BaseResponse<T> {
=======
public class GlobalSuccessDTO<T> {
>>>>>>> 54c0b2c (rename: 글로벌 DTO 이름변경):src/main/java/com/fc/final7/global/response/GlobalSuccessDTO.java

    private HttpStatus httpStatus;
    private String message;
    private Integer dataSize;
    private T data;

<<<<<<< HEAD:src/main/java/com/fc/final7/global/response/BaseResponse.java
    public BaseResponse(Integer dataSize, String message, T data) {
=======
    public GlobalSuccessDTO(Integer dataSize, String message, T data) {
>>>>>>> 54c0b2c (rename: 글로벌 DTO 이름변경):src/main/java/com/fc/final7/global/response/GlobalSuccessDTO.java
        this.httpStatus = OK;
        this.dataSize = dataSize;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> of(Integer dataSize, String message, T data){
        return new BaseResponse<>(dataSize, message, data);
    }
}
