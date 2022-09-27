package net.junhabaek.tddpractice.book.application.port.in;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

// BookCommand의 경우와 마찬가지로, 유즈케이스별로 BookInfo를 분리하는 전략을 사용할 수도 있다.
@Data
@Builder
public class BookInfo {
    private Long id;
    private LocalDateTime createdDate;
    private String bookName;
    private String authorName;
    private Long page;
    private BigInteger price;
    private BigInteger quantity;
}
