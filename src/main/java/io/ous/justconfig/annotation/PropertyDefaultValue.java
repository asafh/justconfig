package io.ous.justconfig.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyDefaultValue {
	/**
	 * Stringed form of the default value, will be parsed according to the property type (must be a primitive or String)
	 * @return
	 */
	String defaultValue();
}
