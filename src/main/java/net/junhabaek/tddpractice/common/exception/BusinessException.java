package net.junhabaek.tddpractice.common.exception;

public class BusinessException extends RuntimeException{
    private ErrorStatus errorStatus;

    protected BusinessException(String message, ErrorStatus errorStatus){
        super(message);
        this.errorStatus=errorStatus;
    }

    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }
}
