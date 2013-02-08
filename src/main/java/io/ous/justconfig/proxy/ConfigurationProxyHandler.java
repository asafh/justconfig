package io.ous.justconfig.proxy;

import io.ous.justconfig.sources.ConfigurationSource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public interface ConfigurationProxyHandler<T> extends InvocationHandler {
	/**
	 * Returns the ConfigurationSource that will be used to resolve values in the configuration specification
	 * @return
	 */
	public ConfigurationSource getConfiguration();

	/**
	 * Returns the configuration specification class
	 * @return
	 */
	public Class<T> getSpecs();
	
	/**
	 * Returns the ClassLoader used by the handler when reading values (e.g. resolving classes)
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
	
	/**
	 * Returns an instance of the target interface, calling it's methods will retrieve
	 * configuration properties as described by the proxy implementation 
	 * @return
	 */
	public T createProxy();
}