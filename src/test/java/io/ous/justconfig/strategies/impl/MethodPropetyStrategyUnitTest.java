package io.ous.justconfig.strategies.impl;

import static org.junit.Assert.assertEquals;

import io.ous.justconfig.strategies.impl.MethodPropetyStrategy;

import java.lang.reflect.Method;

import org.junit.Test;

public class MethodPropetyStrategyUnitTest {
	@Test
	public void testGetPropertyName() {
		MethodPropetyStrategy instance = MethodPropetyStrategy.METHOD_PROPERTY_STRATEGY_INSTANCE;
		for(Method method : ConfigSpecs.class.getMethods()) {
			assertEquals("Name not equal for method "+method.getName(),method.getName(), instance.getPropertyName(method));
		}
	}
	@Test
	public void testGetPropertyType() {
		MethodPropetyStrategy instance = MethodPropetyStrategy.METHOD_PROPERTY_STRATEGY_INSTANCE;
		for(Method method : ConfigSpecs.class.getMethods()) {
			assertEquals("Return type not equal for method "+method.getName(),method.getReturnType(), instance.getPropertyType(method));
		}
	}
}
