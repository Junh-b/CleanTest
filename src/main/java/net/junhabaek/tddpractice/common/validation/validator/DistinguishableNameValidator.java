package net.junhabaek.tddpractice.common.validation.validator;

import net.junhabaek.tddpractice.common.validation.constraint.DistinguishableName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DistinguishableNameValidator implements ConstraintValidator<DistinguishableName, String> {
    private static final Set<Character> whitespaceCharacters = new HashSet<>(List.of('\t', '\n', '\b', '\r', ' '));

    @Override
    public boolean isValid(String str, ConstraintValidatorContext context) {
        if(notNeedToBeValidated(str)) return true;

        return !hasAnyWhiteCharacterBeforeOrAfterWord(str) && !hasInappropriateWhitespaceCharactersInWord(str);
    }

    private boolean notNeedToBeValidated(String str) {
        return str == null || str.length() == 0;
    }

    private boolean hasAnyWhiteCharacterBeforeOrAfterWord(String str){
        char firstCharacter = str.charAt(0);
        if(isWhitespaceCharacter(firstCharacter)) return true;

        if(str.length() == 1) return false;
        char lastCharacter = str.charAt(str.length()-1);
        if(isWhitespaceCharacter(lastCharacter)) return true;

        return false;
    }

    private boolean isWhitespaceCharacter(char ch){
        return whitespaceCharacters.contains(ch);
    }

    private boolean isWhitespaceCharacterExceptSpace(char ch){
        if(ch == ' ') return false;
        return isWhitespaceCharacter(ch);
    }

    private boolean hasInappropriateWhitespaceCharactersInWord(String str) {
        for (int i = 0; i < str.length(); i++) {
            if(isWhitespaceCharacterExceptSpace(str.charAt(i))) return true;
        }
        return false;
    }
}
