package io.ous.justconfig.proxy;

import io.ous.justconfig.sources.ConfigurationSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * This class proxies an annotation, using the properties names as is and the declared defaults values
 * 
 * @author Asafh
 *
 * @param <T> the annotation type
 */
public class ConfigurationAnnotationProxy<T extends Annotation> extends ConfigurationProxyBase<T> {

	public ConfigurationAnnotationProxy(ConfigurationSource config,
			Class<T> clz, ClassLoader loader) {
		super(config, clz, loader);
	}

	/**
	 * Returns the specified default value on the annotation property
	 */
	@Override
	public Object defaultValue(Method method) {
		return method.getDefaultValue();
	}
}
