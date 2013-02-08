package io.ous.justconfig.proxy;

import java.lang.reflect.Proxy;

/**
 * This class implements the createProxy method that will instantiate a spec for this proxy handler 
 * 
 * @author Asafh
 *
 * @param <T> the interface type
 */
abstract class AbstractConfigurationProxyHandler<T> implements ConfigurationProxyHandler<T> {
	public AbstractConfigurationProxyHandler() {
	}
	
	public T createProxy() {
		Class<T> specs = getSpecs();
		return specs.cast(Proxy.newProxyInstance(getClassLoader(), new Class[] {specs}, this));
	}

}
