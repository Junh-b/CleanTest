package net.junhabaek.tddpractice.book.adapter.in.web;

import net.junhabaek.tddpractice.book.application.port.in.BookInfo;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.assertj.core.api.BDDAssertions.then;

public class BookResponseDtoMapperTest {
    private BookResponseDtoMapper mapper = Mappers.getMapper(BookResponseDtoMapper.class);

    @Test
    void Given_ValidBookInfo_When_MapToResponseDto_Then_ShouldBeSuccessful() {
        //given
        final long id = 1L;
        final LocalDateTime createdDate = LocalDateTime.of(2022, 9, 6, 11, 40);
        final String bookName = "Tragedy of Y";
        final String authorName = "Aleery Queen";
        final long page = 100L;
        final BigInteger price = BigInteger.valueOf(15000L);
        final BigInteger quantity = BigInteger.valueOf(10L);

        BookInfo bookInfo = BookInfo.builder()
                .id(id)
                .createdDate(createdDate)
                .bookName(bookName)
                .authorName(authorName)
                .page(page)
                .price(price)
                .quantity(quantity)
                .build();

        //when
        BookResponseDto.RegisterBookResponse registerBookResponse = mapper.bookInfoToRegisterBookResponse(bookInfo);

        //then
        then(registerBookResponse.getBookId()).isEqualTo(id);
        then(registerBookResponse.getCreatedDate()).isEqualTo(createdDate);
    }
}
