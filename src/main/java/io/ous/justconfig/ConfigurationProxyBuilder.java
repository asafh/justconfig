package io.ous.justconfig;

import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.strategies.CompleteStrategy;
import io.ous.justconfig.strategies.DefaultValueStrategy;
import io.ous.justconfig.strategies.PropertyNameStrategy;
import io.ous.justconfig.strategies.PropertyTypeStrategy;
import io.ous.justconfig.strategies.ValueReaderResolverStrategy;
import io.ous.justconfig.strategies.impl.AnnotatedAccessorStrategy;
import io.ous.justconfig.strategies.impl.AnnotationSpecDefaultValueStrategy;
import io.ous.justconfig.strategies.impl.BeanMethodPropetyStrategy;
import io.ous.justconfig.strategies.impl.ComposedCompleteStrategy;
import io.ous.justconfig.strategies.impl.IterableValueReaderResolverStrategy;
import io.ous.justconfig.strategies.impl.MethodPropetyStrategy;
import io.ous.justconfig.strategies.impl.ServiceLoaderValueReaderResolverStrategy;
import io.ous.justconfig.values.ValueReaderService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * This class is used to configure and create a new ConfigurationProxyHandler. <br/>
 * A ConfigurationProxyBuilder instance is not thread safe.
 * @author Asafh
 *
 */
public class ConfigurationProxyBuilder {
	private ValueReaderResolverStrategy valueReaderResolver;
	private PropertyNameStrategy propertyNameStrategy; 
	private PropertyTypeStrategy propertyTypeStrategy;
	private DefaultValueStrategy defaultValueStrategy;
	private ConfigurationSource config;
	private ClassLoader loader;
	private CacheMode cacheMode;
	private boolean readAnnotations;
	public static enum CacheMode {
		/**
		 * Every call to a configuration property accessor will read the values from the Configuration Source
		 */
		None,
		/**
		 * The first call to a configuration property accessor will cause a read<br/>
		 * This <b>does not</b> guarantee each property will only be read once. Multiple reads can occur when other requests are made while the first one is being resolved.
		 */
		Lazy,
		/**
		 * Instantiating will read all of the configuration properties and following invocations of property accessors will <b>not</b> cause a read <br/>
		 * Failure to eagerily read a property will not cache it and the next accessor will attempt another read.
		 */
		Eager
	}
	protected ConfigurationProxyBuilder(ConfigurationSource config, ClassLoader loader) {
		this.config = config;
		classLoader(loader);
		cached(CacheMode.Lazy);
		readAnnotations = false;
	}
	
	/**
	 * Creates a new ConfigurationProxyHandlerBuilder builder that will use the default options unless specified <br/
	 * @return the new builder
	 */
	public static ConfigurationProxyBuilder newBuilder(ConfigurationSource config) {
		return new ConfigurationProxyBuilder(config, Thread.currentThread().getContextClassLoader());
	}
	/**
	 * The class loader that will be used by the ConfigurationProxyHandler to define the proxy class with and that will be supplied to ValueReaderServices when looking up values such as Classes<br/>
	 * If no {@link ValueReaderResolverStrategy} was specified this loader will also be supplied to the default strategy {@link ServiceLoaderValueReaderResolverStrategy} <br/>
	 * Default value is the ContextClassLoader for the thread that created this builder 
	 * @param loader
	 * @return this builder
	 * @see ServiceLoaderValueReaderResolverStrategy#ServiceLoaderValueReaderResolverStrategy(ClassLoader)
	 * @see Thread#getContextClassLoader()
	 */
	public ConfigurationProxyBuilder classLoader(ClassLoader loader) {
		this.loader = loader;
		return this;
	}
	/**
	 * Sets the {@link CacheMode} for this proxy <br/>
	 * Default value is {@link CacheMode#Lazy}
	 * @param mode the CacheMode
	 * @return
	 * @see CacheMode
	 * @see CacheMode#Lazy
	 */
	public ConfigurationProxyBuilder cached(CacheMode mode) {
		this.cacheMode = mode;
		return this;
	}

