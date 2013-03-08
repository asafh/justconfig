package io.ous.justconfig.strategies.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import io.ous.justconfig.annotation.PropertyDefaultValue;
import io.ous.justconfig.annotation.PropertyName;
import io.ous.justconfig.annotation.PropertyType;
import io.ous.justconfig.annotation.PropertyValueReaderService;
import io.ous.justconfig.strategies.CompleteStrategy;
import io.ous.justconfig.values.BasicValueReaderService;
import io.ous.justconfig.values.ValueReaderService;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class AnnotatedAccessorStrategyUnitTest {
	private AnnotatedAccessorStrategy anotStrategy;
	private CompleteStrategy wrapped;
	
	public AnnotatedAccessorStrategyUnitTest() {
	}
	
	
	@Before
	public void createMocks() {
		wrapped = mock(CompleteStrategy.class);
		anotStrategy = new AnnotatedAccessorStrategy(wrapped);
	}
	
	@Test
	public void testPassthrough() {
		Method method = this.getClass().getMethods()[0];
		
		anotStrategy.getPropertyName(method);
		anotStrategy.getPropertyType(method);
		anotStrategy.getDefaultValue(method, String.class);
		anotStrategy.getValueReaderService(method, String.class);
		
		verify(wrapped, times(1)).getPropertyName(method);
		verify(wrapped, times(1)).getPropertyType(method);
		verify(wrapped, times(1)).getDefaultValue(method, String.class);
		verify(wrapped, times(1)).getValueReaderService(method, String.class);
		verifyNoMoreInteractions(wrapped);
	}
	
	public static interface ConfigSpec {
		@PropertyType(String.class)
		CharSequence overrideType();
		
		@PropertyName("test")
		CharSequence overrideName();
		
		@PropertyDefaultValue("xyz")
		String overrideDefault();
		
		@PropertyValueReaderService(BasicValueReaderService.class)
		CharSequence overrideReader();
	}
	
	@Test
	public void testOverrideType() throws SecurityException, NoSuchMethodException {
		Class<?> overridenType = anotStrategy.getPropertyType(specMethod("overrideType"));
		assertEquals(String.class, overridenType);
		verify(wrapped, never()).getPropertyType(any(Method.class));
		verifyNoMoreInteractions(wrapped);
	}
	
	@Test
	public void testOverrideName() throws SecurityException, NoSuchMethodException {
		String value = anotStrategy.getPropertyName(specMethod("overrideName"));
		assertEquals("test", value);
		verify(wrapped, never()).getPropertyType(any(Method.class));
		verifyNoMoreInteractions(wrapped);
	}
	@Test
	public void testOverrideDefault() throws SecurityException, NoSuchMethodException {
		String value = anotStrategy.getDefaultValue(specMethod("overrideDefault"), String.class);
		assertEquals("xyz", value);
		verify(wrapped, never()).getPropertyType(any(Method.class));
		verifyNoMoreInteractions(wrapped);
	}
	@Test
	public void testOverrideReader() throws SecurityException, NoSuchMethodException {
		Method targetMethod = specMethod("overrideReader");
		ValueReaderService forcedService = anotStrategy.getValueReaderService(targetMethod, String.class);
		assertNotNull(forcedService);
		assertEquals(BasicValueReaderService.class,forcedService.getClass());
		verify(wrapped, never()).getValueReaderService(targetMethod, String.class);
		verifyNoMoreInteractions(wrapped);
	}


	private Method specMethod(String name) throws SecurityException, NoSuchMethodException {
		return ConfigSpec.class.getMethod(name);
	}
}
