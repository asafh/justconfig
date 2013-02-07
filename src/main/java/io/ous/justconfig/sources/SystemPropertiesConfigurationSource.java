package io.ous.justconfig.sources;

/**
 * A configuration source from system properties.<br/>
 * Property names may be prefixed with a constant before retrieved from system properties
 * @author Asafh
 *
 */
public class SystemPropertiesConfigurationSource extends ConfigurationSourceBase {
	private String prefix;
	/**
	 * Constructs a SystemPropertiesConfigurationSource without a prefix
	 * 
	 */
	public SystemPropertiesConfigurationSource() {
		this("");
	}
	/**
	 * Constructs a SystemPropertiesConfigurationSource with the given prefix
	 * @param prefix the prefix that will be appended to the name for all calls to System.getProperty(name)
	 */
	public SystemPropertiesConfigurationSource(String prefix) {
		this.prefix = prefix == null ? "" : prefix;
	}
	@Override
	public String getString(String name) {
		return System.getProperty(prefix+name);
	}
}
