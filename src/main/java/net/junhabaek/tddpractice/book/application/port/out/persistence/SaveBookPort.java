package net.junhabaek.tddpractice.book.application.port.out.persistence;

import net.junhabaek.tddpractice.book.domain.Book;

public interface SaveBookPort {
    void saveBook(Book book);
}
