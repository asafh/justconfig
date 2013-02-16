package io.ous.justconfig.strategies;

import io.ous.justconfig.values.ValueReaderService;

public interface ValueReaderResolverStrategy {
	/**
	 * Returns a ValueReaderService implementation that can read the type given or null if none was found
	 * @param forType
	 * @return
	 */
	public ValueReaderService getValueReaderService(Class<?> forType);
}
