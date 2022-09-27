package net.junhabaek.tddpractice.book.application.port.in;

import net.junhabaek.tddpractice.book.application.port.out.persistence.SaveBookPort;
import net.junhabaek.tddpractice.book.application.servicce.RegisterBookService;
import net.junhabaek.tddpractice.book.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class RegisterBookServiceTest {
    final SaveBookPort saveBookPort = Mockito.mock(SaveBookPort.class);

    private final RegisterBookService registerBookService =
            new RegisterBookService(saveBookPort);

    @Test
    void givenValidBookCommand_whenRegisterBook_thenShouldReturnValidBookInfo() {
        //given
        Long bookId = 1L;
        String bookName = "Tragedy of Y";
        String authorName = "Allery Queen";
        Long page = 150L;
        Long price = 15000L;
        Long quantity = 1000L;
        LocalDateTime dummyCreatedTime = LocalDateTime.of(2022,9,6,11,40);

        saveOperationOfSaveBookPort_willSetGivenBookIdAndCreatedDate(bookId, dummyCreatedTime);

        BookCommand.RegisterBook registerBookCommand =
                BookCommand.RegisterBook.builder()
                        .bookName(bookName)
                        .authorName(authorName)
                        .page(page)
                        .price(price)
                        .quantity(quantity)
                        .build();

        //when
        BookInfo creationResultBookInfo = registerBookService.registerBook(registerBookCommand);

        //then
        then(saveBookPort)
                .should(times(1))
                .saveBook(any(Book.class));

        // 매퍼의 테스트와 중복된다고 생각할 수도 있지만, 매퍼의 테스트는 모든 코너케이스를 포함하는 만큼 역할이 다르다.
        assertEquals(bookId, creationResultBookInfo.getId());
        assertEquals(dummyCreatedTime, creationResultBookInfo.getCreatedDate());
        assertEquals(bookName, creationResultBookInfo.getBookName());
        assertEquals(authorName, creationResultBookInfo.getAuthorName());
        assertEquals(page, creationResultBookInfo.getPage());
        assertEquals(BigInteger.valueOf(price), creationResultBookInfo.getPrice());
        assertEquals(BigInteger.valueOf(quantity), creationResultBookInfo.getQuantity());
    }

    private void saveOperationOfSaveBookPort_willSetGivenBookIdAndCreatedDate(Long givenId, LocalDateTime createdDate){
        doAnswer(invocation -> {
            Book book = invocation.getArgument(0);

            book.setId(givenId);
            book.setCreatedDate(createdDate);
            return null;
        }).when(saveBookPort).saveBook(any(Book.class));
    }
}
