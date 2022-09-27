package net.junhabaek.tddpractice.book.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/*
    Money와 Quantity ValueObject는 BigInteger인데, BookRequestDto의 price, quantity가 Long인 이유:
    Money와 Quantity는 책 말고도 다른곳에 쓰일 수 있는 범용 값 객체이다.
    하지만 책에 한해서는 BigInteger까지의 범위가 불필요하다. 책의 가격이 20억을 넘거나, 책의 잔고가 20억을 넘는 사례는 없을 것이라 판단.
 */
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
        private final Long quantity;

        @JsonCreator
        public RegisterBookRequest(@JsonProperty("bookName")String bookName, @JsonProperty("authorName")String authorName,
                                   @JsonProperty("price")Long price, @JsonProperty("page")Long page,
                                   @JsonProperty("quantity")Long quantity) {
            this.bookName = bookName;
            this.authorName = authorName;
            this.price = price;
            this.page = page;
            this.quantity = quantity;
        }
    }
}
