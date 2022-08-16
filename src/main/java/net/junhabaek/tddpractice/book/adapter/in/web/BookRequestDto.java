package net.junhabaek.tddpractice.book.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.junhabaek.tddpractice.book.domain.Money;
import net.junhabaek.tddpractice.book.domain.Quantity;

public interface BookRequestDto {
    @Getter
    @ToString
    public static class RegisterBookRequest {
        private final String bookName;
        private final String authorName;
        private final Money price;
        private final Long page;
        private final Quantity quantity;

        @JsonCreator
        public RegisterBookRequest(@JsonProperty("bookName")String bookName, @JsonProperty("authorName")String authorName,
                                   @JsonProperty("price")Money price, @JsonProperty("page")Long page,
                                   @JsonProperty("quantity")Quantity quantity) {
            this.bookName = bookName;
            this.authorName = authorName;
            this.price = price;
            this.page = page;
            this.quantity = quantity;
        }
    }
}
