package io.ous.justconfig.strategies;

import io.ous.justconfig.values.ValueReaderService;

/**
 * A ValueReaderResolverStrategy that queries each ValueReaderService in the iterable and returns the first one that accepts the type
 * @author Asafh
 *
 */
public class IterableValueReaderResolverStrategy implements ValueReaderResolverStrategy {
	private Iterable<ValueReaderService> readers;
	public IterableValueReaderResolverStrategy(Iterable<ValueReaderService> iterable) {
		this.readers = iterable;
		
	}
	
	/**
	 * Creates a new iterator for the given iterable and returns the first ValueReaderService returned by it that accepts the given type
	 */
	@Override
	public ValueReaderService getValueReaderService(Class<?> forType) throws IllegalArgumentException {
		for(ValueReaderService service : readers) {
			if(service.readable(forType)) {
				return service;
			}
		}
		throw new IllegalArgumentException("Cannot find ValueReaderService that accepts "+forType);
	}

}
