package io.ous;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import io.ous.justconfig.ConfigurationProxyHandlerBuilder;
import io.ous.justconfig.sources.SystemPropertiesConfigurationSource;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.junit.Ignore;
import org.junit.Test;

public class AdhocTest {
	
	private static interface BeanSample {
		public String getFish();
	}
	@Ignore
	@Test
	public void testBeans() throws NoSuchMethodException, SecurityException, IntrospectionException {
		Method method = BeanSample.class.getMethod("getFish");
		BeanInfo beanInfo = java.beans.Introspector.getBeanInfo(BeanSample.class);
		for(PropertyDescriptor desc : beanInfo.getPropertyDescriptors()) {
			System.out.println(desc.getName());			
			System.out.println(desc.getDisplayName());
			System.out.println(desc.getShortDescription());
		}
//		java.beans.MethodDescriptor methodDescriptor = new java.beans.MethodDescriptor(method);
		
		
	}
}
