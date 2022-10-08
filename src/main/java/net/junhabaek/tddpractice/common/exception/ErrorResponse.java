package net.junhabaek.tddpractice.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"errorCode", "errorMessage", "fieldErrorDetails"})
public class ErrorResponse {

    private String errorMessage;
    @JsonIgnore
    private HttpStatus httpStatus;
    private List<FieldErrorDetail> fieldErrorDetails;
    private String errorCode;


    private ErrorResponse(final ErrorStatus status, final List<FieldErrorDetail> fieldErrorDetails) {
        this.errorMessage = status.getDefaultErrorMessage();
        this.httpStatus = status.getHttpStatus();
        this.fieldErrorDetails = fieldErrorDetails;
        this.errorCode = status.getErrorCode();
    }

    private ErrorResponse(final ErrorStatus status) {
        this.errorMessage = status.getDefaultErrorMessage();
        this.httpStatus = status.getHttpStatus();
        this.errorCode = status.getErrorCode();
        this.fieldErrorDetails = new ArrayList<>();
    }

    public static ErrorResponse of(final ErrorStatus status) {
        return new ErrorResponse(status);
    }

    public static ErrorResponse of(final ErrorStatus status, Collection<ConstraintViolation<?>> constraintViolations){
        List<FieldErrorDetail> extractedDetails =
                constraintViolations.stream()
                        .map((constraintViolation ->
                                new FieldErrorDetail(
                                        extractFieldNameFrom(constraintViolation.getPropertyPath().toString()),
                                        defaultStringIfNull(constraintViolation.getInvalidValue(), "Null"),
                                        defaultStringIfNull(constraintViolation.getMessage(),"No message")
                                )))
                        .collect(Collectors.toList());

        return new ErrorResponse(status, extractedDetails);
    }

    public static ErrorResponse of(final ErrorStatus status, final List<FieldError> errors) {
        List<FieldErrorDetail> extractedDetails =
                errors.stream()
                        .map((error)->
                                new FieldErrorDetail(
                                        defaultStringIfNull(error.getField(), "None"),
                                        defaultStringIfNull(error.getRejectedValue(), "Null"),
                                        defaultStringIfNull(error.getDefaultMessage(), "No message")))
                        .collect(Collectors.toList());

        return new ErrorResponse(status, extractedDetails);
    }

    private static String extractFieldNameFrom(String propertyPath){
        List<String> chunks = Arrays.asList(propertyPath.split("\\."));
        int lastIndex = chunks.size()-1;
        return lastIndex >=0? chunks.get(lastIndex) : "None";
    }

    private static String defaultStringIfNull(Object obj, String defaultStr){
        return obj == null? defaultStr : obj.toString();
    }

    @Data
    @AllArgsConstructor
    public static class FieldErrorDetail{
        private String fieldName;
        private String rejectedValue;
        private String constraintMessage;
    }
}
