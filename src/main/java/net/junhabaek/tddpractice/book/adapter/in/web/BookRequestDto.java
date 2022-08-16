package net.junhabaek.tddpractice.book.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.junhabaek.tddpractice.book.domain.Money;
import net.junhabaek.tddpractice.book.domain.Quantity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigInteger;

public interface BookRequestDto {
    @Getter
    @ToString
    public static class RegisterBookRequest {
        @NotBlank
        private final String bookName;
        @NotBlank
        private final String authorName;
        @Min(value=1)
        private final Long price;
        @Min(value=1)
        private final Long page;
        @Min(value=0)
        private final BigInteger quantity;

        @JsonCreator
        public RegisterBookRequest(@JsonProperty("bookName")String bookName, @JsonProperty("authorName")String authorName,
                                   @JsonProperty("price")Long price, @JsonProperty("page")Long page,
                                   @JsonProperty("quantity")BigInteger quantity) {
            this.bookName = bookName;
            this.authorName = authorName;
            this.price = price;
            this.page = page;
            this.quantity = quantity;
        }
    }
}
