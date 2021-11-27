package net.thinklog.common.validator;

import cn.hutool.core.util.IdcardUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author azhao
 */
public class IdCardPatternValidator implements ConstraintValidator<IdCardPattern, String> {

    @Override
    public void initialize(IdCardPattern pattern){
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return IdcardUtil.isValidCard(s);
    }
}

