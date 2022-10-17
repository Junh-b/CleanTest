package net.junhabaek.tddpractice.common.validation;

public abstract class ConstraintMessageTemplate {
    public static final String NOTBLANK_MESSAGE_TEMPLATE = " cannot be blank.";
    public static final String NOTNULL_MESSAGE_TEMPLATE = " cannot be null.";
    public static final String MIN_MESSAGE_TEMPLATE = " should be {value} or more. '${validatedValue}' is less than {value}.";
    public static final String MAX_MESSAGE_TEMPLATE = " should be {value} or less. '${validatedValue}' is more than {value}.";
    public static final String DISTINGUISHABLE_NAME_TEMPLATE =
            " is not distinguishable. Check whitespace characters before or after the word or inappropriate whitespace characters in the word.";
    public static final String SIZE_MESSAGE_TEMPLATE = " should be between {min} and {max}.";

    /*
    public static final String NULL_MESSAGE_TEMPLATE =
    public static final String NOTEMPTY_MESSAGE_TEMPLATE =
    public static final String EMAIL_MESSAGE_TEMPLATE =
    public static final String NEGATIVE_MESSAGE_TEMPLATE =
    public static final String POSITIVE_MESSAGE_TEMPLATE =
     */
}
