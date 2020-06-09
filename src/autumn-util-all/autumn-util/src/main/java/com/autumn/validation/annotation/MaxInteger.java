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

/**
 * 最大 Integer 值
 * 
 * @author 老码农 2019-05-09 09:22:34
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = MaxInteger.ValidChecker.class)
public @interface MaxInteger {
	
	/**
	 * 消息
	 * 
	 * @return
	 */
	String message() default "{com.autumn.validation.annotation.MaxInteger.message}";

	/**
	 * 最小值
	 * 
	 * @return
	 */
	int value();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		MaxInteger[] value();
	}

	class ValidChecker implements ConstraintValidator<MaxInteger, Integer> {
		private String message;
		private int value;

		@Override
		public void initialize(MaxInteger constraintAnnotation) {
			this.message = constraintAnnotation.message();
			this.value = constraintAnnotation.value();
		}

		@Override
		public boolean isValid(Integer value, ConstraintValidatorContext context) {
			context.buildConstraintViolationWithTemplate(message);
			if (value == null) {
				return true;
			}
			return value <= this.value;
		}
	}
}
