package io.ous.justconfig.util;

import static org.mockito.Mockito.mock;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class SimpleMock<T> {
	private T mock;
	Answer<Object> answer;
	public SimpleMock(Class<T> clz) {
		mock = mock(clz, new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation)
					throws Throwable {
				return answer.answer(invocation);
			}
		});
		always(null);
	}
	public T getMock() {
		return mock;
	}
	
	public SimpleMock<T> always(final Object object) {
		return answer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation)
					throws Throwable {
				return object;
			}
		});
	}
	public<E extends Throwable> SimpleMock<T> doThrow(final Class<E> clz) {
		return answer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation)
					throws Throwable {
				throw clz.newInstance();
			}
		});
	}
	public<E extends Throwable> SimpleMock<T> doThrow(final E err) {
		return answer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation)
					throws Throwable {
				throw err;
			}
		});
	}

	public SimpleMock<T> answer(Answer<Object> answer) {
		this.answer = answer;
		return this;
	}

}
