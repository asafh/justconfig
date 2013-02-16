# The Justconfig library

The Justconfig library provides easy access to configuration properties. By simply specifying an interface describing your configuration values you can access those values without a hassle, independent of their source.  
Justconfig has no dependencies.
Check out the examples!


## Simple Example
This example showcases a simple usage, leveraging annotation interfaces' ability to specify default values with method declaration


```
#!java
public class Simple  {	
	private static @interface ConfigSpecs {
		int poolSize() default 16;
		String threadGroupName() default "myexecs";
	}
	private static final ConfigSpecs CONFIG = ConfigurationProxyHandlerBuilder.newBuilder().
												configuration(new SystemPropertiesConfigurationSource())
												.build(ConfigSpecs.class)
												.createProxy();
	
	
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
* Value Reader Service
* Proxy Handler

### Configuration Sources
A Configuration source retrieves simple values (Strings or primitives) for a String configuration property name. A simpler subset of [Apache Configuration](http://commons.apache.org/configuration/) API's [Configuration](http://commons.apache.org/configuration/apidocs/org/apache/commons/configuration/Configuration.html) interface.  
Justconfig comes out of the box with these configuration sources:  

* System Properties  
* java.util.Properties  
* Apache Configuration (optional dependency)


New Configuration sources can be easily added by implementing the ConfigurationSource interface or extending the ConfigurationSourceBase class which helps with String backed configuration sources.

### Configuration Spec
Configuration spec is your definition of what the configuration holds. You define one by defining an interface or an annotation like the following

```
#!java
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

If you're wondering why pick or not pick annotations over interfaces, these are the key differences:  

* Annotations let you specify default values in an convenient way  
* Annotations' methods' return types are limited to Strings, Enums, Classes and (not boxed) primitives. This also means all primitive values must either be explicitly set (in the configuration source) or a default value must be given (In the Configuration Spec) or a NullPointerException will be thrown on access to indicate the missing value  
* Annotaions unlike interface can't extends other annotations/interfaces.


### Value Reader Services
While the return types for methods in annotations are from a constant set, interfaces can have other return values.  
This is where Value Reader Services come in to play, an implementation accepts certain types that it can obtain from a configuration, and does so when such a value is requested.

Look at EnumValueReaderService for a simple example:
```
#!java
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
Justconfig by default looks for ValueReaderServices using Java's [ServiceLoader](http://docs.oracle.com/javase/6/docs/api/java/util/ServiceLoader.html), allowing you to easily implement value readers for more types or plug in libraries that do.