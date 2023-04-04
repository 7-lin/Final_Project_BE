package com.fc.final7.global.exception;

import com.fc.final7.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    protected ResponseEntity<ErrorResponse> handleDuplicateEmailException(DuplicateEmailException e){
        log.error(e.getMessage(), e.getStackTrace());
        ErrorResponse response = ErrorResponse.of(ErrorCode.DUPLICATE_ID);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpirationException.class)
    protected ResponseEntity<ErrorResponse> handleTokenExpirationException(TokenExpirationException e){
        log.error(e.getMessage(), e.getStackTrace());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.TOKEN_NOT_EXISTS);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSearchProductException.class)
    protected ResponseEntity<ErrorResponse> handleNoSearchProductException(NoSearchProductException e) {
        log.error(e.getMessage(), e.getStackTrace());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.PRODUCT_NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewPasswordMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleReviewPasswordMismatchException(ReviewPasswordMismatchException e) {
        log.error(e.getMessage(), e.getStackTrace());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.REVIEW_PASSWORD_MISMATCH);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleReviewNotFoundException(ReviewNotFoundException e) {
        log.error(e.getMessage(), e.getStackTrace());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.REVIEW_NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
