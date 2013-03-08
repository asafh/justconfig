package io.ous.justconfig.strategies.impl;

import java.lang.reflect.Method;

import io.ous.justconfig.annotation.PropertyDefaultValue;
import io.ous.justconfig.annotation.PropertyName;
import io.ous.justconfig.annotation.PropertyType;
import io.ous.justconfig.annotation.PropertyValueReaderService;
import io.ous.justconfig.strategies.CompleteStrategy;
import io.ous.justconfig.util.TypeUtils;
import io.ous.justconfig.values.ValueReaderService;

public class AnnotatedAccessorStrategy implements CompleteStrategy {
	private CompleteStrategy delegate;

	public AnnotatedAccessorStrategy(CompleteStrategy delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public ValueReaderService getValueReaderService(Method specMethod, Class<?> forType) {
		PropertyValueReaderService accessor = specMethod.getAnnotation(PropertyValueReaderService.class);
		if(accessor == null) {
			return delegate.getValueReaderService(specMethod, forType);
		}
		Class<? extends ValueReaderService> readerClass = accessor.value(); //never null
		try {
			return readerClass.newInstance(); //TODO: cache? reader registry? what?
		} catch (Exception e) {
			//TODO: ok, delegate to delegate. But silent ignore? really?
			return delegate.getValueReaderService(specMethod, forType);
		}
	}

	
	@Override
	public Class<?> getPropertyType(Method specMethod) {
		PropertyType accessor = specMethod.getAnnotation(PropertyType.class);
		return accessor == null ? delegate.getPropertyType(specMethod) : accessor.value();
	}

	@Override
	public String getPropertyName(Method specMethod) {
		PropertyName accessor = specMethod.getAnnotation(PropertyName.class);
		return accessor == null ?  delegate.getPropertyName(specMethod) : accessor.value();
	}

	@Override
	public<T> T getDefaultValue(Method specMethod, Class<T> propertyType) {
		PropertyDefaultValue accessor = specMethod.getAnnotation(PropertyDefaultValue.class);
		if(accessor == null) {
			return delegate.getDefaultValue(specMethod, propertyType);
		}
		String def = accessor.value();
		return TypeUtils.getValue(def, propertyType);
	}

}
