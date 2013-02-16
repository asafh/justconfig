package io.ous.justconfig.strategies;

import io.ous.justconfig.values.ValueReaderService;

public interface ValueReaderResolverStrategy {
	/**
	 * Returns a ValueReaderService implementation that can read the type given
	 * @param forType
	 * @return
	 * @throws IllegalArgumentException if no such ValueReaderService can be found
	 */
	public ValueReaderService getValueReaderService(Class<?> forType) throws IllegalArgumentException;
}
