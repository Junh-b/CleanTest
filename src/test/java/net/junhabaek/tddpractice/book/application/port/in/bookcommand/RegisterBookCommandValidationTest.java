package net.junhabaek.tddpractice.book.application.port.in.bookcommand;

import net.junhabaek.tddpractice.book.application.port.in.BookCommand;
import net.junhabaek.tddpractice.utils.validation.ValidatorTest;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

// TODO 일부 Constraint는 자체 단위테스트가 있으면, 중복해서 구현할 필요가 없다. 특히 NotNull, DistinguishableName 등
public class RegisterBookCommandValidationTest extends ValidatorTest<BookCommand.RegisterBook> {

    public final static String VALID_BOOK_NAME = "Tragedy of Y";
    public final static String VALID_AUTHOR_NAME = "Allery Queen";
    public final static Long VALID_PRICE = 100L;
    public final static Long VALID_PAGE = 1L;
    public final static Long VALID_QUANTITY = 0L;

    @Test
    void Given_ValidCommand_When_ValidateIt_Then_NoneOfConstraintViolationReturned() {
        //given
        BookCommand.RegisterBook validRegisterCommand =
                BookCommand.RegisterBook.builder()
                        .bookName(VALID_BOOK_NAME)
                        .authorName(VALID_AUTHOR_NAME)
                        .price(VALID_PRICE)
                        .page(VALID_PAGE)
                        .quantity(VALID_QUANTITY)
                        .build();

        //when
        List<ConstraintViolationInfo> constraintViolationInfoList = validate(validRegisterCommand);

        //then
        then(constraintViolationInfoList.size()).isEqualTo(0);
    }

    /*
        START OF BOOK_NAME TEST
     */
    @Test
    void Given_EmptyBookName_When_ValidatingCommand_Then_SizeConstraintViolationReturned() {
        //given
        String emptyBookName = "";

        BookCommand.RegisterBook invalidRegisterCommand =
                BookCommand.RegisterBook.builder()
                        .bookName(emptyBookName)
                        .authorName(VALID_AUTHOR_NAME)
                        .price(VALID_PRICE)
                        .page(VALID_PAGE)
                        .quantity(VALID_QUANTITY)
                        .build();

        //when
        List<ConstraintViolationInfo> constraintViolationInfoList = validate(invalidRegisterCommand);

        //then
        then(constraintViolationInfoList.size()).isEqualTo(1);

        ConstraintViolationInfo bookNameConstraintViolation = constraintViolationInfoList.get(0);
        then(bookNameConstraintViolation.getConstraintClass()).isEqualTo(Size.class);
        then(bookNameConstraintViolation.getInvalidValue()).isEqualTo(emptyBookName);
        then(bookNameConstraintViolation.getMessage()).isEqualTo("bookName should be between 1 and 255.");
    }

    @Test
    void Given_LongBookName_When_ValidatingCommand_Then_SizeConstraintViolationReturned() {
        //given
        String longBookNameWith256char = "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456";

        BookCommand.RegisterBook invalidRegisterCommand =
                BookCommand.RegisterBook.builder()
                        .bookName(longBookNameWith256char)
                        .authorName(VALID_AUTHOR_NAME)
                        .price(VALID_PRICE)
                        .page(VALID_PAGE)
                        .quantity(VALID_QUANTITY)
                        .build();

        //when
        List<ConstraintViolationInfo> constraintViolationInfoList = validate(invalidRegisterCommand);

        //then
        then(constraintViolationInfoList.size()).isEqualTo(1);

        ConstraintViolationInfo bookNameConstraintViolation = constraintViolationInfoList.get(0);
        then(bookNameConstraintViolation.getConstraintClass()).isEqualTo(Size.class);
        then(bookNameConstraintViolation.getInvalidValue()).isEqualTo(longBookNameWith256char);
        then(bookNameConstraintViolation.getMessage()).isEqualTo("bookName should be between 1 and 255.");
    }
    /*
        END OF BOOK_NAME TEST
     */


    /*
        START OF AUTHOR_NAME TEST
     */
    @Test
    void Given_EmptyAuthorName_When_ValidatingCommand_Then_SizeConstraintViolationReturned() {
        //given
        String emptyAuthorName = "";

        BookCommand.RegisterBook invalidRegisterCommand =
                BookCommand.RegisterBook.builder()
                        .bookName(VALID_BOOK_NAME)
                        .authorName(emptyAuthorName)
                        .price(VALID_PRICE)
                        .page(VALID_PAGE)
                        .quantity(VALID_QUANTITY)
                        .build();

        //when
        List<ConstraintViolationInfo> constraintViolationInfoList = validate(invalidRegisterCommand);

        //then
        then(constraintViolationInfoList.size()).isEqualTo(1);

        ConstraintViolationInfo bookNameConstraintViolation = constraintViolationInfoList.get(0);
        then(bookNameConstraintViolation.getConstraintClass()).isEqualTo(Size.class);
        then(bookNameConstraintViolation.getInvalidValue()).isEqualTo(emptyAuthorName);
        then(bookNameConstraintViolation.getMessage()).isEqualTo("authorName should be between 1 and 255.");
    }

