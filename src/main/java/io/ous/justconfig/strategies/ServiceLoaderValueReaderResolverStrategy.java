package io.ous.justconfig.strategies;

import java.util.ServiceLoader;

import io.ous.justconfig.values.ValueReaderService;

public class ServiceLoaderValueReaderResolverStrategy extends IterableValueReaderResolverStrategy {
	public ServiceLoaderValueReaderResolverStrategy(ServiceLoader<ValueReaderService> loader) {
		super(loader);
	}
	public ServiceLoaderValueReaderResolverStrategy(ClassLoader loader) {
		this(ServiceLoader.load(ValueReaderService.class, loader));
	}
}
