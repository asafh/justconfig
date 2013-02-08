package io.ous.justconfig.sources;

import io.ous.TestHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Talk about a verbose name...
 * @author Asafh
 *
 */
public abstract class AbstractStringBackedConfigurationSourceUnitTest {
	private ConfigurationSource config;
	private Map<String,String> values;
	
	public AbstractStringBackedConfigurationSourceUnitTest() {
		
	}
	protected abstract ConfigurationSource create(Map<String,String> values);
	
	public ConfigurationSource getConfigurationSource() {
		return config;
	}
	
	@Before
	public void createValues() {
		values = new HashMap<String, String>();
		Random random = new Random();
		for(int i =0; i < 100; ++i) {
			values.put(TestHelper.getBasicValue(random, String.class), TestHelper.getBasicValue(random, String.class));
		}
		values.put("xxwy.xvcd", "adg adg adg %33444");
		
		config = create(values);
	}
	
	@Test
	public void test() {
		for(Map.Entry<String, String> entry : values.entrySet()) {
			assertEquals("Inequalitiy for entry set "+entry,entry.getValue(), config.getString(entry.getKey()));
		}
	}
	
	@Test
	public void testNulls() {
		assertNull(config.getString("I_DONT_EXIST"));
	}
}
