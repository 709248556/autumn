package com.autumn.validation.annotation;

import com.autumn.util.StringUtils;
import com.autumn.validation.MatchesUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 身份证号码校验
 *
 * @author shao
 * @date 2018/1/11 15:25
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = IDCard.ValidChecker.class)
public @interface IDCard {

	/**
	 * 消息
	 *
	 * @return
	 */
	String message() default "身份证号码格式不正确。";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		IDCard[] value();
	}

	class ValidChecker implements ConstraintValidator<IDCard, String> {

		private String message;

		@Override
		public void initialize(IDCard constraintAnnotation) {
			message = constraintAnnotation.message();
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			context.buildConstraintViolationWithTemplate(message);
			if (StringUtils.isNullOrBlank(value)) {
				return true;
			}
			return MatchesUtils.isIdCard(value);
		}

	}
}
