package io.ous.justconfig.values;

import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.util.Primitives;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ClassValueReaderServiceUnitTest extends
		AbstractValueReaderServiceUnitTest<ClassValueReaderService> {
	
	public ClassValueReaderServiceUnitTest() {
		super(ClassValueReaderService.class);
	}

	@Override
	public Set<Class<?>> getReadableTypes() {
		HashSet<Class<?>> ret = new HashSet<Class<?>>();
		ret.add(Class.class);
		return ret;
	}
	
	protected void testValue(Class<?> val) {
		ConfigurationSource config = mock(ConfigurationSource.class);
		when(config.getString(anyString())).thenReturn(val.getName());
		Class<?> value = service.readValue(Thread.currentThread().getContextClassLoader(), config, "any", Class.class);
		assertEquals(val, value);
	}
	@Test
	public void testGlobal() {
		testValue(Class.class);
		testValue(List.class);
		testValue(ArrayList.class);
		testValue(ClassValueReaderService.class);
	}
	@Test
	public void testInner() {
		testValue(Primitives.PrimitiveType.class);
		testValue(Map.Entry.class);
	}
	
	@Test
	public void testNull() {
		ConfigurationSource config = mock(ConfigurationSource.class);
		when(config.getString(anyString())).thenReturn(null);
		Class<?> value = service.readValue(Thread.currentThread().getContextClassLoader(), config, "any", Class.class);
		assertNull(value);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testBadValue() {
		ConfigurationSource config = mock(ConfigurationSource.class);
		when(config.getString(anyString())).thenReturn("there's no such class");
		service.readValue(Thread.currentThread().getContextClassLoader(), config, "any", Class.class);
	}
	
}
