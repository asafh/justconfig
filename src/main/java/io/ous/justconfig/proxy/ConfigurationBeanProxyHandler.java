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
	protected static final String[] PREFIXES = {GET_PREFIX, IS_PREFIX};
	
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
		String ret = super.getPropertyName(method);
		for(String prefix : PREFIXES) {
			int preLength = prefix.length();
			if(ret.length() <= preLength) { //For the third check
				continue;
			}
			if(ret.startsWith(prefix)) {
				char firstChar = ret.charAt(preLength);
				if(Character.isUpperCase(firstChar)) { //not gettysburgVila() only getTysburgVila
					return Character.toLowerCase(firstChar) + ret.substring(preLength+1); //Plus one for the lowercased char
				}
			}
		}
		return ret;
	}

}
