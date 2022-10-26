package net.junhabaek.tddpractice.common.validation.validator;

import net.junhabaek.tddpractice.common.validation.ConstraintMessageTemplate;
import net.junhabaek.tddpractice.common.validation.constraint.DistinguishableName;
import net.junhabaek.tddpractice.base.ValidatorTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;

class DistinguishableNameValidatorTest extends ValidatorTest<DistinguishableNameValidatorTest.TestObject> {

    @Test
    void Given_ValidName_When_ValidateIt_Then_ReturnEmptyConstraintViolations() {
        //given
        final String validName = "abcd";
        TestObject testObject = new TestObject(validName);

        //when
        List<ConstraintViolationInfo> constraintViolationInfos = validate(testObject);

        //then
        then(constraintViolationInfos.size()).isEqualTo(0);
    }

    @Test
    void Given_IndistinguishableName_When_ValidateIt_Then_ReturnSingleConstraintViolationWithInterpolatedMessage() {
        //given
        final String indistinguishableName = "    ";
        TestObject testObject = new TestObject(indistinguishableName);

        //when
        List<ConstraintViolationInfo> constraintViolationInfos = validate(testObject);

        //then
        then(constraintViolationInfos.size()).isEqualTo(1);

        ConstraintViolationInfo violationInfo = constraintViolationInfos.get(0);
        then(violationInfo.getMessage()).isEqualTo("str" + ConstraintMessageTemplate.DISTINGUISHABLE_NAME_TEMPLATE);
    }

    private void assertDistinguishableNameConstraintViolationExists(List<ConstraintViolationInfo> validationResults){
        then(validationResults.size()).isEqualTo(1);

        ConstraintViolationInfo violationInfo = validationResults.get(0);
        then(violationInfo.getConstraintClass()).isEqualTo(DistinguishableName.class);
    }

    @Test
    void Given_TabIncludedString_When_ValidateIt_Then_ReturnSingleConstraintViolationOfDistinguishableName() {
        //given
        final String tabIncludedStr = "ab\tcd";
        TestObject testObject = new TestObject(tabIncludedStr);

        //when
        List<ConstraintViolationInfo> constraintViolationInfos = validate(testObject);

        //then
        assertDistinguishableNameConstraintViolationExists(constraintViolationInfos);
    }

    @Test
    void Given_NewLineIncludedString_When_ValidateIt_Then_ReturnSingleConstraintViolationOfDistinguishableName() {
        //given
        final String newLineIncludedStr = "ab\ncd";
        TestObject testObject = new TestObject(newLineIncludedStr);

        //when
        List<ConstraintViolationInfo> constraintViolationInfos = validate(testObject);

        //then
        assertDistinguishableNameConstraintViolationExists(constraintViolationInfos);
    }

    @Test
    void Given_BackspaceIncludedString_When_ValidateIt_Then_ReturnSingleConstraintViolationOfDistinguishableName() {
        //given
        final String backspaceIncludedStr = "ab\bcd";
        TestObject testObject = new TestObject(backspaceIncludedStr);

        //when
        List<ConstraintViolationInfo> constraintViolationInfos = validate(testObject);

        //then
        assertDistinguishableNameConstraintViolationExists(constraintViolationInfos);
    }

    @Test
    void Given_CarriageReturnIncludedString_When_ValidateIt_Then_ReturnSingleConstraintViolationOfDistinguishableName() {
        //given
        final String carriageReturnIncludedStr = "ab\rcd";
        TestObject testObject = new TestObject(carriageReturnIncludedStr);

        //when
        List<ConstraintViolationInfo> constraintViolationInfos = validate(testObject);

        //then
        assertDistinguishableNameConstraintViolationExists(constraintViolationInfos);
    }

    @Test
    void Given_SpaceBeforeWord_When_ValidateIt_Then_ReturnSingleConstraintViolation() {
        //given
        final String spaceBeforeWordStr = "  abcd";
        TestObject testObject = new TestObject(spaceBeforeWordStr);

        //when
        List<ConstraintViolationInfo> constraintViolationInfos = validate(testObject);

        //then
        assertDistinguishableNameConstraintViolationExists(constraintViolationInfos);
    }

    @Test
    void Given_SpaceAfterWord_When_ValidateIt_Then_ReturnSingleConstraintViolation() {
        //given
        final String spaceAfterWordStr = "abcd  ";
        TestObject testObject = new TestObject(spaceAfterWordStr);

        //when
        List<ConstraintViolationInfo> constraintViolationInfos = validate(testObject);

        //then
        assertDistinguishableNameConstraintViolationExists(constraintViolationInfos);
    }

    protected static class TestObject{
        @DistinguishableName(message = "str" + ConstraintMessageTemplate.DISTINGUISHABLE_NAME_TEMPLATE)
        String str;

        public TestObject(String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }
    }
}