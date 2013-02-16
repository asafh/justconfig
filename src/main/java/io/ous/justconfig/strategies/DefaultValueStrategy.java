package io.ous.justconfig.strategies;

import java.lang.reflect.Method;

public interface DefaultValueStrategy {
	/**
	 * Returns the default value for the property accessed by <code>method</code>
	 * 
	 * @param method
	 * @return
	 */
	public Object getDefaultValue(Method method);
}
