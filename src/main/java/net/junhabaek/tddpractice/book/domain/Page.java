package net.junhabaek.tddpractice.book.domain;

import lombok.Getter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Access(value= AccessType.FIELD)
public class Page {
    @Getter
    private Long page;

    public Page plus(Page other){
        return new Page(this.page + other.page);
    }
    public Page minus(Page other) {
        return new Page(this.page - other.page);
    }
    public Page multiply(Long times) {
        return new Page(this.page * times);
    }

    private Page() {
    }

    public Page(Long page) {
        if(page == null || page.compareTo(1L) < 0) {
            throw new IllegalArgumentException("Page can not have value that below 1.");
        }
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page1 = (Page) o;
        return page.equals(page1.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page);
    }

    public static Page of(Long value){
        return new Page(value);
    }
}