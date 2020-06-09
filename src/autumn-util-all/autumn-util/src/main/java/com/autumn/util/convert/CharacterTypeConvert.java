package com.autumn.util.convert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:30:23
 */
public class CharacterTypeConvert extends CharacterConvert {

	@Override
	public Character getDefaultValue() {
		return '\u0000';
	}

	@Override
	public Class<?> getType() {
		return Character.TYPE;
	}
}
