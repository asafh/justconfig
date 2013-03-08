package io.ous.justconfig.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyType {
	/**
	 * Property Type, according to which a ValueReaderService might be retrieved
	 * @return
	 */
	Class<?> value();
	
}
