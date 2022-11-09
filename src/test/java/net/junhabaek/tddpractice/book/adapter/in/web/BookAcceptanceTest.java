package net.junhabaek.tddpractice.book.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import net.junhabaek.tddpractice.base.AcceptanceTest;
import net.junhabaek.tddpractice.book.adapter.out.persistence.BookRepository;
import net.junhabaek.tddpractice.book.domain.Book;
import net.junhabaek.tddpractice.common.exception.ErrorResponse;
import net.junhabaek.tddpractice.common.exception.ErrorStatus;
import net.junhabaek.tddpractice.common.validation.ConstraintMessageTemplate;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.BDDAssertions.then;

public class BookAcceptanceTest extends AcceptanceTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BookRepository bookRepository;

    @Override
    protected List<Class> getEntityClasses() {
        return List.of(Book.class);
    }

    @DisplayName("유효한 책 생성 요청을 전달했을 때, 책 생성에 성공한다.")
    @Test
    void Given_ValidRegisterBookRequest_When_RegisterBook_Then_ShouldBeSuccessful() {
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
        then(response.statusCode())
                .isEqualTo(HttpStatus.CREATED.value());
        then(jsonPath.getInt("bookId"))
                .isNotNull();
        then(jsonPath.getString("createdDate"))
                .isNotNull();
    }

    @DisplayName("유효하지 않은 책 생성 요청을 전달했을 때, 책 생성에 실패한다.")
    @Test
    void Given_InvalidRegisterBookRequest_When_RegisterBook_Then_ShouldBeFail() {
        //given
        String bookName = "ab\ndf";
        String authorName = "  abcd";
        Long price = -50L;
        Long page = -1L;
        Long quantity = -50L;

        Map<String, ErrorResponse.FieldErrorDetail> expectedFieldErrorDetails = new HashMap<>();
        expectedFieldErrorDetails.put("bookName",
                new ErrorResponse.FieldErrorDetail("bookName", bookName,
                        "bookName" + ConstraintMessageTemplate.DISTINGUISHABLE_NAME_TEMPLATE));
        expectedFieldErrorDetails.put("authorName",
                new ErrorResponse.FieldErrorDetail("authorName", authorName,
                        "authorName" + ConstraintMessageTemplate.DISTINGUISHABLE_NAME_TEMPLATE));
        expectedFieldErrorDetails.put("price",
                new ErrorResponse.FieldErrorDetail("price", price.toString(),
                        "price should be 100 or more. '-50' is less than 100."));
        expectedFieldErrorDetails.put("page",
                new ErrorResponse.FieldErrorDetail("page", page.toString(),
                        "page should be 1 or more. '-1' is less than 1."));
        expectedFieldErrorDetails.put("quantity",
                new ErrorResponse.FieldErrorDetail("quantity", quantity.toString(),
                        "quantity should be 0 or more. '-50' is less than 0."));

        //when
        ExtractableResponse<Response> response = registerBook(bookName, authorName, price, page, quantity);
        JsonPath jsonPath = response.body().jsonPath();

        //then
        then(response.statusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        then(jsonPath.getString("errorCode"))
                .isEqualTo(ErrorStatus.INVALID_INPUT_VALUE.getErrorCode());
        then(jsonPath.getString("errorMessage"))
                .isEqualTo(ErrorStatus.INVALID_INPUT_VALUE.getDefaultErrorMessage());

        then(jsonPath.getList("fieldErrorDetails").size())
                .isEqualTo(5);

        for (ErrorResponse.FieldErrorDetail fieldErrorDetail: jsonPath.getList("fieldErrorDetails", ErrorResponse.FieldErrorDetail.class)) {
            ErrorResponse.FieldErrorDetail expectedDetail = expectedFieldErrorDetails.get(fieldErrorDetail.getFieldName());

            then(fieldErrorDetail.getRejectedValue()).isEqualTo(expectedDetail.getRejectedValue());
            then(fieldErrorDetail.getConstraintMessage()).isEqualTo(expectedDetail.getConstraintMessage());
        }
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
