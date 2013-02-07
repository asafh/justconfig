package io.ous;

import io.ous.justconfig.DefaultJustConfigToolkit;
import io.ous.justconfig.proxy.ConfigurationAnnotationProxy;
import io.ous.justconfig.sources.SystemPropertiesConfigurationSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;

import static org.junit.Assert.*;

public class AdhocTest {
	private static @interface Anot {
		int primError();
		int primDef() default 5;
		String nullString();
		String defString() default "def";
		String abcdef();
		int testInteger();
	}
	@Test
	public void test() {
		String value = "MY VALUE";
		System.setProperty("abcdef", value);
		System.setProperty("testInteger", "5");
		
		Anot conf = DefaultJustConfigToolkit.INSTANCE.proxy(new ConfigurationAnnotationProxy<Anot>(new SystemPropertiesConfigurationSource(), Anot.class, Thread.currentThread().getContextClassLoader()));
		assertEquals(conf.primDef(), 5);
		assertNull(conf.nullString());
		assertEquals(conf.defString(), "def");
		assertEquals(conf.abcdef(), value);
		assertEquals(conf.testInteger(), 5);
	}
	
	@Test(expected=NullPointerException.class)
	public void testNoPrimitive() {
		Anot conf = DefaultJustConfigToolkit.INSTANCE.proxy(new ConfigurationAnnotationProxy<Anot>(new SystemPropertiesConfigurationSource(), Anot.class, Thread.currentThread().getContextClassLoader()));
		conf.primError();
	}
}
