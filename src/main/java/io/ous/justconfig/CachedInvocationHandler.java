package io.ous.justconfig;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

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
		if(cache.containsKey(method)) {
			Object cached = cache.get(method);
			if(cached == NULL_VALUE) {
				cached = null;
			}
			return cached;
		}
		Object toCache = wrapped.invoke(proxy, method, args),
				ret = toCache;
		
		if(toCache == null) {
			toCache = NULL_VALUE;
		}
		
		cache.putIfAbsent(method, toCache);
		return ret;
	}

}
