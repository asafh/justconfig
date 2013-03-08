package io.ous.justconfig.strategies.impl;

import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import io.ous.justconfig.strategies.DefaultValueStrategy;
import io.ous.justconfig.strategies.PropertyNameStrategy;
import io.ous.justconfig.strategies.PropertyTypeStrategy;
import io.ous.justconfig.strategies.ValueReaderResolverStrategy;

public class ComposedCompleteStrategyUnitTest {
	PropertyNameStrategy propertyNameStrategy;
	PropertyTypeStrategy propertyTypeStrategy;
	DefaultValueStrategy defaultValueStrategy;
	ValueReaderResolverStrategy valueReaderResolver;
	private ComposedCompleteStrategy composedCompleteStrategy;
	public ComposedCompleteStrategyUnitTest() {
	}
	
	@Before
	public void createMocks() {
		propertyNameStrategy = mock(PropertyNameStrategy.class);
		propertyTypeStrategy = mock(PropertyTypeStrategy.class);
		defaultValueStrategy = mock(DefaultValueStrategy.class);
		valueReaderResolver  = mock(ValueReaderResolverStrategy.class);
		composedCompleteStrategy = new ComposedCompleteStrategy(propertyNameStrategy, propertyTypeStrategy, defaultValueStrategy, valueReaderResolver);
	}
	
	@Test
	public void testPassthrough() {
		Method method = this.getClass().getMethods()[0];
		
		composedCompleteStrategy.getPropertyName(method);
		composedCompleteStrategy.getPropertyType(method);
		composedCompleteStrategy.getDefaultValue(method, String.class);
		composedCompleteStrategy.getValueReaderService(method, String.class);
		
		verify(propertyNameStrategy, times(1)).getPropertyName(method);
		verify(propertyTypeStrategy, times(1)).getPropertyType(method);
		verify(defaultValueStrategy, times(1)).getDefaultValue(method, String.class);
		verify(valueReaderResolver, times(1)).getValueReaderService(method, String.class);
		verifyNoMoreInteractions(propertyNameStrategy,propertyTypeStrategy,defaultValueStrategy,valueReaderResolver);
	}
}
