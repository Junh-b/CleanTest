package net.junhabaek.tddpractice.book.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(value = AccessLevel.PROTECTED)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id", updatable = false)
    private Long id;

    @Column(name="book_name", nullable = false)
    private String bookName;

    private String authorName;
    private Long price;
    private Long page;
    private Long quantity;


    // create new book which is not yet persisted
    public static Book createNewBook(String bookName, String authorName, Long price, Long page, Long quantity){
        Book book = new Book();

        book.setBookName(bookName);
        book.setAuthorName(authorName);
        book.setPrice(price);
        book.setPage(page);
        book.setQuantity(quantity);

        return book;
    }

    // restore book object which has already been persisted
    public static Book restoreBook(Long id, String bookName, String authorName, Long price, Long page, Long quantity){
        Book book = new Book();

        book.setId(id);
        book.setBookName(bookName);
        book.setAuthorName(authorName);
        book.setPrice(price);
        book.setPage(page);
        book.setQuantity(quantity);

        return book;
    }

    public void changeBookName(String bookName){
        this.bookName = bookName;
    }

    public void changePrice(Long price){
        this.price = price;
    }
}

