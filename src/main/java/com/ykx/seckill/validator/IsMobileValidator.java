package com.ykx.seckill.validator;

import com.ykx.seckill.utils.ValidateUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created on 2023/5/4.
 *
 * @author KaiXuan Yang
 */

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = true;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            return ValidateUtil.isMobile(value);
        } else {
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                return ValidateUtil.isMobile(value);
            }
        }
    }
}
