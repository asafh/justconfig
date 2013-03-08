package io.ous.justconfig;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import io.ous.concurrentunit.ExecutionTarget;
import io.ous.concurrentunit.ExecutionTargetBase;
import io.ous.concurrentunit.ExecutionTargetRunner;
import io.ous.concurrentunit.execution.ImmediateExecutionStrategy;
import io.ous.concurrentunit.execution.ScheduledExecutionStrategy;
import io.ous.concurrentunit.util.Throwables;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class CachedInvocationHandlerUnitTest {
	private static final Object PROXY = new Object(); 
	private static final Object[] NO_ARGS = new Object[0];
	private static final Method x = method("x");
//	private static final Method y = method("x");
	private static interface Spec {
		String x();
		String y();
	}
	private abstract static class CachedInvocationHandlerTestExecutionTarget<V> extends ExecutionTargetBase<V> {
		protected CachedInvocationHandler cachedInvocationHandler;
		protected InvocationHandler wrapped;
		protected Object unique;
		@Override
		public void before() throws Throwable {
			wrapped = mock(InvocationHandler.class);
			cachedInvocationHandler = new CachedInvocationHandler(wrapped);
			unique = new Object();
			when(wrapped.invoke(PROXY, x, NO_ARGS)).thenReturn(unique);
		}

		
	}
	

	
	
	@Test
	public void testCached() throws SecurityException, Throwable {
		final AtomicInteger i = new AtomicInteger();
		ExecutionTargetRunner runner = new ExecutionTargetRunner(new ScheduledExecutionStrategy(50, 100, 50));
		runner.run(new CachedInvocationHandlerTestExecutionTarget<Void>() {
			@Override
			public Void call() throws Exception {
				System.out.println(i.getAndIncrement()+" "+ System.currentTimeMillis());
				try {
					assertEquals(unique, cachedInvocationHandler.invoke(PROXY, x, NO_ARGS));
					assertEquals(unique,cachedInvocationHandler.invoke(PROXY, x, NO_ARGS));
					assertEquals(unique,cachedInvocationHandler.invoke(PROXY, x, NO_ARGS));
					assertEquals(unique,cachedInvocationHandler.invoke(PROXY, x, NO_ARGS));
					
					return null;
				}
				catch (Throwable e) {
					return Throwables.asException(e);
				}
			}
			@Override
			public void after() throws Throwable {
				verify(wrapped, times(1)).invoke(PROXY, x, NO_ARGS);
				verifyNoMoreInteractions(wrapped);
			}
		});
	}
	
	@Test
	public void testCachedImmediate() throws SecurityException, Throwable {
		final AtomicInteger i = new AtomicInteger();
		ExecutionTargetRunner runner = new ExecutionTargetRunner(new ImmediateExecutionStrategy(120));
		runner.run(new CachedInvocationHandlerTestExecutionTarget<Void>() {
			@Override
			public void before() throws Throwable {
				super.before();
				assertEquals(unique, cachedInvocationHandler.invoke(PROXY, x, NO_ARGS)); //Cache once
			}
			@Override
			public Void call() throws Exception {
				System.out.println(i.getAndIncrement()+" "+ System.currentTimeMillis());
				try {
					assertEquals(unique, cachedInvocationHandler.invoke(PROXY, x, NO_ARGS));
					assertEquals(unique,cachedInvocationHandler.invoke(PROXY, x, NO_ARGS));
					assertEquals(unique,cachedInvocationHandler.invoke(PROXY, x, NO_ARGS));
					assertEquals(unique,cachedInvocationHandler.invoke(PROXY, x, NO_ARGS));
					
					return null;
				}
				catch (Throwable e) {
					return Throwables.asException(e);
				}
			}
			@Override
			public void after() throws Throwable {
				verify(wrapped, times(1)).invoke(PROXY, x, NO_ARGS);
				verifyNoMoreInteractions(wrapped);
			}
		});
	}


	private static Method method(String name) {
		try {
			return Spec.class.getMethod(name);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
