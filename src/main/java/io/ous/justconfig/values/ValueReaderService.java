package io.ous.justconfig.values;

import io.ous.justconfig.ConfigurationSource;

public interface ValueReaderService<T> {
	public boolean readable(Class<?> type);
	public T readValue(ClassLoader loader, ConfigurationSource config,
						String name, Class<? extends T> type)
							throws IllegalArgumentException;
}
