package io.ous.justconfig.proxy;

import io.ous.justconfig.sources.ConfigurationSource;

import java.lang.reflect.Method;

public class ConfigurationProxyWrapper<T> extends AbstractConfigurationProxyHandler<T> {
	private final ConfigurationProxyHandler<T> wrapped;
	
	public ConfigurationProxyWrapper(ConfigurationProxyHandler<T> wrapped) {
		if(wrapped == null) {
			throw new NullPointerException("Wrapped handler cannot be null");
		}
		this.wrapped = wrapped;
	}
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return wrapped.invoke(proxy, method, args);
	}

	public ConfigurationSource getConfiguration() {
		return wrapped.getConfiguration();
	}

	
	public Object defaultValue(Method method) {
		return wrapped.defaultValue(method);
	}

	public Class<?> getPropertyType(Method method) {
		return wrapped.getPropertyType(method);
	}

	public String getPropertyName(Method method) {
		return wrapped.getPropertyName(method);
	}
	public Class<T> getSpecs() {
		return wrapped.getSpecs();
	}
	public ClassLoader getClassLoader() {
		return wrapped.getClassLoader();
	}
}
