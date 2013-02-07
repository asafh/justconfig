package io.ous.justconfig.sources.external;

import java.util.Objects;

import org.apache.commons.configuration.Configuration;

import io.ous.justconfig.sources.ConfigurationSource;

public class ApacheCommonsConfigurationSource implements ConfigurationSource {
	private final Configuration config;

	public ApacheCommonsConfigurationSource(Configuration config) {
		Objects.requireNonNull(config);
		this.config = config;
		
	}
	public String getString(String name) {
		return config.getString(name);
	}

	public Byte getByte(String name) {
		return config.getByte(name,null);
	}

	public Short getShort(String name) {
		return config.getShort(name,null);
	}

	public Long getLong(String name) {
		return config.getLong(name,null);
	}

	public Double getDouble(String name) {
		return config.getDouble(name,null);
	}

	public Integer getInteger(String name) {
		return config.getInteger(name,null);
	}

	public Float getFloat(String name) {
		return config.getFloat(name,null);
	}

	public Boolean getBoolean(String name) {
		return config.getBoolean(name,null);
	}

	public Character getCharacter(String name) {
		String val = getString(name);
		return val == null  || val.length() == 0 ? null : val.charAt(0);
	}
}
