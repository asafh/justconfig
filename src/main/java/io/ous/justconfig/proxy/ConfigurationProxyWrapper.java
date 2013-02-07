package io.ous.justconfig.proxy;

import io.ous.justconfig.sources.ConfigurationSource;

import java.lang.reflect.Method;
import java.util.Objects;

public class ConfigurationProxyWrapper<T> implements
		ConfigurationProxy<T> {
	private final ConfigurationProxy<T> wrapped;
	public ConfigurationProxyWrapper(ConfigurationProxy<T> wrapped) {
		Objects.requireNonNull(wrapped);
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
	public Class<T> getTargetInterface() {
		return wrapped.getTargetInterface();
	}
	public ClassLoader getClassLoader() {
		return wrapped.getClassLoader();
	}

}
