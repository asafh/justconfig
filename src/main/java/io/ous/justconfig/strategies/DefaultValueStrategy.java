package io.ous.justconfig.strategies;

import java.lang.reflect.Method;

public interface DefaultValueStrategy {
	/**
	 * Returns the default value for the property accessed by <code>method</code> of type <code>propertyType</code>
	 * 
	 * @param method
	 * @return
	 */
	public<T> T getDefaultValue(Method method, Class<T> propertyType);
}
