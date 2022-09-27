package net.junhabaek.tddpractice.book.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import net.junhabaek.tddpractice.base.AcceptanceTest;
import net.junhabaek.tddpractice.book.domain.Book;
import net.junhabaek.tddpractice.common.exception.ErrorStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;

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
        JsonPath jsonPath = response.body().jsonPath();

        //then
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        Assertions.assertNotNull(jsonPath.getInt("bookId"));
        Assertions.assertNotNull(jsonPath.getString("createdDate"));
    }

    @DisplayName("유효하지 않은 책 생성 요청을 전달했을 때, 책 생성에 실패한다.")
    @Test
    void GivenInvalidRegisterBookRequest_WhenRegisterBook_ShouldBeFail() {
        //given
        String bookName = "";
        String authorName = "";
        Long price = -50L;
        Long page = -1L;
        Long quantity = -50L;

        //when
        ExtractableResponse<Response> response = registerBook(bookName, authorName, price, page, quantity);
        JsonPath jsonPath = response.body().jsonPath();

        //then
        Assertions.assertEquals(
                HttpStatus.BAD_REQUEST.value(),
                response.statusCode());

        Assertions.assertEquals(
                ErrorStatus.INVALID_INPUT_VALUE.getErrorCode(),
                jsonPath.getString("errorCode"));
        Assertions.assertEquals(
                ErrorStatus.INVALID_INPUT_VALUE.getDefaultErrorMessage(),
                jsonPath.getString("errorMessage"));

        Assertions.assertEquals(
                5,
                jsonPath.getList("errors").size()
        );

        Assertions.assertFalse(
                jsonPath.getBoolean("errors[0].bindingFailure")
        );
    }

    // 재사용 가능.
    ExtractableResponse<Response> registerBook(String bookName, String authorName, Long price,
                                               Long page, Long quantity){

        ObjectNode requestNode = objectMapper.createObjectNode();

        requestNode.put("bookName", bookName);
        requestNode.put("authorName", authorName);
        requestNode.put("price", price);
        requestNode.put("page", page);
        requestNode.put("quantity", quantity);

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
