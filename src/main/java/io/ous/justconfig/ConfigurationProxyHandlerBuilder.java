package io.ous.justconfig;

import java.util.ServiceLoader;

import io.ous.justconfig.proxy.ConfigurationBeanProxyHandler;
import io.ous.justconfig.proxy.ConfigurationProxyHandler;
import io.ous.justconfig.proxy.ConfigurationProxyHandlerBase;
import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.values.ValueReaderService;

/**
 * This class is used to configure and create a new ConfigurationProxyHandler
 * @author Asafh
 *
 */
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
	/**
	 * Creates a new ConfigurationProxyHandlerBuilder builder that will use the default options unless specified <br/
	 * @return the new builder
	 */
	public static ConfigurationProxyHandlerBuilder newBuilder() {
		return new ConfigurationProxyHandlerBuilder(null,null,Thread.currentThread().getContextClassLoader());
	}
	/**
	 * The class loader that will be used by the ConfigurationProxyHandler to define the proxy class with and find Class values <br/>
	 * If no readers were specified to the builder this class loader will also be used when finding the ValueReaderService services. <br/>
	 * Default value is the ContextClassLoader for the thread that created this builder 
	 * @param loader
	 * @return this builder
	 */
	public ConfigurationProxyHandlerBuilder classLoader(ClassLoader loader) {
		this.loader = loader;
		return this;
	}
	/**
	 * The configuration source <br/>
	 * Mandatory
	 * @param config
	 * @return this builder
	 */
	public ConfigurationProxyHandlerBuilder configuration(ConfigurationSource config) {
		this.config = config;
		return this;
	}
	/**
	 * The ValueReaderService that will be available for the ConfigurationProxyHandler <br/>
	 * If no readers are specified, all readers returned by
	 * java.util.ServiceLoader.load(ValueReaderService.class, loader) will be used, where loader is the ClassLoader configured for this builder
	 * @param readers
	 * @return this builder
	 */
	public ConfigurationProxyHandlerBuilder readers(Iterable<ValueReaderService> readers) {
		this.readersSource = readers;
		return this;
	}
	/**
	 * If set, the ConfigurationProxyHandler will interpertate "getXXX" and "isYYY" method names as "XXX"
	 * and "YYY" configuration properties respectively
	 * @return this builder
	 */
	public ConfigurationProxyHandlerBuilder beanNames() {
		this.beanNames = true;
		return this;
	}
	
	/**
	 * Builds the ConfigurationProxyHandler for the given Configuration specification using the supplied options or their defaults.
	 * @param specs the Configuration specification
	 * @return
	 */
	public<T> ConfigurationProxyHandler<T> build(Class<T> specs) {
		if(readersSource == null) {
			readersSource = ServiceLoader.load(ValueReaderService.class, loader);
		}
		if(beanNames) {
			//TODO: Two alternatives: 1. create an annonymous inner class that overrides the getPropertyName and get rid of this class
			//2. Have a boolean flag sent to ConfigurationProxyHandlerBase that will control whether or not property names are bean style
			return new ConfigurationBeanProxyHandler<T>(readersSource, config, specs, loader);
		}
		else {
			return new ConfigurationProxyHandlerBase<T>(readersSource, config, specs, loader);
		}
	}
}