    @Test
    void Given_LongAuthorName_When_ValidatingCommand_Then_SizeConstraintViolationReturned() {
        //given
        String longAuthorNameWith256char = "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456";

        BookCommand.RegisterBook invalidRegisterCommand =
                BookCommand.RegisterBook.builder()
                        .bookName(VALID_BOOK_NAME)
                        .authorName(longAuthorNameWith256char)
                        .price(VALID_PRICE)
                        .page(VALID_PAGE)
                        .quantity(VALID_QUANTITY)
                        .build();

        //when
        List<ConstraintViolationInfo> constraintViolationInfoList = validate(invalidRegisterCommand);

        //then
        then(constraintViolationInfoList.size()).isEqualTo(1);

        ConstraintViolationInfo bookNameConstraintViolation = constraintViolationInfoList.get(0);
        then(bookNameConstraintViolation.getConstraintClass()).isEqualTo(Size.class);
        then(bookNameConstraintViolation.getInvalidValue()).isEqualTo(longAuthorNameWith256char);
        then(bookNameConstraintViolation.getMessage()).isEqualTo("authorName should be between 1 and 255.");
    }
    /*
        END OF AUTHOR_NAME TEST
     */


    /*
        START OF PRICE TEST
     */
    @Test
    void Given_LowPrice_When_ValidateCommand_Then_MinConstraintViolationReturned() {
        //given
        Long lowerPriceThanMinimum = 99L;

        BookCommand.RegisterBook invalidRegisterCommand =
                BookCommand.RegisterBook.builder()
                        .bookName(VALID_BOOK_NAME)
                        .authorName(VALID_AUTHOR_NAME)
                        .price(lowerPriceThanMinimum)
                        .page(VALID_PAGE)
                        .quantity(VALID_QUANTITY)
                        .build();

        //when
        List<ConstraintViolationInfo> constraintViolationInfoList = validate(invalidRegisterCommand);

        //then
        then(constraintViolationInfoList.size()).isEqualTo(1);

        ConstraintViolationInfo priceConstraintViolation = constraintViolationInfoList.get(0);
        then(priceConstraintViolation.getConstraintClass()).isEqualTo(Min.class);
        then(priceConstraintViolation.getInvalidValue()).isEqualTo(lowerPriceThanMinimum);
        then(priceConstraintViolation.getMessage()).isEqualTo("price should be 100 or more. '99' is less than 100.");
    }
    /*
        END OF PRICE TEST
     */


    /*
        START OF PAGE TEST
     */
    @Test
    void Given_LowPage_When_ValidateCommand_Then_MinConstraintViolationReturned() {
        //given
        Long lowerPageThanMinimum = 0L;

        BookCommand.RegisterBook invalidRegisterCommand =
                BookCommand.RegisterBook.builder()
                        .bookName(VALID_BOOK_NAME)
                        .authorName(VALID_AUTHOR_NAME)
                        .price(VALID_PRICE)
                        .page(lowerPageThanMinimum)
                        .quantity(VALID_QUANTITY)
                        .build();

        //when
        List<ConstraintViolationInfo> constraintViolationInfoList = validate(invalidRegisterCommand);

        //then
        then(constraintViolationInfoList.size()).isEqualTo(1);

        ConstraintViolationInfo priceConstraintViolation = constraintViolationInfoList.get(0);
        then(priceConstraintViolation.getConstraintClass()).isEqualTo(Min.class);
        then(priceConstraintViolation.getInvalidValue()).isEqualTo(lowerPageThanMinimum);
        then(priceConstraintViolation.getMessage()).isEqualTo("page should be 1 or more. '0' is less than 1.");
    }
    /*
        END OF PAGE TEST
     */


    /*
        START OF QUANTITY TEST
     */
    @Test
    void Given_LowQuantity_When_ValidateCommand_Then_MinConstraintViolationReturned() {
        //given
        Long lowerQuantityThanMinimum = -1L;

        BookCommand.RegisterBook invalidRegisterCommand =
                BookCommand.RegisterBook.builder()
                        .bookName(VALID_BOOK_NAME)
                        .authorName(VALID_AUTHOR_NAME)
                        .price(VALID_PRICE)
                        .page(VALID_PAGE)
                        .quantity(lowerQuantityThanMinimum)
                        .build();

        //when
        List<ConstraintViolationInfo> constraintViolationInfoList = validate(invalidRegisterCommand);

        //then
        then(constraintViolationInfoList.size()).isEqualTo(1);

        ConstraintViolationInfo priceConstraintViolation = constraintViolationInfoList.get(0);
        then(priceConstraintViolation.getConstraintClass()).isEqualTo(Min.class);
        then(priceConstraintViolation.getInvalidValue()).isEqualTo(lowerQuantityThanMinimum);
        then(priceConstraintViolation.getMessage()).isEqualTo("quantity should be 0 or more. '-1' is less than 0.");
    }
    /*
        END OF QUANTITY TEST
     */
}
