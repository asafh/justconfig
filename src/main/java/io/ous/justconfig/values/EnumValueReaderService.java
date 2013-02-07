package io.ous.justconfig.values;

import io.ous.justconfig.sources.ConfigurationSource;

/**
 * This value reader retrieves enum constants
 * @author Asafh
 *
 */
@SuppressWarnings("rawtypes")
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
