package net.junhabaek.tddpractice.book.adapter.out.persistence;

import net.junhabaek.tddpractice.book.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
