package net.junhabaek.tddpractice.book.application.port.in;

import lombok.Builder;
import lombok.Getter;
import net.junhabaek.tddpractice.common.validation.ConstraintMessageTemplate;
import net.junhabaek.tddpractice.common.validation.constraint.DistinguishableName;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public abstract class BookCommand {
    @Getter
    @Builder
    public static class RegisterBook {
        @DistinguishableName(message = "bookName" + ConstraintMessageTemplate.DISTINGUISHABLE_NAME_TEMPLATE)
        @Size(min = 1, max = 255, message = "bookName" + ConstraintMessageTemplate.SIZE_MESSAGE_TEMPLATE)
        @NotNull(message = "bookName" + ConstraintMessageTemplate.NOTNULL_MESSAGE_TEMPLATE)
        private final String bookName;

        @DistinguishableName(message = "authorName" + ConstraintMessageTemplate.DISTINGUISHABLE_NAME_TEMPLATE)
        @Size(min = 1, max = 255, message = "authorName" + ConstraintMessageTemplate.SIZE_MESSAGE_TEMPLATE)
        @NotNull(message = "authorName" + ConstraintMessageTemplate.NOTNULL_MESSAGE_TEMPLATE)
        private final String authorName;

        @Min(value= 100, message = "price" + ConstraintMessageTemplate.MIN_MESSAGE_TEMPLATE)
        @NotNull(message = "price" + ConstraintMessageTemplate.NOTNULL_MESSAGE_TEMPLATE)
        private final Long price;

        @Min(value = 1, message = "page" + ConstraintMessageTemplate.MIN_MESSAGE_TEMPLATE)
        @NotNull(message = "page" + ConstraintMessageTemplate.NOTNULL_MESSAGE_TEMPLATE)
        private final Long page;

        @Min(value = 0, message = "quantity" + ConstraintMessageTemplate.MIN_MESSAGE_TEMPLATE)
        @NotNull(message = "quantity" + ConstraintMessageTemplate.NOTNULL_MESSAGE_TEMPLATE)
        private final Long quantity;
    }
}
