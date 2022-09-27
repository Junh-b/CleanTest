package net.junhabaek.tddpractice.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"errorCode", "errorMessage", "errors"})
public class ErrorResponse {

    private String errorMessage;
    @JsonIgnore
    private HttpStatus httpStatus;
    private List<FieldError> errors;
    //TODO 사실, 이 프로젝트에서 사용할 FieldError를 따로 정의하는게 더 적절. 이미 만들어진 FieldError는 너무 많은 정보 포함
    private String errorCode;


    private ErrorResponse(final ErrorStatus status, final List<FieldError> errors) {
        this.errorMessage = status.getDefaultErrorMessage();
        this.httpStatus = status.getHttpStatus();
        this.errors = errors;
        this.errorCode = status.getErrorCode();
    }

    private ErrorResponse(final ErrorStatus status) {
        this.errorMessage = status.getDefaultErrorMessage();
        this.httpStatus = status.getHttpStatus();
        this.errorCode = status.getErrorCode();
        this.errors = new ArrayList<>();
    }

    public static ErrorResponse of(final ErrorStatus status, final BindingResult bindingResult) {
        return new ErrorResponse(status, bindingResult.getFieldErrors());
    }

    public static ErrorResponse of(final ErrorStatus status) {
        return new ErrorResponse(status);
    }

    public static ErrorResponse of(final ErrorStatus status, final List<FieldError> errors) {
        return new ErrorResponse(status, errors);
    }

}
