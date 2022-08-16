package net.junhabaek.tddpractice.common.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.FieldError;

import java.io.IOException;
import java.util.List;

public class ErrorResponseTest {
    public static ObjectMapper objectMapper;

    @BeforeAll
    static void init(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void givenJsonFile_whenReadAsJsonNode_shouldIncludErrorCodeFieldWith1() throws IOException {
        //given
        JsonNode jsonNode = objectMapper.readValue(
                ResourceUtils.getFile("classpath:ErrorResponseExampleWithFieldError.json"),
                JsonNode.class);
        //when
        String code = jsonNode.get("errorCode").asText();

        //then
        Assertions.assertEquals("A05", code);
    }

    @Test
    void givenErrorResponseWithFieldError_whenConvertToJson_thenShouldEqualExpectedFormat() throws IOException {
        //given
        JsonNode expectedJsonNode = objectMapper.readValue(
                ResourceUtils.getFile("classpath:ErrorResponseExampleWithFieldError.json"),
                JsonNode.class);
        JsonNode expectedErrorsNode = expectedJsonNode.get("errors").elements().next();

        List<FieldError> targetFieldErrors = List.of(
                new FieldError("someObject", "someField", "someMessage"));

        ErrorResponse targetErrorResponse = ErrorResponse.of(ErrorStatus.INTERNAL_SERVER_ERROR, targetFieldErrors);

        //when
        JsonNode actualConvertResult = objectMapper.valueToTree(targetErrorResponse);
        JsonNode actualErrorsNode = actualConvertResult.get("errors").elements().next();

        //then
        Assertions.assertFalse(actualConvertResult.has("httpStatus"));

        Assertions.assertEquals(expectedJsonNode.get("errorCode").asText(), actualConvertResult.get("errorCode").asText());
        Assertions.assertEquals(expectedJsonNode.get("errorMessage").asText(), actualConvertResult.get("errorMessage").asText());

        Assertions.assertEquals(expectedErrorsNode.get("objectName").asText(), actualErrorsNode.get("objectName").asText());
        Assertions.assertEquals(expectedErrorsNode.get("field").asText(), actualErrorsNode.get("field").asText());
        Assertions.assertEquals(expectedErrorsNode.get("defaultMessage").asText(), actualErrorsNode.get("defaultMessage").asText());

    }

    @Test
    void givenErrorResponseWithoutFieldError_whenConvertToJson_thenShouldEqualExpectedFormat() throws IOException {
        //given
        JsonNode expectedJsonNode = objectMapper.readValue(
                ResourceUtils.getFile("classpath:ErrorResponseExampleWithoutFieldError.json"),
                JsonNode.class);

        ErrorResponse targetErrorResponse = ErrorResponse.of(ErrorStatus.METHOD_NOT_SUPPORTED);

        //when
        JsonNode actualConvertResult = objectMapper.valueToTree(targetErrorResponse);

        //then
        Assertions.assertFalse(actualConvertResult.has("httpStatus"));
        Assertions.assertFalse(actualConvertResult.has("errors"));

        Assertions.assertEquals(expectedJsonNode.get("errorCode").asText(), actualConvertResult.get("errorCode").asText());
        Assertions.assertEquals(expectedJsonNode.get("errorMessage").asText(), actualConvertResult.get("errorMessage").asText());
    }
}
