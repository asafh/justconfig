package io.ous.justconfig.values;

import io.ous.justconfig.sources.ConfigurationSource;

public interface ValueReaderService{
	public boolean readable(Class<?> type);
	public Object readValue(ClassLoader loader, ConfigurationSource config,
						String name, Class<?> type)
							throws IllegalArgumentException;
}
