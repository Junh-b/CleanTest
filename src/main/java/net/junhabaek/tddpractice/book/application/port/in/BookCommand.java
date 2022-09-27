package net.junhabaek.tddpractice.book.application.port.in;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public abstract class BookCommand {
    @Getter
    @Builder
    public static class RegisterBook {
        @NotBlank(message = "bookName cannot be blank.")
        private final String bookName;

        @NotBlank(message = "authorName cannot be blank")
        private final String authorName;

        @NotNull(message = "price cannot be null")
        private final Long price;

        @NotNull(message = "page cannot be null")
        private final Long page;

        @NotNull(message = "quantity cannot be null")
        private final Long quantity;
    }
}
