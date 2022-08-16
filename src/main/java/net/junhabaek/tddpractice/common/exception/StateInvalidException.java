package net.junhabaek.tddpractice.common.exception;

public class StateInvalidException extends BusinessException{
    public StateInvalidException(String message) {
        super(message, ErrorStatus.INVALID_INPUT_VALUE);
    }
}

