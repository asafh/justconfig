package io.ous.justconfig.strategies.impl;

import java.lang.reflect.Method;

import io.ous.justconfig.annotation.PropertyAccessor;
import io.ous.justconfig.strategies.CompleteStrategy;
import io.ous.justconfig.util.TypeUtils;
import io.ous.justconfig.values.ValueReaderService;

public class AnnotatedAccessorStrategy implements CompleteStrategy {
	private CompleteStrategy delegate;

	public AnnotatedAccessorStrategy(CompleteStrategy delegate) {
		this.delegate = delegate;
	}
	private PropertyAccessor getAnnotation(Method specMethod) {
		return specMethod.getAnnotation(PropertyAccessor.class);
	}
	
	@Override
	public ValueReaderService getValueReaderService(Method specMethod, Class<?> forType) {
		PropertyAccessor accessor = getAnnotation(specMethod);
		if(accessor == null) {
			return delegate.getValueReaderService(specMethod, forType);
		}
		Class<? extends ValueReaderService> readerClass = accessor.reader();
		if(readerClass == null) {
			return delegate.getValueReaderService(specMethod, forType);
		}
		try {
			return readerClass.newInstance(); //TODO: cache? reader registery? what?
		} catch (Exception e) {
			//TODO: ok, delegate to delegate, but silent ignore? really?
			return delegate.getValueReaderService(specMethod, forType);
		}
	}

	
	@Override
	public Class<?> getPropertyType(Method specMethod) {
		PropertyAccessor accessor = getAnnotation(specMethod);
		if(accessor == null) {
			return delegate.getPropertyType(specMethod);
		}
		Class<?> returnType = accessor.type();
		return returnType == null ? delegate.getPropertyType(specMethod) : returnType;
	}

	@Override
	public String getPropertyName(Method specMethod) {
		PropertyAccessor accessor = getAnnotation(specMethod);
		if(accessor == null) {
			return delegate.getPropertyName(specMethod);
		}
		String propName = accessor.name();
		return propName == null ? delegate.getPropertyName(specMethod) : propName;
	}

	@Override
	public<T> T getDefaultValue(Method method, Class<T> propertyType) {
		PropertyAccessor accessor = getAnnotation(method);
		if(accessor == null) {
			return delegate.getDefaultValue(method, propertyType);
		}
		String def = accessor.defaultValue();
		return def == null ? delegate.getDefaultValue(method, propertyType) : TypeUtils.getValue(def, propertyType);
	}

}
