package io.ous.justconfig.strategies.impl;

import static org.junit.Assert.assertEquals;

import io.ous.justconfig.strategies.impl.BeanMethodPropetyStrategy;
import io.ous.justconfig.testutil.ConfigSpecs;

import java.lang.reflect.Method;

import org.junit.Test;

public class BeanMethodPropetyStrategyUnitTest {
	private Method getObject,
				   isThing;
	private BeanMethodPropetyStrategy instance;
	public BeanMethodPropetyStrategyUnitTest() throws SecurityException, NoSuchMethodException {
		instance = BeanMethodPropetyStrategy.BEAN_METHOD_PROPERTY_STRATEGY_INSTANCE;
		getObject = ConfigSpecs.class.getMethod("getObject");
		isThing = ConfigSpecs.class.getMethod("isThing");
	}
	@Test
	public void testGetterProperty() throws SecurityException, NoSuchMethodException {
		assertEquals("getObject not reduced to object", "object", instance.getPropertyName(getObject));
	}
	@Test
	public void testIsProperty() throws SecurityException, NoSuchMethodException {
		assertEquals("isThing not reduced to thing", "thing", instance.getPropertyName(isThing));
	}
	@Test
	public void testNotAffectedMethods() throws SecurityException, NoSuchMethodException {
		for(Method method : ConfigSpecs.class.getMethods()) {//Assert no effect on other methods
			if(getObject.equals(method) || isThing.equals(method)) {
				continue;
			}
			assertEquals("Name not equal for method "+method.getName(),method.getName(), instance.getPropertyName(method));
		}
		
	}
}
