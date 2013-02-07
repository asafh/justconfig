package io.ous.justconfig.proxy;

import io.ous.justconfig.sources.ConfigurationSource;

import java.lang.reflect.Method;

/**
 * The BeanInterface proxy strips method names of prefixed "get" or "is"
 * 
 * @author Asafh
 * @param <T> the proxied interface type
 */
public class ConfigurationBeanInterfaceProxy<T> extends ConfigurationProxyBase<T> {
	protected static final String GET_PREFIX = "get";
	protected static final String IS_PREFIX = "is";
	
	public ConfigurationBeanInterfaceProxy(ConfigurationSource config,
											Class<T> clz, ClassLoader loader) {
		super(config, clz, loader);
	}
	
	/**
	 * The ConfigurationBeanInterface will strip a prefixed "get" or "is" before any method name
	 * @param method
	 */
	@Override
	public String getPropertyName(Method method) {
		String ret = method.getName();
		
		if(ret.startsWith(GET_PREFIX)) {
			return ret.substring(GET_PREFIX.length());
		}
		else if(ret.startsWith(IS_PREFIX)) {
			return ret.substring(IS_PREFIX.length());
		}
		return ret;
	}

}
