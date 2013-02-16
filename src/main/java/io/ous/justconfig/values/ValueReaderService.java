package io.ous.justconfig.values;

import io.ous.justconfig.sources.ConfigurationSource;

/**
 * The ValueReaderSerivce service can read values of certain types a configuration source
 * @author Asafh
 *
 */
public interface ValueReaderService {
	/**
	 * Returns true if and only if this ValueReaderService can read the given type
	 * @param type the type that needs to be extracted
	 * @return
	 */
	public boolean readable(Class<?> type);
	/**
	 * Returns an instance of <code>type</code> by reading the configuration property named <code>name</code> from <code>config</code>,
	 * Using the given class loader if needed (for instance to resolve values of type Class)
	 * @param loader The class loader used to resolve any needed classes
	 * @param config The configuration source to read the value from
	 * @param name The name of the configuration property we want to read
	 * @param type The type of the value we are expecting back
	 * @return
	 * @throws IllegalArgumentException if the value denoted by <code>name</code> at <code>config</code> is not valid
	 */
	public Object readValue(ClassLoader loader, ConfigurationSource config,
						String name, Class<?> type)
							throws IllegalArgumentException;
}
