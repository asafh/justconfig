package io.ous.justconfig;

import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.values.ValueReaderService;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DefaultJustConfigToolkit extends JustConfigToolkit {
	//TODO: remove this... do I actually need the toolkit here?
	public static DefaultJustConfigToolkit INSTANCE = new DefaultJustConfigToolkit(Thread.currentThread().getContextClassLoader());
	
	private ServiceLoader<ValueReaderService> serviceLoader; 
	protected DefaultJustConfigToolkit(ClassLoader loader) {
		this.serviceLoader = ServiceLoader.load(ValueReaderService.class, loader);
	}
	protected ServiceLoader<ValueReaderService> getServiceLoader() {
		return serviceLoader;
	}

	public Iterator<ValueReaderService> iterator() {
		return serviceLoader.iterator();
	}
	
	@Override
	public Object readValue(ClassLoader loader, ConfigurationSource config, String name, Class<?> type) {
		for(ValueReaderService reader : this) {
			if(reader.readable(type)) {
				return reader.readValue(loader, config, name, type);
			}
		}
		return null;
	}
}