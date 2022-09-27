package net.junhabaek.tddpractice.book.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="book")
@Access(value=AccessType.FIELD)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id", updatable = false)
    private Long id;

    @Column(name="book_name", nullable = false)
    private String bookName;

    @Column(name="author_name", nullable = false)
    private String authorName;
    @Embedded
    private Money price;
    @Embedded
    private Page page;
    @Embedded
    private Quantity quantity;

    // TODO 생성 시간 abstract class로 끌어올리기? --> 정작 도메인 클래스들이 상속의 이점을 활용 못하게 될 수도?
    @Column(name="created_date", nullable = false, updatable = false)
    @CreatedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDate;


    // 2 static factory idea reference : Get Your Hands Dirty on Clean Architecture's Account class in chapter 6
    // create new book which is not yet persisted
    public static Book createNewBook(String bookName, String authorName, Money price, Quantity quantity, Page page){
        Book book = new Book();

        book.bookName = bookName;
        book.authorName = authorName;
        book.price = price;
        book.quantity = quantity;
        book.page = page;

        //can emit domain event here

        return book;
    }

    // restore book object which has already been persisted
    public static Book restoreBook(Long id, String bookName, String authorName, Money price, Quantity quantity, Page page){
        Book book = new Book();

        book.id = id;
        book.bookName = bookName;
        book.authorName = authorName;
        book.price = price;
        book.quantity = quantity;
        book.page = page;

        return book;
    }

    public void changeBookName(String bookName){
        this.bookName = bookName;
    }

    public void changePrice(Money price){
        this.price = price;
    }

    public void setId(Long id) {
        // TODO 모든 엔티티가 공통으로 가져야 할 메서드. 리팩터링 필요.
        if(this.getId()!= null){
            throw new IllegalStateException("can not change id of exising entity");
        }
        this.id = id;
    }

    public void setCreatedDate(LocalDateTime createdDate){
        if(this.getCreatedDate()!= null){
            throw new IllegalStateException("can not change created date of existing entity");
        }
        this.createdDate = createdDate;
    }
}

