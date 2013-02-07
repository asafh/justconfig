package io.ous.justconfig.values;

import io.ous.justconfig.sources.ConfigurationSource;

/**
 * A value reader for class instances
 * @author Asafh
 *
 */
public class ClassValueReaderSerivce implements ValueReaderService {
	public boolean readable(Class<?> type) {
		return Class.class.isAssignableFrom(type);
	}

	public Class<?> readValue(ClassLoader loader, ConfigurationSource config,
								String name, Class<?> type) {
		String clzName = config.getString(name);
		try {
			return clzName == null ? null : Class.forName(clzName, true, loader);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Configuration property "+name+" specified a class that could not be found "+clzName,e);
		}
	}

}
