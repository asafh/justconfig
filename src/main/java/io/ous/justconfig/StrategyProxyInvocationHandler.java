package io.ous.justconfig;

import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.strategies.CompleteStrategy;
import io.ous.justconfig.values.ValueReaderService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class StrategyProxyInvocationHandler implements InvocationHandler {
	private final ClassLoader loader;
	private final ConfigurationSource config;
	private final CompleteStrategy strategy;
	public StrategyProxyInvocationHandler(
			ConfigurationSource config,
			ClassLoader loader,
			CompleteStrategy strategy) {
				this.config = config;
				this.loader = loader;
				this.strategy = strategy;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		//Applying strategies
		String name = strategy.getPropertyName(method);
		Class<? extends Object> type = strategy.getPropertyType(method);
		ValueReaderService reader = strategy.getValueReaderService(method, type);
		
		if(reader == null) {
			throw new IllegalArgumentException("Cannot find ValueReaderService that accepts "+method);
		}
		
		//Acting on strategies:
		Object ret = reader.readValue(loader, config, name, type);

		//Resorting to default strategy if needed.
		return ret == null ? strategy.getDefaultValue(method, type) : ret;
	}
}
