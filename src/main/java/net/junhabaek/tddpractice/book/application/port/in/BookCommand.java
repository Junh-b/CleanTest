package net.junhabaek.tddpractice.book.application.port.in;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public abstract class BookCommand {
    @Getter
    @Builder
    public static class RegisterBook {
        @NotBlank(message = "bookName cannot be blank.")
        private final String bookName;

        @NotBlank(message = "authorName cannot be blank.")
        private final String authorName;

        @Min(value= 100, message = "price should be {value} or more. '${validatedValue}' is less than {value}.")
        @NotNull(message = "price cannot be null.")
        private final Long price;

        @Min(value = 1, message = "page should be {value} or more. '${validatedValue}' is less than {value}.")
        @NotNull(message = "page cannot be null.")
        private final Long page;

        @Min(value = 0, message = "quantity should be {value} or more. '${validatedValue}' is less than {value}.")
        @NotNull(message = "quantity cannot be null.")
        private final Long quantity;
    }
}
