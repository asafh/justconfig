package io.ous.justconfig.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import io.ous.justconfig.ConfigurationProxyHandlerBuilder;
import io.ous.justconfig.sources.SystemPropertiesConfigurationSource;

import org.junit.Before;
import org.junit.Test;

public class AnnotationSpecTest {	
	private static final String DEF_STRING_VAL = "def";
	private static final int DEF_INT_VAL = 332;
	
	private static final String VAL_SYS_PROP_STR = "syspropval";
	private static final int VAL_SYS_PROP_INT = 667;
	private static @interface ConfigSpecs {
		int primError();
		int primDef() default DEF_INT_VAL;
		String nullString();
		String defString() default DEF_STRING_VAL;
		String sysPropStr();
		int sysPropInt();
	}
	private ConfigSpecs config;
	@Before
	public void createConfiguration() {
		System.setProperty("sysPropStr", VAL_SYS_PROP_STR);
		System.setProperty("sysPropInt", String.valueOf(VAL_SYS_PROP_INT));
		
		config = ConfigurationProxyHandlerBuilder.newBuilder().configuration(new SystemPropertiesConfigurationSource()).build(ConfigSpecs.class).createProxy();
	}
	
	@Test
	public void test() {
		assertNull(config.nullString());
		
		assertEquals(DEF_INT_VAL,		config.primDef());
		assertEquals(DEF_STRING_VAL, 	config.defString());
		assertEquals(VAL_SYS_PROP_STR,	config.sysPropStr());
		assertEquals(VAL_SYS_PROP_INT,	config.sysPropInt());
	}
	
	@Test(expected=NullPointerException.class)
	public void testNoPrimitive() {
		config.primError();
	}
}
