package net.junhabaek.tddpractice.book.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BookRequestDtoTest {
    public static ObjectMapper objectMapper;

    @BeforeAll
    public static void init(){
        objectMapper = new ObjectMapper();
    }

    @DisplayName("RegisterBook에 필요한 정보를 가진 ObjectNode를 ObjectMapper로 성공적으로 변환할 수 있다.")
    @Test
    void Given_ObjectNodeForRegisterBook_When_ConvertToBookRequestDto_Then_AllFieldsShouldBeValid() throws JsonProcessingException {
        //given
        final String bookName = "tragedy of Y";
        final String authorName = "Ellery Queen";
        final long price = 18000L;
        final long page = 200L;
        final long quantity = 50L;

        ObjectNode requestJsonNode = buildRequestJson(bookName, authorName, price, page, quantity);

        //when
        BookRequestDto.RegisterBookRequest registerBookRequest =
                objectMapper.treeToValue(requestJsonNode, BookRequestDto.RegisterBookRequest.class);

        //then
        Assertions.assertEquals(bookName, registerBookRequest.getBookName());
        Assertions.assertEquals(authorName, registerBookRequest.getAuthorName());
        Assertions.assertEquals(price, registerBookRequest.getPrice());
        Assertions.assertEquals(page, registerBookRequest.getPage());
        Assertions.assertEquals(quantity, registerBookRequest.getQuantity());
    }

    private ObjectNode buildRequestJson(String bookName, String authorName, Long price, Long page, Long quantity){
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("bookName", bookName);
        objectNode.put("authorName", authorName);
        objectNode.put("price", price);
        objectNode.put("page", page);
        objectNode.put("quantity", quantity);

        return objectNode;
    }

}
