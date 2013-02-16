package io.ous.justconfig.strategies;

import java.lang.reflect.Method;

public interface PropertyTypeStrategy {
	/**
	 * Returns the expected configuration property type for the given method <br/>
	 * Should general stay the methods return type. <br/>
	 * Any return value which is not a subclass of the method's return type will cause an error on access
	 * @param method
	 * @return the expected
	 */
	public Class<?> getPropertyType(Method method);
}
