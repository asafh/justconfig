package io.ous.justconfig.strategies.impl;

import java.lang.reflect.Method;

import io.ous.justconfig.strategies.ValueReaderResolverStrategy;
import io.ous.justconfig.values.ValueReaderService;

/**
 * Allows chaining ValueReaderResolverStrategy objects by returning the first ValueReaderService 
 * @author Asafh
 *
 */
public class ValueReaderResolverChain implements ValueReaderResolverStrategy {
	private ValueReaderResolverStrategy[] strategies;
	public ValueReaderResolverChain(ValueReaderResolverStrategy... strategies) {
		this.strategies = strategies;
	}
	@Override
	public ValueReaderService getValueReaderService(Method specMethod,Class<?> forType) {
		for(ValueReaderResolverStrategy strategy : strategies) {
			ValueReaderService ret = strategy.getValueReaderService(specMethod, forType);
			if(ret != null) {
				return ret;
			}
		}
		return null;
	}

}
