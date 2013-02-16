package io.ous.justconfig;

import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.strategies.DefaultValueStrategy;
import io.ous.justconfig.strategies.PropertyNameStrategy;
import io.ous.justconfig.strategies.PropertyTypeStrategy;
import io.ous.justconfig.strategies.ValueReaderResolverStrategy;
import io.ous.justconfig.values.ValueReaderService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class StrategyProxyInvocationHandler implements InvocationHandler {
	private final PropertyNameStrategy propertyNameStrategy;
	private final PropertyTypeStrategy propertyTypeStrategy;
	private final DefaultValueStrategy defaultValueStrategy;
	private final ValueReaderResolverStrategy valueReaderResolver;
	private final ClassLoader loader;
	private final ConfigurationSource config;
	public StrategyProxyInvocationHandler(
			ConfigurationSource config,
			ClassLoader loader,
			PropertyNameStrategy propertyNameStrategy,
			PropertyTypeStrategy propertyTypeStrategy,
			DefaultValueStrategy defaultValueStrategy,
			ValueReaderResolverStrategy valueReaderResolver) {
				this.config = config;
				this.loader = loader;
				this.propertyNameStrategy = propertyNameStrategy;
				this.propertyTypeStrategy = propertyTypeStrategy;
				this.defaultValueStrategy = defaultValueStrategy;
				this.valueReaderResolver = valueReaderResolver;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String name = propertyNameStrategy.getPropertyName(method);
		Class<?> type = propertyTypeStrategy.getPropertyType(method);
		Object ret = readValue(name, type);
		return ret == null ? defaultValueStrategy.getDefaultValue(method) : ret;
	}
	/**
	 * Reads the configuration property <code>name</code> from <code>config</code> to represent an object of type <code>type</code> <br/>
	 *   
	 * @param config
	 * @param name
	 * @param type
	 * @return
	 */
	protected Object readValue(String name, Class<?> type) {
		ValueReaderService reader = valueReaderResolver.getValueReaderService(type);
		return reader.readValue(loader, config, name, type);
	}
}
