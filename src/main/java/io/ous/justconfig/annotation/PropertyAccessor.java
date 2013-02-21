package io.ous.justconfig.annotation;

import io.ous.justconfig.values.ValueReaderService;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyAccessor {
	/**
	 * The property name
	 * @return
	 */
	String name();
	/**
	 * Property Type, according to which a ValueReaderService might be retrieved
	 * @return
	 */
	Class<?> type();
	/**
	 * Type of the ValueReaderService to use
	 * @return
	 */
	Class<? extends ValueReaderService> reader();
	/**
	 * Stringed form of the default value, will be parsed according to the property type (must be a primitive or String)
	 * @return
	 */
	String defaultValue();
}
