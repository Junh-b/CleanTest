package net.junhabaek.tddpractice.book.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.junhabaek.tddpractice.book.domain.Money;
import net.junhabaek.tddpractice.book.domain.Quantity;

public interface BookRequestDto {
    @Getter
    @AllArgsConstructor
    @ToString
    public static class RegisterBookRequest {
        private final String bookName;
        private final String authorName;
        private final Money price;
        private final Long page;
        private final Quantity quantity;
    }
}
