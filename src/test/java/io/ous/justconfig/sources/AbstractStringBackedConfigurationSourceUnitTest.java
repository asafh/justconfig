package io.ous.justconfig.sources;

import java.util.Enumeration;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

/**
 * Talk about a verbose name...
 * @author Asafh
 *
 */
public abstract class AbstractStringBackedConfigurationSourceUnitTest {
	private ResourceBundle bundle;

	protected abstract ConfigurationSource getSource();
	protected abstract void set(String name, String value);

	@Before
	public void createValues() {
		bundle = ResourceBundle.getBundle(AbstractStringBackedConfigurationSourceUnitTest.class.getCanonicalName());
		Enumeration<String> keys = bundle.getKeys();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			set(key,bundle.getString(key));
		}
	}
	
	@Test
	public void test() {
		ConfigurationSource config = getSource();
		Enumeration<String> keys = bundle.getKeys();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			set(key,bundle.getString(key));
		}
	}
	
	
}
