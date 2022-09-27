package net.junhabaek.tddpractice.book.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import net.junhabaek.tddpractice.book.application.port.out.persistence.SaveBookPort;
import net.junhabaek.tddpractice.book.domain.Book;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookPersistenceAdapter implements SaveBookPort {
    private final BookRepository bookRepository;

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }
}
