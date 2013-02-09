package io.ous.justconfig.proxy;

import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.values.ValueReaderService;

import java.lang.reflect.Method;

/**
 * The base implementation for the ConfigurationProxy that does everything straightforward <br/>:
 * <ul>
 * <li>Default values are supplied only for specs that are annotation classes for methods with declared default value</li>
 * <li>Property name is the method's name</li>
 * <li>Property type is the method's return type</li>
 * </ul>
 * @author Asafh
 *
 * @param <T> the specs type
 */
public class ConfigurationProxyHandlerBase<T> extends AbstractConfigurationProxyHandler<T>  {
	private final ConfigurationSource config;

	private final ClassLoader loader;
	private final Class<T> clz;

	private Iterable<ValueReaderService> readers;
	
	public ConfigurationProxyHandlerBase(Iterable<ValueReaderService> readers, ConfigurationSource config, Class<T> clz, ClassLoader loader) {
		this.readers = readers;
		this.config = config;
		this.clz = clz;
		this.loader = loader;
	}
	public ConfigurationProxyHandlerBase(Iterable<ValueReaderService> readers,ConfigurationSource config, Class<T> clz) {
		this(readers, config,clz,Thread.currentThread().getContextClassLoader());
	}
	/* (non-Javadoc)
	 * @see io.ous.justconfig.ConfigurationInterfaceProxy#getConfiguration()
	 */
	public ConfigurationSource getConfiguration() {
		return config;
	}
	
	public Class<T> getSpecs() {
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
		Object ret = readValue(name, type );
		return ret == null ? defaultValue(method) : ret;
	}
	
	/**
	 * Reads the configuration property <code>name</code> from <code>config</code> to represent an object of type <code>type</code> <br/>
	 * <code>loader</code> might be used by value readers  
	 * @param loader
	 * @param config
	 * @param name
	 * @param type
	 * @return
	 */
	protected Object readValue(String name, Class<?> type) {
		for(ValueReaderService reader : readers) {
			if(reader.readable(type)) {
				return reader.readValue(getClassLoader(), config, name, type);
			}
		}
		return null;
	}
	
	/**
	 * If the specs interface is an annotation, the default values on each property will be used. <br/>
	 * For non-annotation specs and for annotation methods with no default a null will be returned <br/>
	 * Having no configuration value and no default value for (unboxed) primitive types will cause a null pointer exception on access.
	 */
	public Object defaultValue(Method method) {
		return method.getDefaultValue();
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
