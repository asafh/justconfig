package io.ous.justconfig.annotation;

import io.ous.justconfig.values.ValueReaderService;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyValueReaderService {
	/**
	 * Type of the ValueReaderService to use
	 * @return
	 */
	Class<? extends ValueReaderService> reader();
}
