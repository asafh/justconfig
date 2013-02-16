package io.ous.justconfig.strategies;

import java.lang.reflect.Method;

public class MethodPropetyStrategy implements PropertyNameStrategy, PropertyTypeStrategy {
	public static MethodPropetyStrategy METHOD_PROPERTY_STRATEGY_INSTANCE = new MethodPropetyStrategy();
	/**
	 * Returns the method's return type <br/>
	 * Should generally not be overridden
	 */
	@Override
	public Class<?> getPropertyType(Method method) {
		return method.getReturnType();
	}
	/**
	 * Returns the method's name.
	 * @param method
	 */
	@Override
	public String getPropertyName(Method method) {
		return method.getName();
	}

}
