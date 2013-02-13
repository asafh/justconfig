package io.ous.justconfig.sources;

import io.ous.TestHelper;

import java.math.BigInteger;
import java.util.Map;


public class SystemPropertiesConfigurationSourceUnitTest extends AbstractStringBackedConfigurationSourceUnitTest {
	private static final String PREFIX = new BigInteger(60, TestHelper.getRandom()).toString(32);

	@Override
	protected ConfigurationSource create(Map<String, String> values) {
		for(Map.Entry<String, String> entry : values.entrySet()) {
			System.setProperty(PREFIX+entry.getKey(), entry.getValue());
		}
		return new SystemPropertiesConfigurationSource(PREFIX);
	}
	

	
}
