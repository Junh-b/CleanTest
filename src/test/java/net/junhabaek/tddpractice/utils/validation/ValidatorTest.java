package net.junhabaek.tddpractice.utils.validation;

import net.junhabaek.tddpractice.common.validation.ValidationHelper;
import org.junit.jupiter.api.BeforeAll;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @param <T> 검증 대상 클래스를 의미한다.
 */
public abstract class ValidatorTest<T> {
    protected static Validator validator;

    @BeforeAll
    public static void init(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * @param validateTarget Validation의 대상이 되는 instance를 전달한다.
     *                       javax.validation의 annotation으로 Constraint가 정의되어 있어야 한다.
     * @return Constraint를 위반한 field명을 key로, Constraint 위반 정보를 담는 ConstraintViolationInfo 인스턴스를 Value로 삼는 Map을 반환한다
     */
    protected <T> List<ConstraintViolationInfo> validate(T validateTarget) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(validateTarget);

        List<ConstraintViolationInfo> constraintViolationInfoList =
                constraintViolations.stream()
                        .map(ConstraintViolationInfo::new)
                        .collect(Collectors.toList());

        return constraintViolationInfoList;
    }


    /**
     * javax.validation의 ConstraintViolation 클래스로부터, Validation 검증에 필수적인 필드만을 추출해낸 클래스이다.
     */
    protected static class ConstraintViolationInfo{
        /**
         * Constraint annotation class를 의미한다. 어떤 종류의 Constraint에 의해 발생한 ConstraintViolation인지 알기 위해 사용된다.
         */
        Class constraintClass;
        /**
         * ConstraintViolation이 발생한 필드 명을 의미한다.
         */
        String fieldName;
        /**
         * Constraint를 통과하지 못한 값을 의미한다.
         */
        Object invalidValue;
        /**
         * Constraint annotation의 'message'가 interpolation된 결과 메시지이다.
         */
        String message;

        public ConstraintViolationInfo(ConstraintViolation constraintViolation){
            this.constraintClass = constraintViolation.getConstraintDescriptor().getAnnotation().annotationType();
            this.fieldName = ValidationHelper.extractFieldNameFromPropertyPathStr(constraintViolation.getPropertyPath().toString());
            this.invalidValue = constraintViolation.getInvalidValue();
            this.message = constraintViolation.getMessage();
        }

        public Class getConstraintClass() {
            return constraintClass;
        }

        public String getFieldName() {
            return fieldName;
        }

        public Object getInvalidValue() {
            return invalidValue;
        }

        public String getMessage() {
            return message;
        }
    }
}
