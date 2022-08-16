package net.junhabaek.tddpractice.common.exception;

public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException(String message) {
        super(message, ErrorStatus.ENTITY_NOT_FOUND);
    }
}
