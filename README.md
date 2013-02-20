# The Justconfig library

The Justconfig library provides easy access to configuration properties. By simply specifying an interface describing your configuration values you can access those values without a hassle, independent of their source.  
Justconfig has no dependencies.


#Why would I use it?
If you want to avoid parsing and navigating various configuration sources and instead
have a slick, nifty, object which lets you access your configuration, returning values with the compile time type you expect.  
Take a look at a sample!


## Simple Example
This example showcases a simple usage, leveraging annotation interfaces' ability to specify default values with method declaration


```java
public class Simple  {	
	private static @interface ConfigSpecs {
		int poolSize() default 16;
		String threadGroupName() default "myexecs";
	}
	private static final ConfigSpecs CONFIG = ConfigurationProxyBuilder.newBuilder(new SystemPropertiesConfigurationSource())
												.build(ConfigSpecs.class);
	
	
    public static void main( String[] args ) throws InstantiationException, IllegalAccessException{
    	System.setProperty("threadGroupName", "configuredValue");
    	assertEquals(16, CONFIG.poolSize());
    	assertEquals("configuredValue", CONFIG.threadGroupName());
    }

}


```

## More information
There are a few, very simple, core concepts in Justconfig:  

* Configuration Sources
* Configuration Spec
* Proxy objects
* Value Reader Service


### Configuration Sources
A Configuration source retrieves simple (Strings or primitives) values (configuration raw value) for keys
(configuration property name). This can be viewed as a simple subset of
[Apache Configuration](http://commons.apache.org/configuration/)'s
[Configuration](http://commons.apache.org/configuration/apidocs/org/apache/commons/configuration/Configuration.html) interface.  

Justconfig comes out of the box with these configuration sources:  

* System Properties  
* java.util.Properties  
* Apache Configuration (optional dependency)


New Configuration sources can be easily added by implementing the ConfigurationSource interface or extending the ConfigurationSourceBase class which helps with String backed configuration sources.

### Configuration Spec
Configuration spec is your definition of what the configuration holds. You define one by defining an interface or an annotation like the following

```java
public @interface ConfigSpecs {
	int poolSize() default 16;
	String threadGroupName();
}
public interface ConfigSpecs2 {
	Boolean poolSize();
	String threadGroupName();
}
```
Justconfig will get you an instance of the interface, working just like how you think it will.  

If you're wondering when to pick annotations over interfaces or the other way around, these are the key differences:  

* Annotations let you specify default values in an convenient way  
* Annotations' methods' return types are limited to Strings, Enums, Classes and (not boxed) primitives. This also means all primitive values must either be explicitly set (in the configuration source) or a default value must be given (In the Configuration Spec) or a NullPointerException will be thrown on access to indicate the missing value  
* Annotaions unlike interface can't extends other annotations/interfaces.

### Proxy Objects
These are the core component you will interactive with, built using a ConfigurationProxyBuilder are an implementation
of a given Configuration Spec backed by a Configuration Source

### Value Reader Services
While the return types for methods in annotations are from a constant set, interfaces can have other return values.  
This is where Value Reader Services come in to play,
an implementation of a ValueReaderService accepts certain types that it can obtain from a configuration,
and obtain those values when requested.

Look at EnumValueReaderService for a simple example:
```java
public class EnumValueReaderService implements ValueReaderService {

	/**
	 * return true iff the type is an enum.
	 */
	public boolean readable(Class<?> type) {
		return Enum.class.isAssignableFrom(type);
	}

	/**
	 * Reads an enum constant using the enum's valueOf method
	 */
	@SuppressWarnings({ "unchecked" })
	public Enum readValue(ClassLoader loader, ConfigurationSource config,
			String name, Class<?> type)
			throws IllegalArgumentException {
		String enumName = config.getString(name);
		if(enumName == null) {
			return null;
		}
		Class<? extends Enum> eClass = type.asSubclass(Enum.class);
		return Enum.valueOf(eClass, enumName);
	}
}
```
Justconfig by default looks for ValueReaderServices using
Java's [ServiceLoader](http://docs.oracle.com/javase/6/docs/api/java/util/ServiceLoader.html),
allowing you to easily implement value readers for more types or plug in libraries that do.  
When building a proxy (with ConfigurationProxyBuilder), one can override the way Justconfig retreives
a ValueReaderService for a type by supplying a ValueReaderResolverStrategy instance to the valueReaderResolverStrategy method.
