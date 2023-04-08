package com.fc.final7.global.exception;

import com.fc.final7.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(org.springframework.web.bind.MethodArgumentNotValidException e){
        log.error("handleMethodArgumentNotValidException", e);
        ErrorResponse response = ErrorResponse.of(ErrorCode.VALIDATION_FIELDS, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EXIST_MEMBER.class)
    protected ResponseEntity<ErrorResponse> handleDuplicateEmailException(EXIST_MEMBER e){
        log.error("handleDuplicateEmailOrPhoneException", e);
        ErrorResponse response = ErrorResponse.of(ErrorCode.EXIST_MEMBER);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpirationException.class)
    protected ResponseEntity<ErrorResponse> handleTokenExpirationException(TokenExpirationException e){
        log.error("handleTokenExpirationException", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NULL_ACCESS_REFRESH_TOKEN);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    protected ResponseEntity<ErrorResponse> handlePasswordNotMatchException(PasswordNotMatchException e){
        log.error("handlePasswordNotMatchException", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.PASSWORD_NOT_MATCHS);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handlePatternNotMatchException(IllegalArgumentException e){
        log.error("handlePatternNotMatchException", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.PATTERN_NOT_MATCH);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("handleEntityNotFoundException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.ENTITY_NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.NULL_OR_TYPE_MISS_MATCH);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e) {
        log.error("handleNullPointerException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.NULL_POINTER_EXCEPTION);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CommentPasswordMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleReviewNotFoundException(CommentPasswordMismatchException e) {
        log.error(e.getMessage(), e.getStackTrace());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.COMMENT_PASSWORD_MISMATCH);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnusualAccessRouteException.class)
    protected ResponseEntity<ErrorResponse> handleUnusualAccessRouteException(UnusualAccessRouteException e) {
        log.error(e.getMessage(), e.getStackTrace());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.UNUSUAL_ACCESS_ROUTE);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoSearchReservationException.class)
    protected ResponseEntity<ErrorResponse> handleNoSearchReservationException(NoSearchReservationException e) {
        log.error(e.getMessage(), e.getStackTrace());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.RESERVATION_NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
