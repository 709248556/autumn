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
import java.util.HashSet;
import java.util.Set;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.autumn.util.StringUtils;

/**
 * 性别验证
 * 
 * @author 老码农 2019-05-09 09:07:56
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = Sex.ValidChecker.class)
public @interface Sex {

	/**
	 * 消息
	 *
	 * @return
	 */
	String message() default "性别只能是男或者女。";

	/**
	 * 允许的值集合
	 * 
	 * @return
	 */
	String[] value() default { "男", "女" };

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		Sex[] value();
	}

	class ValidChecker implements ConstraintValidator<Sex, String> {

		private String message;
		private Set<String> sexSet = new HashSet<>();

		@Override
		public void initialize(Sex constraintAnnotation) {
			message = constraintAnnotation.message();
			sexSet.clear();
			if (constraintAnnotation.value() != null) {
				for (String item : constraintAnnotation.value()) {
					sexSet.add(item.toLowerCase());
				}
			}
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			context.buildConstraintViolationWithTemplate(message);
			if (StringUtils.isNullOrBlank(value)) {
				return true;
			}
			return sexSet.contains(value.toLowerCase());
		}

	}
}
