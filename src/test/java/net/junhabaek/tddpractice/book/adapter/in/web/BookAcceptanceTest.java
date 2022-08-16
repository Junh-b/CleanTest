package net.junhabaek.tddpractice.book.adapter.in.web;

import io.restassured.RestAssured;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import net.junhabaek.tddpractice.base.AcceptanceTest;
import net.junhabaek.tddpractice.book.domain.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class BookAcceptanceTest extends AcceptanceTest {
    @Autowired
    ObjectMapper objectMapper;

    @Override
    protected List<Class> getEntityClasses() {
        return List.of(Book.class);
    }

    @DisplayName("유효한 책 생성 요청을 전달했을 때, 책 생성에 성공한다.")
    @Test
    void GivenValidRegisterBookRequest_WhenRegisterBook_ShouldBeSuccessful() {
        //given
        String bookName = "tragedy of Y";
        String authorName = "Ellery Queen";
        Long price = 18000L;
        Long page = 200L;
        Long quantity = 50L;

        //when
        ExtractableResponse<Response> response = registerBook(bookName, authorName, price, page, quantity);

        //then
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.statusCode());
    }


        return given()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(requestNode)
                .when()
                    .post("/books")
                .then()
                    .log().all()
                    .extract();
    }
}
