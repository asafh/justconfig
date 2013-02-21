package io.ous.justconfig.strategies.impl;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import io.ous.justconfig.strategies.impl.AnnotationDefaultValueStrategy;

import org.junit.Before;
import org.junit.Test;

public class AnnotationDefaultValueStrategyUnitTest {
	AnnotationDefaultValueStrategy strategy;
	@Before
	public void init() {
		strategy = new AnnotationDefaultValueStrategy();
	}
	private void testDefault(String methodName, Object expectedValue) throws SecurityException, NoSuchMethodException {
		Method def = ConfigSpecs.class.getMethod(methodName);
		Object value = strategy.getDefaultValue(def, def.getReturnType());
		assertEquals(expectedValue, value);
	}

	@Test
	public void testDefaultPrimitive() throws SecurityException, NoSuchMethodException {
		testDefault("primDef", ConfigSpecs.DEF_INT_VAL);
	}
	@Test
	public void testDefaultString() throws SecurityException, NoSuchMethodException {
		testDefault("defString", ConfigSpecs.DEF_STRING_VAL);
	}
	
	
	@Test
	public void testMissingDefaultString() throws SecurityException, NoSuchMethodException {
		testDefault("sysPropStr", null);
	}
	@Test
	public void testMissingDefaultInt() throws SecurityException, NoSuchMethodException {
		testDefault("sysPropInt", null);
	}
}
