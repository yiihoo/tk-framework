package net.thinklog.common.validator;

import net.thinklog.common.kit.RegExpKit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author azhao
 */
public class NotEmptyPatternValidator implements ConstraintValidator<NotEmptyPattern, String> {
    private NotEmptyPattern notEmptyPattern;

    @Override
    public void initialize(NotEmptyPattern pattern) {
        this.notEmptyPattern = pattern;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return RegExpKit.regExp(s, this.notEmptyPattern.regexp());
    }
}

