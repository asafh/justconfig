package io.ous.justconfig;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A thread safe wrapper for an invocation handler, caching return values for method invocations.
 * (key is just the method, parameters values if any are ignored) <br/>
 * In case of two or more concurrent invocations for a previous uncached method,
 * any number of invocations might pass through any result may be cached.
 * Once any such invocation was cached the other concurrent invocation's return value will be ignored
 * and the cached value will be returned. <br/>
 * This assures that, although when there are several initial concurrent invocations more than one can pass through,
 * the same value will always be returned by this InvocationHandler
 * @author Asafh
 *
 */
class CachedInvocationHandler implements InvocationHandler {
	private static final Object NULL_VALUE = new Object(); //Placeholder for null values...
	private InvocationHandler wrapped;
	private ConcurrentHashMap<Method, Object> cache;
	public CachedInvocationHandler(InvocationHandler wrapped) {
		this.wrapped = wrapped;
		this.cache = new ConcurrentHashMap<Method, Object>();
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if(cache.containsKey(method)) { //already cached
			Object cached = cache.get(method);
			return unwrapNull(cached);
		}
		
		Object ret = wrapped.invoke(proxy, method, args), //return value can be null
				safeRet = wrapNull(ret);  //wrap it if so
		
		
		Object safePreviousValue = cache.putIfAbsent(method, safeRet); //not override if we already cached (other concurrent invocation)
		
		//If we already cached something before the putIfAbsent, we need to return that value (and unwrap it if it is a null), ret is already unwrapped
		return safePreviousValue == null ? ret : unwrapNull(safePreviousValue); 
	}
	private Object unwrapNull(Object object) {
		return object == NULL_VALUE ? null : object;
	}
	private Object wrapNull(Object object) {
		return object == null ? NULL_VALUE : object;
	}

}
