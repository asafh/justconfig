package io.ous.justconfig.proxy;

import io.ous.justconfig.sources.ConfigurationSource;

import java.lang.reflect.Method;

/**
 * A convenience class to wrap an existing ConfigurationProxyHandler instance <br/>
 * Every method call unless overridden delegates to the wrapped instance.
 * 
 * @author Asafh
 *
 * @param <T>
 */
public class ConfigurationProxyWrapper<T> extends AbstractConfigurationProxyHandler<T> {
	/**
	 * Wrapped instance
	 */
	private final ConfigurationProxyHandler<T> wrapped;
	/**
	 * Creates a new ConfigurationProxyWrapper with the given ConfigurationProxyHandler backing
	 * @param wrapped the ConfigurationProxyHandler to back this wrapper
	 * @throws NullPointerException if the given ConfigurationProxyHandler is null.
	 */
	public ConfigurationProxyWrapper(ConfigurationProxyHandler<T> wrapped) throws NullPointerException {
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
	@Override
	public T createProxy() {
		return wrapped.createProxy();
	}
}
