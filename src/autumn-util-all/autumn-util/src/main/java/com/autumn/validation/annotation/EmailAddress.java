package com.autumn.validation.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.autumn.util.StringUtils;
import com.autumn.validation.MatchesUtils;

/**
 * 邮箱地址验证
 * 
 * @author 老码农 2018-12-13 00:35:02
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EmailAddress.ValidChecker.class)
public @interface EmailAddress {

	/**
	 * 消息
	 *
	 * @return
	 */
	String message() default "电子邮件格式不正确。";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		MobilePhone[] value();
	}

	class ValidChecker implements ConstraintValidator<EmailAddress, String> {

		private String message;

		@Override
		public void initialize(EmailAddress constraintAnnotation) {
			message = constraintAnnotation.message();
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			context.buildConstraintViolationWithTemplate(message);
			if (StringUtils.isNullOrBlank(value)) {
				return true;
			}
			return MatchesUtils.isEmail(value);
		}

	}
}
