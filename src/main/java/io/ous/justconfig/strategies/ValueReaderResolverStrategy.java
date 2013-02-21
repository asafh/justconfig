package io.ous.justconfig.strategies;

import java.lang.reflect.Method;

import io.ous.justconfig.values.ValueReaderService;

public interface ValueReaderResolverStrategy {
	/**
	 * Returns a ValueReaderService implementation that can read the a value for the given configuration spec method,
	 * with the expected output type (as determined by the PropertyTypeStrategy)
	 * @param specMethod
	 * @param forType 
	 * @return
	 */
	public ValueReaderService getValueReaderService(Method specMethod, Class<?> forType);
}
