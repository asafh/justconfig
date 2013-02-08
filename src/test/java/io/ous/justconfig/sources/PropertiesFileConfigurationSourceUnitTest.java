package io.ous.justconfig.sources;

import java.io.File;
import java.io.IOException;

import org.junit.Before;

public class PropertiesFileConfigurationSourceUnitTest extends AbstractStringBackedConfigurationSourceUnitTest {
	private File tmpProp;

	@Before
	public void init() throws IOException {
		this.tmpProp = File.createTempFile("testprops", ".properties");
	}

	@Override
	protected ConfigurationSource getSource() {
		return null;
	}

	@Override
	protected void set(String name, String value) {
		// TODO Auto-generated method stub
		
	}
}
