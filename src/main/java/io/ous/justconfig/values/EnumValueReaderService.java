package io.ous.justconfig.values;

import io.ous.justconfig.ConfigurationSource;

@SuppressWarnings("rawtypes")
public class EnumValueReaderService implements ValueReaderService<Enum> {

	public boolean readable(Class<?> type) {
		return Enum.class.isAssignableFrom(type);
	}

	@SuppressWarnings({ "unchecked" })
	public Enum readValue(ClassLoader loader, ConfigurationSource config,
			String name, Class<? extends Enum> type)
			throws IllegalArgumentException {
		String enumName = config.getString(name);
		if(enumName == null) {
			return null;
		}
		return Enum.valueOf(type, enumName);
	}

}
