package net.junhabaek.tddpractice.book.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

public abstract class BookResponseDto {
    @Getter
    @ToString
    @AllArgsConstructor
    @JsonPropertyOrder({"bookId", "createdDate"})
    public static class RegisterBookResponse {
        @JsonProperty("bookId")
        private Long bookId;
        @JsonProperty("createdDate")
        private LocalDateTime createdDate;
    }
}
