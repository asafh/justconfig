package io.ous.justconfig.strategies.impl;

import io.ous.justconfig.strategies.DefaultValueStrategy;

import java.lang.reflect.Method;

public class AnnotationDefaultValueStrategy implements DefaultValueStrategy {
	/**
	 * If the given method's defining interface is an annotation, the value specified using the annotation's <code>default</code>
	 * keyword will be returned if exists. <br/>
	 * Otherwise, (methods in non-annotation interfaces and for annotation methods with no default) null will be returned <br/>
	 */
	@Override
	public Object getDefaultValue(Method method) {
		return method.getDefaultValue();
	}

}
