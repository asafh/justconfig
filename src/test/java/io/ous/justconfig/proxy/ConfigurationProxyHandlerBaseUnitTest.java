package io.ous.justconfig.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.values.ValueReaderService;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ConfigurationProxyHandlerBaseUnitTest {
	private static final String DEF_STRING_VAL = "def";
	private static final int DEF_INT_VAL = 332;
	
	private static @interface ConfigSpecs {
		int primError();
		int primDef() default DEF_INT_VAL;
		String nullString();
		String defString() default DEF_STRING_VAL;
		String sysPropStr();
		int sysPropInt();
	}
	
	private ArrayList<ValueReaderService> readers;
	private ConfigurationSource config;
	private ConfigurationProxyHandlerBase<ConfigSpecs> object;

	@Before
	public void init() {
		readers = new ArrayList<ValueReaderService>();
		config = mock(ConfigurationSource.class);
		object = new ConfigurationProxyHandlerBase<ConfigSpecs>(readers,config, ConfigSpecs.class);
	}
	
	@Test
	public void testDefaultValue() throws SecurityException, NoSuchMethodException {
		Object value = object.defaultValue(getMethod("primDef"));
		assertEquals(DEF_INT_VAL, value);
		
		value = object.defaultValue(getMethod("defString"));
		assertEquals(DEF_STRING_VAL, value);
		
		value = object.defaultValue(getMethod("sysPropStr"));
		assertNull(value);
		
		value = object.defaultValue(getMethod("sysPropInt"));
		assertNull(value);
	}
	
	@Test
	public void testGetClassLoader() {
		assertEquals(Thread.currentThread().getContextClassLoader(), object.getClassLoader());
	}
	@Test
	public void testGetConfiguration() {
		assertEquals(config, object.getConfiguration());
	}
	@Test
	public void testGetSpecs() {
		assertEquals(ConfigSpecs.class, object.getSpecs());
	}
	
	
	@Test
	public void testGetPropertyName() {
		for(Method method : ConfigSpecs.class.getMethods()) {
			assertEquals("Name not equal for method "+method.getName(),method.getName(), object.getPropertyName(method));
		}
	}
	@Test
	public void testGetPropertyType() {
		for(Method method : ConfigSpecs.class.getMethods()) {
			assertEquals("Type not equal for method "+method.getName(),method.getReturnType(), object.getPropertyType(method));
		}
	}
	
	@Test
	public void testInvoke() { //I'm going to sleep... what's this this...
		ValueReaderService reader = mock(ValueReaderService.class);
		when(reader.readable(any(Class.class))).thenReturn(true);
		readers.add(reader );
		ConfigSpecs configSpecs = object.createProxy();
		configSpecs.sysPropStr();
		verify(reader).readable(String.class);
	}
	

	private Method getMethod(String name) throws SecurityException, NoSuchMethodException {
		return ConfigSpecs.class.getMethod(name);
	}
}