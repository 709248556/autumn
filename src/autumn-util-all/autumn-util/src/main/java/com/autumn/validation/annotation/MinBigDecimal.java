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
import java.math.BigDecimal;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.autumn.util.BigDecimalUtils;

/**
 * 最小 BigDecimal 值
 * 
 * @author 老码农 2019-05-09 09:44:27
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = MinBigDecimal.ValidChecker.class)
public @interface MinBigDecimal {

	/**
	 * 消息
	 * 
	 * @return
	 */
	String message() default "{com.autumn.validation.annotation.MinBigDecimal.message}";

	/**
	 * 最小值
	 * 
	 * @return
	 */
	double value();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		MinBigDecimal[] value();
	}

	class ValidChecker implements ConstraintValidator<MinBigDecimal, BigDecimal> {
		private String message;
		private BigDecimal value;

		@Override
		public void initialize(MinBigDecimal constraintAnnotation) {
			this.message = constraintAnnotation.message();
			this.value = new BigDecimal(Double.toString(constraintAnnotation.value()));
		}

		@Override
		public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
			context.buildConstraintViolationWithTemplate(message);
			if (value == null) {
				return true;
			}
			return BigDecimalUtils.ge(value, this.value);
		}
	}
}
