package io.ous.justconfig;

import java.util.ServiceLoader;

import io.ous.justconfig.proxy.ConfigurationBeanProxyHandler;
import io.ous.justconfig.proxy.ConfigurationProxyHandler;
import io.ous.justconfig.proxy.ConfigurationProxyHandlerBase;
import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.values.ValueReaderService;

public class ConfigurationProxyHandlerBuilder {
	private Iterable<ValueReaderService> readersSource;
	private ConfigurationSource config;
	private ClassLoader loader;
	private boolean beanNames;
	protected ConfigurationProxyHandlerBuilder(Iterable<ValueReaderService> readers,
										ConfigurationSource config, ClassLoader loader) {
		readers(readers);
		configuration(config);
		classLoader(loader);
		beanNames = false;
	}
	public static ConfigurationProxyHandlerBuilder newBuilder() {
		return new ConfigurationProxyHandlerBuilder(null,null,Thread.currentThread().getContextClassLoader());
	}
	
	public ConfigurationProxyHandlerBuilder classLoader(ClassLoader loader) {
		this.loader = loader;
		return this;
	}
	public ConfigurationProxyHandlerBuilder configuration(ConfigurationSource config) {
		this.config = config;
		return this;
	}
	public ConfigurationProxyHandlerBuilder readers(Iterable<ValueReaderService> readers) {
		this.readersSource = readers;
		return this;
	}
	public ConfigurationProxyHandlerBuilder beanNames() {
		this.beanNames = true;
		return this;
	}
	
	
	public<T> ConfigurationProxyHandler<T> build(Class<T> specs) {
		if(readersSource == null) {
			readersSource = ServiceLoader.load(ValueReaderService.class, loader);
		}
		if(beanNames) { //TODO: maybe I should just create an annonymous inner class that overrides the getPropertyName and get rid of this class
			return new ConfigurationBeanProxyHandler<T>(readersSource, config, specs, loader);
		}
		else {
			return new ConfigurationProxyHandlerBase<T>(readersSource, config, specs, loader);
		}
	}
}
