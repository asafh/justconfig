package io.ous.justconfig.proxy;

import io.ous.justconfig.sources.ConfigurationSource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public interface ConfigurationProxy<T> extends InvocationHandler {

	/**
	 * Returns the ConfigurationSource for this proxy
	 * @return
	 */
	public ConfigurationSource getConfiguration();

	/**
	 * Returns the interface class that's being proxied
	 * @return
	 */
	public Class<T> getTargetInterface();
	/**
	 * Returns the ClassLoader used in the proxy
	 * @return
	 */
	public ClassLoader getClassLoader();
	
	/**
	 * Returns the default value for the property accessed by <code>method</code>
	 * 
	 * @param method
	 * @return
	 */
	public Object defaultValue(Method method);
	
	/**
	 * Returns the expected configuration property type for the given method <br/>
	 * Should general stay the methods return type. <br/>
	 * Any return value which is not a subclass of the method's return type will cause an error on access
	 * @param method
	 * @return the expected
	 */
	public Class<?> getPropertyType(Method method);
	
	/**
	 * Returns the configuration property name that will correspond to the given method <br/>
	 * 
	 * @param method
	 */
	public String getPropertyName(Method method);
}