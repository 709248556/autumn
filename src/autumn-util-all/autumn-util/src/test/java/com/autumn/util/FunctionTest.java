package com.autumn.util;

import org.junit.Test;

import com.autumn.util.function.FunctionOneAction;

public class FunctionTest {

	@Test
	public void test1() {

		t1(new E1<Long>(), 9L);

		t1(new FunctionOneAction<Long>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7218135249313803224L;

			@Override
			public void apply(Long t) {
				System.out.println(t.toString());

			}
		}, 10L);

		t1(i -> {
			System.out.println(i.toString());
		}, 89L);
	}

	private <E> void t1(FunctionOneAction<E> fun, E value) {
		fun.apply(value);
	}

	class E1<E> implements FunctionOneAction<E> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6963128018339530572L;

		@Override
		public void apply(E t) {
			System.out.println(t.toString());
		}
	}

}
