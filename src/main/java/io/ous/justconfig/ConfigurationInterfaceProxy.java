package io.ous.justconfig;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class ConfigurationInterfaceProxy<T> implements InvocationHandler {
	private final ConfigurationSource config;
	private final T proxy;
	public ConfigurationInterfaceProxy(ConfigurationSource config, Class<T> clz) {
		this(config,clz, Thread.currentThread().getContextClassLoader());
	}
	public ConfigurationInterfaceProxy(ConfigurationSource config, Class<T> clz, ClassLoader loader) {
		this.config = config;
		this.proxy = clz.cast(Proxy.newProxyInstance(loader, new Class[] {clz}, this));
	}
	public ConfigurationSource getConfiguration() {
		return config;
	}
	public T getProxy() {
		return proxy;
	}
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return null;
	}

}
