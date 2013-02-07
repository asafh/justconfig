package io.ous.justconfig.proxy;

import io.ous.justconfig.DefaultJustConfigToolkit;
import io.ous.justconfig.sources.ConfigurationSource;

import java.lang.reflect.Method;

/**
 * The base implementation for the ConfigurationProxy that does everything straightforward <br/>:
 * <ul>
 * <li>There are no default values (String, class, enum constants and wrapped primitives will return null, unwrapped primitives will cause an error on access)</li>
 * <li>Property name is the method's name</li>
 * <li>Property type is the method's return type</li>
 * 
 * @author Asafh
 *
 * @param <T> the interface type
 */
public class ConfigurationProxyBase<T> implements ConfigurationProxy<T> {
	private final ConfigurationSource config;

	private final ClassLoader loader;
	private final Class<T> clz;
	
	public ConfigurationProxyBase(ConfigurationSource config, Class<T> clz, ClassLoader loader) {
		this.config = config;
		this.clz = clz;
		this.loader = loader;
	}
	/* (non-Javadoc)
	 * @see io.ous.justconfig.ConfigurationInterfaceProxy#getConfiguration()
	 */
	public ConfigurationSource getConfiguration() {
		return config;
	}
	
	public Class<T> getTargetInterface() {
		return clz;
	}
	public ClassLoader getClassLoader() {
		return loader;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String name = getPropertyName(method);
		Class<?> type = getPropertyType(method);
		Object ret = DefaultJustConfigToolkit.INSTANCE.readValue(loader, config, name, type );
		return ret == null ? defaultValue(method) : ret;
	}
	/**
	 * The default implementation always returns null (and therefore does not allow missing values for primitive types!)
	 */
	public Object defaultValue(Method method) {
		return null;
	}
	
	/**
	 * Returns the method's return type <br/>
	 * Should generally not be overridden
	 */
	public Class<?> getPropertyType(Method method) {
		return method.getReturnType();
	}
	/**
	 * Default implementation returns the method name.
	 * @param method
	 * 
	 */
	public String getPropertyName(Method method) {
		return method.getName();
	}

}
