package io.ous.justconfig;

import java.lang.reflect.Proxy;

import io.ous.justconfig.proxy.ConfigurationProxy;
import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.values.ValueReaderService;



public abstract class JustConfigToolkit implements Iterable<ValueReaderService> {
	public JustConfigToolkit() {
		
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
	public abstract Object readValue(ClassLoader loader, ConfigurationSource config, String name, Class<?> type);
	
	@SuppressWarnings("unchecked")
	public<T> T proxy(ConfigurationProxy<T> handler) {
		return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] {handler.getTargetInterface()}, handler);
	}
}