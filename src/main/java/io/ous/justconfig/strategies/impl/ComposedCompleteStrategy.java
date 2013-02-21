package io.ous.justconfig.strategies.impl;

import java.lang.reflect.Method;

import io.ous.justconfig.strategies.CompleteStrategy;
import io.ous.justconfig.strategies.DefaultValueStrategy;
import io.ous.justconfig.strategies.PropertyNameStrategy;
import io.ous.justconfig.strategies.PropertyTypeStrategy;
import io.ous.justconfig.strategies.ValueReaderResolverStrategy;
import io.ous.justconfig.values.ValueReaderService;

public class ComposedCompleteStrategy implements CompleteStrategy {
	private final PropertyNameStrategy propertyNameStrategy;
	private final PropertyTypeStrategy propertyTypeStrategy;
	private final DefaultValueStrategy defaultValueStrategy;
	private final ValueReaderResolverStrategy valueReaderResolver;
	
	public ComposedCompleteStrategy(PropertyNameStrategy propertyNameStrategy,
			PropertyTypeStrategy propertyTypeStrategy,
			DefaultValueStrategy defaultValueStrategy,
			ValueReaderResolverStrategy valueReaderResolver) {
				this.propertyNameStrategy = propertyNameStrategy;
				this.propertyTypeStrategy = propertyTypeStrategy;
				this.defaultValueStrategy = defaultValueStrategy;
				this.valueReaderResolver = valueReaderResolver;
	};
	@Override
	public <T> T getDefaultValue(Method method, Class<T> propertyType) {
		return defaultValueStrategy.getDefaultValue(method, propertyType);
	}

	@Override
	public String getPropertyName(Method method) {
		return propertyNameStrategy.getPropertyName(method);
	}

	@Override
	public Class<?> getPropertyType(Method method) {
		return propertyTypeStrategy.getPropertyType(method);
	}

	@Override
	public ValueReaderService getValueReaderService(Method specMethod,
			Class<?> forType) {
		return valueReaderResolver.getValueReaderService(specMethod, forType);
	}

}