	/**
	 * The ValueReaderService that will be available for the ConfigurationProxyHandler <br/>
	 * If no readers are specified, all readers returned by
	 * java.util.ServiceLoader.load(ValueReaderService.class, loader) will be used, where loader is the ClassLoader configured for this builder
	 * @param readers
	 * @return this builder
	 */
	public ConfigurationProxyBuilder valueReaderResolverStrategy(ValueReaderResolverStrategy strategy) {
		this.valueReaderResolver = strategy;
		return this;
	}
	/**
	 * Sets a ValueReaderResolverStrategy that will query the given ValueReaderServices
	 * @param services
	 * @return
	 * @see IterableValueReaderResolverStrategy#IterableValueReaderResolverStrategy(Iterable)
	 */
	public ConfigurationProxyBuilder valueReaderResolvers(Iterable<ValueReaderService> services) {
		return valueReaderResolverStrategy(new IterableValueReaderResolverStrategy(services));
	}
	/**
	 * A shorthand to propertyNameStrategy(new BeanMethodPropetyStrategy()) <br/>
	 * 
	 * @return this builder
	 * @see BeanMethodPropetyStrategy
	 * @see ConfigurationProxyBuilder#propertyNameStrategy(PropertyNameStrategy)
	 */
	public ConfigurationProxyBuilder beanNames() {
		return propertyNameStrategy(new BeanMethodPropetyStrategy());
	}
	
	public ConfigurationProxyBuilder readAnnotations() {
		readAnnotations = true;
		return this;
	}
	
	public ConfigurationProxyBuilder propertyNameStrategy(PropertyNameStrategy strategy) {
		this.propertyNameStrategy = strategy;
		return this;
	}
	
	public ConfigurationProxyBuilder propertyTypeStrategy(PropertyTypeStrategy strategy) {
		this.propertyTypeStrategy = strategy;
		return this;
	}
	
	public ConfigurationProxyBuilder defaultValueStrategy(DefaultValueStrategy strategy) {
		this.defaultValueStrategy = strategy;
		return this;
	}
	
	/**
	 * Builds an instance of <code>specs</code> using the given for the given Configuration specification using the supplied options or their defaults.
	 * @param specs the Configuration specification
	 * @return
	 */
	public<T> T build(Class<T> specs) {
		
		InvocationHandler handler = buildInvocationHandler(specs);
		T ret = specs.cast(Proxy.newProxyInstance(loader, new Class[] {specs}, handler));
		
		if(cacheMode == CacheMode.Eager) {
			Object[] args = new Object[0];
			for(Method method : specs.getMethods()) {
				if(method.getParameterTypes().length != 0) {
					continue; //Ignoring illegal methods
				}
				try {
					handler.invoke(ret, method, args); //Caching handled by CachedInvocationHandler
				} catch (Throwable e) {
					//Ignore, see CacheMode#Eager
				}
			}
		}
		return ret;
	}
	/**
	 * Creates StrategyProxyInvocationHandler combining the given or default strategies/ClassLoader and the given configuration. <br/>
	 * If cacheMode is other than {@link CacheMode#None} then the handler is wrapped with a CachedInvocationHandler
	 * @param specs
	 * @return
	 */
	protected<T> InvocationHandler buildInvocationHandler(Class<T> specs) {
		if(propertyNameStrategy == null) {
			propertyNameStrategy = MethodPropetyStrategy.METHOD_PROPERTY_STRATEGY_INSTANCE;
		}
		if(propertyTypeStrategy == null) {
			propertyTypeStrategy = MethodPropetyStrategy.METHOD_PROPERTY_STRATEGY_INSTANCE;
		}
		if(defaultValueStrategy == null) {
			defaultValueStrategy = new AnnotationSpecDefaultValueStrategy();
		}
		if(valueReaderResolver == null) {
			valueReaderResolver = new ServiceLoaderValueReaderResolverStrategy(loader);
		}
		
		CompleteStrategy strategy = new ComposedCompleteStrategy(propertyNameStrategy,
																propertyTypeStrategy,
																defaultValueStrategy,
																valueReaderResolver); 
		if(readAnnotations) {
			strategy = new AnnotatedAccessorStrategy(strategy);
		}
		
		InvocationHandler handler = new StrategyProxyInvocationHandler(config, loader, strategy);
		
		if(cacheMode != CacheMode.None) {
			handler = new CachedInvocationHandler(handler);
			
		}
		return handler;
	}
}
