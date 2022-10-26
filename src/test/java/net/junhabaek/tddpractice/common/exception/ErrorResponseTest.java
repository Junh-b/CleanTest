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

import static org.assertj.core.api.BDDAssertions.then;

public class ErrorResponseTest {
    public static ObjectMapper objectMapper;

    @BeforeAll
    static void init(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void Given_JsonFile_When_ReadAsJsonNode_Then_shouldIncludeErrorCodeFieldWith1() throws IOException {
        //given
        JsonNode jsonNode = objectMapper.readValue(
                ResourceUtils.getFile("classpath:ErrorResponseExampleWithFieldError.json"),
                JsonNode.class);
        //when
        String code = jsonNode.get("errorCode").asText();

        //then
        then(code).isEqualTo(ErrorStatus.INVALID_INPUT_VALUE.getErrorCode());
    }

    @Test
    void Given_ErrorResponseWithFieldError_When_ConvertToJson_Then_ShouldEqualExpectedFormat() throws IOException {
        //given
        JsonNode expectedJsonNode = objectMapper.readValue(
                ResourceUtils.getFile("classpath:ErrorResponseExampleWithFieldError.json"),
                JsonNode.class);
        JsonNode expectedErrorsNode = expectedJsonNode.get("fieldErrorDetails").elements().next();

        List<FieldError> targetFieldErrors = List.of(
                new FieldError("someObject", "someField", "rejectedValue",
                        true, null, null, "someMessage"));

        ErrorResponse targetErrorResponse = ErrorResponse.of(ErrorStatus.INVALID_INPUT_VALUE, targetFieldErrors);

        //when
        JsonNode actualConvertResult = objectMapper.valueToTree(targetErrorResponse);
        JsonNode actualErrorsNode = actualConvertResult.get("fieldErrorDetails").elements().next();

        //then
        then(actualConvertResult.has("httpStatus")).isFalse();

        then(actualConvertResult.get("errorCode")).isEqualTo(expectedJsonNode.get("errorCode"));
        then(actualConvertResult.get("errorMessage").asText()).isEqualTo(expectedJsonNode.get("errorMessage").asText());

        then(actualErrorsNode.get("rejectedValue").asText()).isEqualTo(expectedErrorsNode.get("rejectedValue").asText());
        then(actualErrorsNode.get("fieldName").asText()).isEqualTo(expectedErrorsNode.get("fieldName").asText());
        then(actualErrorsNode.get("constraintMessage").asText()).isEqualTo(expectedErrorsNode.get("constraintMessage").asText());
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
        then(actualConvertResult.has("httpStatus")).isFalse();
        then(actualConvertResult.has("fieldErrorDetails")).isFalse();

        then(actualConvertResult.get("errorCode").asText()).isEqualTo(expectedJsonNode.get("errorCode").asText());
        then(actualConvertResult.get("errorMessage").asText()).isEqualTo(expectedJsonNode.get("errorMessage").asText());
    }
}
