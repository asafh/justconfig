package io.ous.justconfig.sources;

/**
 * This classes allows delegation of ConfigurationSources
 * @author Asafh
 *
 */
public class ConfigurationSourceChain implements ConfigurationSource {
	private final ConfigurationSource[] sources;

	/**
	 * Creates a new ConfigurationSourceChain with the given sources as it's chain (in that order)
	 * @param sources the sources that will be attempted to read
	 */
	public ConfigurationSourceChain(ConfigurationSource... sources) {
		this.sources = sources;
	}
	
	public String getString(String name) {
		for(ConfigurationSource source : sources) {
			String ret = source.getString(name);
			if(ret != null) {
				return ret;
			}
		}
		return null;
	}

	public Byte getByte(String name) {
		for(ConfigurationSource source : sources) {
			Byte ret = source.getByte(name);
			if(ret != null) {
				return ret;
			}
		}
		return null;
	}

	public Short getShort(String name) {
		for(ConfigurationSource source : sources) {
			Short ret = source.getShort(name);
			if(ret != null) {
				return ret;
			}
		}
		return null;
	}

	public Long getLong(String name) {
		for(ConfigurationSource source : sources) {
			Long ret = source.getLong(name);
			if(ret != null) {
				return ret;
			}
		}
		return null;
	}

	public Double getDouble(String name) {
		for(ConfigurationSource source : sources) {
			Double ret = source.getDouble(name);
			if(ret != null) {
				return ret;
			}
		}
		return null;
	}

	public Integer getInteger(String name) {
		for(ConfigurationSource source : sources) {
			Integer ret = source.getInteger(name);
			if(ret != null) {
				return ret;
			}
		}
		return null;
	}

	public Float getFloat(String name) {
		for(ConfigurationSource source : sources) {
			Float ret = source.getFloat(name);
			if(ret != null) {
				return ret;
			}
		}
		return null;
	}

	public Boolean getBoolean(String name) {
		for(ConfigurationSource source : sources) {
			Boolean ret = source.getBoolean(name);
			if(ret != null) {
				return ret;
			}
		}
		return null;
	}

	public Character getCharacter(String name) {
		for(ConfigurationSource source : sources) {
			Character ret = source.getCharacter(name);
			if(ret != null) {
				return ret;
			}
		}
		return null;
	}

}
