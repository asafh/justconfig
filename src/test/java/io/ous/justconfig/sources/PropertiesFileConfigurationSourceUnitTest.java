package io.ous.justconfig.sources;

import java.util.Map;
import java.util.Properties;

public class PropertiesFileConfigurationSourceUnitTest extends AbstractStringBackedConfigurationSourceUnitTest {
	 

	@Override
	protected ConfigurationSource create(Map<String, String> values) {
		Properties props = new Properties();
		for(Map.Entry<String, String> entry : values.entrySet()) {
			props.setProperty(entry.getKey(), entry.getValue());
		}
		
		return new PropertiesConfigurationSource(props);
	}
}
