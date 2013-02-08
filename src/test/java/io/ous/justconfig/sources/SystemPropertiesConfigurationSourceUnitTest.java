package io.ous.justconfig.sources;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Before;

public class SystemPropertiesConfigurationSourceUnitTest extends AbstractStringBackedConfigurationSourceUnitTest {
	
	private String prefix;

	@Before
	public void init() {
		this.prefix = new BigInteger(60, new Random()).toString(32);
	}

	@Override
	protected ConfigurationSource getSource() {
		return new SystemPropertiesConfigurationSource(prefix);
	}

	@Override
	protected void set(String name, String value) {
		System.setProperty(prefix+name,value);
	}
}
