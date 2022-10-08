package net.junhabaek.tddpractice.book.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

public abstract class BookResponseDto {
    @Getter
    @ToString
    @AllArgsConstructor
    public static class RegisterBookResponse {
        private Long bookId;
        private LocalDateTime createdDate;
    }
}
