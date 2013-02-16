package io.ous;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import io.ous.justconfig.ConfigurationProxyHandlerBuilder;
import io.ous.justconfig.proxy.ConfigurationProxyHandler;
import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.values.ValueReaderService;

public class ConfigurationProxyHandlerBuilderUnitTest {
	public interface Specs {
		public Object getObject();
	}
	/*public int gettysburgVila();
		public String istanbul();
		public boolean isStuff();
		public Map<?,?> custom();*/

	private ConfigurationProxyHandlerBuilder builder;
	private ConfigurationSource config;
	
	@Before
	public void init() {
		config = mock(ConfigurationSource.class);
		builder = ConfigurationProxyHandlerBuilder.newBuilder()
				.configuration(config);
	}
	
	@Test
	public void testReaderServices() {
		final Object uniqueObject = new Object();
		
		Collection<ValueReaderService> services = new ArrayList<ValueReaderService>();
		ValueReaderService service = mock(ValueReaderService.class);
		when(service.readable(any(Class.class))).thenReturn(true);
		
		when(service.readValue(any(ClassLoader.class), any(ConfigurationSource.class), anyString(), any(Class.class))).thenReturn(uniqueObject);
		services.add(service);
		
		Specs ret = builder.readers(services)
				.buildProxy(Specs.class);
		assertEquals(uniqueObject, ret.getObject());
	}
	
	@Test
	public void testConfig() {
		ConfigurationProxyHandler<Specs> ret = builder.build(Specs.class);
		assertEquals(config, ret.getConfiguration());
	}
	
	@Test
	public void testDefaultClassLoader() {
		ConfigurationProxyHandler<Specs> ret = builder.build(Specs.class);
		assertEquals(Thread.currentThread().getContextClassLoader(), ret.getClassLoader());
	}
	@Test
	public void testBeanNames() throws SecurityException, NoSuchMethodException {
		ConfigurationProxyHandler<Specs> ret = builder.beanNames()
													.build(Specs.class);
		assertEquals("object", ret.getPropertyName(Specs.class.getMethod("getObject")));
	}
	@Test
	public void testNoBeanNames() throws SecurityException, NoSuchMethodException {
		ConfigurationProxyHandler<Specs> ret = builder.build(Specs.class);
		assertEquals("getObject", ret.getPropertyName(Specs.class.getMethod("getObject")));
	}
}
