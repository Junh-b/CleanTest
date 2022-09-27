package net.junhabaek.tddpractice.book.application.servicce;

import lombok.RequiredArgsConstructor;
import net.junhabaek.tddpractice.book.application.port.in.BookCommand;
import net.junhabaek.tddpractice.book.application.port.in.BookInfo;
import net.junhabaek.tddpractice.book.application.port.in.RegisterBookUsecase;
import net.junhabaek.tddpractice.book.application.port.out.persistence.SaveBookPort;
import net.junhabaek.tddpractice.book.domain.Book;
import net.junhabaek.tddpractice.book.domain.Money;
import net.junhabaek.tddpractice.book.domain.Page;
import net.junhabaek.tddpractice.book.domain.Quantity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class RegisterBookService implements RegisterBookUsecase {

    private final SaveBookPort saveBookPort;

    @Override
    @Transactional
    public BookInfo registerBook(BookCommand.RegisterBook registerBookCommand) {
        Book newBook = Book.createNewBook(registerBookCommand.getBookName(),
                                            registerBookCommand.getAuthorName(),
                                            Money.of(registerBookCommand.getPrice()),
                                            Quantity.of(registerBookCommand.getQuantity()),
                                            Page.of(registerBookCommand.getPage()));

        saveBookPort.saveBook(newBook);

        BookInfo saveResult = BookInfo.builder()
                .id(newBook.getId())
                .createdDate(newBook.getCreatedDate())
                .bookName(newBook.getBookName())
                .authorName(newBook.getAuthorName())
                .price(newBook.getPrice().getAmount())
                .page(newBook.getPage().getPage())
                .quantity(newBook.getQuantity().getQuantity()).build();

        return saveResult;
    }
}
