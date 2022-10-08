package net.junhabaek.tddpractice.common.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
@Slf4j
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
    //  Spring, Validation Exceptions
    //

    // 현재 프로젝트 기준으로는 Validation 관련 Exception이 서비스 단에서 발생하는 ConstraintViolationException이지만,
    // Controller의 메서드 검증에 사용할 때에는 BindException이 발생한다.
    // 서비스에 검증 기능이 담겨 있더라도, 더 빠른 응답을 위해 Controller에 중복 검증 로직을 넣을 수도 있으므로 Handler 코드 남겨둠
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorStatus.INVALID_INPUT_VALUE, e.getBindingResult().getFieldErrors());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e){

        final ErrorResponse response = ErrorResponse.of(ErrorStatus.INVALID_INPUT_VALUE, e.getConstraintViolations());

        return new ResponseEntity<>(response,response.getHttpStatus());
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

    /*
        Not Handled By Upper Exceptions
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        final ErrorResponse response = ErrorResponse.of(ErrorStatus.INTERNAL_SERVER_ERROR);
        log.info(e.getMessage());
        // TODO 개발 모드일 경우, 여기서 로그 출력시키는게 맞다.
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
