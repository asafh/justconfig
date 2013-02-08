package io.ous.justconfig.values;

import io.ous.justconfig.sources.ConfigurationSource;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class EnumValueReaderServiceUnitTest extends
		AbstractValueReaderServiceUnitTest<EnumValueReaderService> {
	private static enum MyEnum {
		One,
		Two,
		Three3Three;
	}
	public EnumValueReaderServiceUnitTest() {
		super(EnumValueReaderService.class);
	}

	@Override
	public Set<Class<?>> getReadableTypes() {
		HashSet<Class<?>> ret = new HashSet<Class<?>>();
		ret.add(MyEnum.class);
		return ret;
	}
	protected<T extends Enum<T>> void testValue(Class<T> eClass, T val) {
		ConfigurationSource config = mock(ConfigurationSource.class);
		when(config.getString(anyString())).thenReturn(val.name());
		Enum<?> value = service.readValue(Thread.currentThread().getContextClassLoader(), config, "any", eClass);
		assertEquals(val, value);
	}
	@Test
	public void testOne() {
		testValue(MyEnum.class,MyEnum.One);
	}
	@Test
	public void testThree() {
		testValue(MyEnum.class,MyEnum.Three3Three);
	}
	
	@Test
	public void testNull() {
		ConfigurationSource config = mock(ConfigurationSource.class);
		when(config.getString(anyString())).thenReturn(null);
		Enum<?> value = service.readValue(Thread.currentThread().getContextClassLoader(), config, "any", MyEnum.class);
		assertNull(value);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testBadValue() {
		ConfigurationSource config = mock(ConfigurationSource.class);
		when(config.getString(anyString())).thenReturn("idontexist");
		service.readValue(Thread.currentThread().getContextClassLoader(), config, "any", MyEnum.class);
	}
	
}
