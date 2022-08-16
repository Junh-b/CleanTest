package net.junhabaek.tddpractice.common.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@ControllerAdvice
public class BasicExceptionHandler {

    //
    //  PROJECT UNIQUE EXCEPTIONS
    //

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e){
        ErrorStatus status = ErrorStatus.ENTITY_NOT_FOUND;
        ErrorResponse response = ErrorResponse.of(status);
        return new ResponseEntity<>(response, status.getHttpStatus());
    }

    @ExceptionHandler(StateInvalidException.class)
    protected ResponseEntity<ErrorResponse> handleStateInvalidException(StateInvalidException e){
        ErrorStatus status = ErrorStatus.INVALID_INPUT_VALUE;
        ErrorResponse response = ErrorResponse.of(status);
        return new ResponseEntity<>(response, status.getHttpStatus());
    }
    // END OF PROJECT UNIQUE EXCEPTIONS


    //
    //  SPRING BUILTIN EXCEPTIONS
    //

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorStatus.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorStatus.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ErrorStatus errorStatus = ErrorStatus.INVALID_TYPE;

        String mismatchedValue = e.getValue() != null ? e.getValue().toString() : "";

        List<FieldError> errors = List.of(new FieldError(e.getName(), mismatchedValue, e.getErrorCode()));

        ErrorResponse response = ErrorResponse.of(errorStatus, errors);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorStatus.METHOD_NOT_SUPPORTED);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    // END OF BUILTIN EXCEPTIONS

    //
    //  NOT HANDLED BY UPPER
    //
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        final ErrorResponse response = ErrorResponse.of(ErrorStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
