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

/**
 * 非null值和非空白值
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-31 01:43:56
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = NotNullOrBlank.ValidChecker.class)
public @interface NotNullOrBlank {

	/**
	 * 消息
	 * 
	 * @return
	 */
	String message() default "{com.autumn.validation.annotation.NotNullOrBlank.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		NotNullOrBlank[] value();
	}
	
	class ValidChecker implements ConstraintValidator<NotNullOrBlank, String> {

		private String message;

		@Override
		public void initialize(NotNullOrBlank constraintAnnotation) {
			message = constraintAnnotation.message();
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			context.buildConstraintViolationWithTemplate(message);
			return !StringUtils.isNullOrBlank(value);
		}

	}
}
