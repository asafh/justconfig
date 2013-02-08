package io.ous.justconfig.proxy;

import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.values.ValueReaderService;

import java.lang.reflect.Method;

/**
 * The BeanInterface proxy strips method names of prefixed "get" or "is"
 * 
 * @author Asafh
 * @param <T> the proxied interface type
 */
public class ConfigurationBeanProxyHandler<T> extends ConfigurationProxyHandlerBase<T> {
	protected static final String GET_PREFIX = "get";
	protected static final String IS_PREFIX = "is";
	
	public ConfigurationBeanProxyHandler(Iterable<ValueReaderService> readers, ConfigurationSource config,
											Class<T> clz, ClassLoader loader) {
		super(readers, config, clz, loader);
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
