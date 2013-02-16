package io.ous.justconfig;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import io.ous.justconfig.ConfigurationProxyBuilder;
import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.values.ValueReaderService;

public class ConfigurationProxyBuilderUnitTest {
	public interface Specs {
		public Object getObject();
	}
	/*public int gettysburgVila();
		public String istanbul();
		public boolean isStuff();
		public Map<?,?> custom();*/

	private ConfigurationProxyBuilder builder;
	private ConfigurationSource config;
	
	@Before
	public void init() {
		config = mock(ConfigurationSource.class);
		builder = ConfigurationProxyBuilder.newBuilder(config);
	}
	
	@Test
	public void testReaderServices() {
		final Object uniqueObject = new Object();
		
		Collection<ValueReaderService> services = new ArrayList<ValueReaderService>();
		ValueReaderService service = mock(ValueReaderService.class);
		when(service.readable(any(Class.class))).thenReturn(true);
		
		when(service.readValue(any(ClassLoader.class), any(ConfigurationSource.class), anyString(), any(Class.class))).thenReturn(uniqueObject);
		services.add(service);
		
		Specs ret = builder.valueReaderResolvers(services).build(Specs.class);
		assertEquals(uniqueObject, ret.getObject());
	}
}
