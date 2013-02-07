package io.ous.justconfig.sources;

import java.util.Properties;

public class PropertiesConfigurationSource extends ConfigurationSourceBase {
	private Properties props;

	public PropertiesConfigurationSource(Properties props) {
		this.props = props;
	}

	@Override
	public String getString(String name) {
		return props.getProperty(name);
	}
}
