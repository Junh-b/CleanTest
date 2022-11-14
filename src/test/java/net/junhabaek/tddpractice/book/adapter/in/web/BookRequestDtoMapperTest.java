package net.junhabaek.tddpractice.book.adapter.in.web;

import net.junhabaek.tddpractice.book.application.port.in.BookCommand;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookRequestDtoMapperTest {
    private BookRequestDtoMapper mapper = Mappers.getMapper(BookRequestDtoMapper.class);

    // Happy Path Testing
    @Test
    void Given_ValidRegisterBookRequest_When_MapToCommand_Then_ShouldBeSuccessful() {
        //given
        final String bookName = "Tragedy of Y";
        final String authorName = "Allery Queen";
        final long price = 15000L;
        final long page = 150L;
        final long quantity = 50L;

        BookRequestDto.RegisterBookRequest registerBookRequest =
                new BookRequestDto.RegisterBookRequest(bookName, authorName, price, page, quantity);

        //when
        BookCommand.RegisterBook registerBookCommand = mapper.registerRequestToRegisterCommand(registerBookRequest);

        //then
        assertEquals(bookName, registerBookCommand.getBookName());
        assertEquals(authorName, registerBookCommand.getAuthorName());
        assertEquals(price, registerBookCommand.getPrice());
        assertEquals(page, registerBookCommand.getPage());
        assertEquals(quantity, registerBookCommand.getQuantity());
    }
}
