package com.autumn.validation;

/**
 * 默认数据验证
 * 
 * @author 老码农 2019-03-16 16:16:18
 */
public class DefaultDataValidation implements DataValidation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8501698572121413438L;

	@Override
	public void valid() {
		ValidationUtils.validation(this);
	}

}
