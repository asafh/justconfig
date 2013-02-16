package io.ous.justconfig.strategies;

import java.lang.reflect.Method;

public interface PropertyNameStrategy {
	/**
	 * Returns the configuration property name that will correspond to the given method <br/>
	 * 
	 * @param method
	 */
	public String getPropertyName(Method method);
}
