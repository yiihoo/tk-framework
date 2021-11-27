package net.thinklog.common.kit;

import net.thinklog.common.exception.Asserts;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author 10186
 */
public class ValidKit {

    /**
     * 使用hibernate的注解来进行验证
     */
    private static Validator validator = Validation
            .byProvider(HibernateValidator.class).configure()
            .failFast(true).buildValidatorFactory()
            .getValidator();

    /**
     * 功能描述: <br>
     * 〈注解验证参数〉
     *
     * @param obj
     */
    public static <T> Set<ConstraintViolation<T>> validate(T obj) {
        return validator.validate(obj);
    }

    public static <T> void check(T obj) {
        Set<ConstraintViolation<T>> errs = validator.validate(obj);
        StringBuilder sb = new StringBuilder();
        if (errs.size() > 0) {
            for (ConstraintViolation rs : errs) {
                sb.append(rs.getMessage());
            }
            Asserts.fail(sb.toString());
        }
    }
}
