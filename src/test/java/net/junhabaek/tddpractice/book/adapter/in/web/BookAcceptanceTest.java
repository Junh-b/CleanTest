package net.junhabaek.tddpractice.book.adapter.in.web;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import net.junhabaek.tddpractice.base.AcceptanceTest;
import net.junhabaek.tddpractice.book.domain.Book;
import net.junhabaek.tddpractice.book.domain.Money;
import net.junhabaek.tddpractice.book.domain.Quantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class BookAcceptanceTest extends AcceptanceTest {
    @Override
    protected List<Class> getEntityClasses() {
        return List.of(Book.class);
    }

    @DisplayName("유효한 요청을 전달했을 때, 책 생성에 성공한다.")
    @Test
    void GivenValidRegisterBookRequest_WhenRegisterBook_ShouldBeSuccessful() {
        //given
        String bookName = "tragedy of Y";
        String authorName = "Ellery Queen";
        Money price = Money.of(18000L);
        Long page = 200L;
        Quantity quantity = Quantity.of(100L);

        //when
        ExtractableResponse<Response> response = registerBook(bookName, authorName, price, page, quantity);

        //then
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.statusCode());
    }

    ExtractableResponse<Response> registerBook(String bookName, String authorName, Money price,
                                               Long page, Quantity quantity){
        BookRequestDto.RegisterBookRequest requestDto =
                new BookRequestDto.RegisterBookRequest(bookName, authorName,
                        price, page, quantity);

        return RestAssured
                .given()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(requestDto)
                .when()
                    .post("/books")
                .then()
                    .log().all()
                    .extract();
    }
}
