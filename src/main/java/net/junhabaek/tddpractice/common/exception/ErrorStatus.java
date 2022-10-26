package net.junhabaek.tddpractice.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorStatus {

    // Common
    METHOD_NOT_SUPPORTED("A01", HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed"),
    INVALID_TYPE("A02", HttpStatus.BAD_REQUEST, "Invalid Type"),
    INVALID_INPUT_VALUE("A03", HttpStatus.BAD_REQUEST, "Invalid Input"),
    ENTITY_NOT_FOUND("A04", HttpStatus.BAD_REQUEST, "Entity Not Found"),
    INTERNAL_SERVER_ERROR("A05", HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected Server Error"),
    UNAUTHORIZED_RESOURCE("A06", HttpStatus.BAD_REQUEST, "Cant' Access Unauthorized Resource to current user"),
    NOT_CLASSIFIED_BUSINESS_ERROR("A99", HttpStatus.BAD_REQUEST, "Unclassified Business Error. Please check your request.");
    ;


    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String defaultErrorMessage;

    ErrorStatus(final String errorCode, final HttpStatus httpStatus,  final String defaultErrorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.defaultErrorMessage = defaultErrorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getDefaultErrorMessage() {
        return this.defaultErrorMessage;
    }

}

