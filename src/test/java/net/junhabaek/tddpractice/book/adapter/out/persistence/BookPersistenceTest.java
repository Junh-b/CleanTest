package net.junhabaek.tddpractice.book.adapter.out.persistence;

import net.junhabaek.tddpractice.book.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@DataJpaTest
public class BookPersistenceTest {
    @PersistenceContext
    EntityManager em;

    private Long initBook(){
        Book book = Book.createNewBook("tragedy of Y", "alleryQueen", Money.of(15000L),
                Quantity.of(250L), Page.of(1L));

        em.persist(book);
        em.flush();
        em.clear();
        return book.getId();
    }

    @Test
    void GivenInitialBook_WhenFindWithEntityManager_ThenResultHasExpectedId() {
        //given
        Long id = initBook();

        //when
        Book book = em.find(Book.class, id);

        //then
        Assertions.assertThat(book.getId()).isEqualTo(id);
    }

    @Test
    void GivenInitialBook_WhenFindWithJPQLFind_ThenResultHasExpectedId() {
        //given
        Long id = initBook();

        //when
        TypedQuery<Book> query =
                em.createQuery("SELECT b from Book b where b.bookName = :bookName", Book.class)
                        .setParameter("bookName", "tragedy of Y");

        Book book = query.getSingleResult();

        //then
        Assertions.assertThat(book.getId()).isEqualTo(id);
    }


}
