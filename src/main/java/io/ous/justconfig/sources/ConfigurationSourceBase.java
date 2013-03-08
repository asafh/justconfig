package io.ous.justconfig.sources;

import io.ous.justconfig.util.TypeUtils;

public abstract class ConfigurationSourceBase implements ConfigurationSource {

	public abstract String getString(String name);

	/**
	 * {@link TypeUtils#getByte(String)}
	 */
	public Byte getByte(String name) {
		String val = getString(name);
		return TypeUtils.getByte(val);
	}

	/**
	 * {@link TypeUtils#getShort(String)}
	 */
	public Short getShort(String name) {
		String val = getString(name);
		return TypeUtils.getShort(val);
	}
	
	/**
	 * {@link TypeUtils#getLong(String)}
	 */
	public Long getLong(String name) {
		String val = getString(name);
		return TypeUtils.getLong(val);
	}

	/**
	 * {@link TypeUtils#getDouble(String)}
	 */
	public Double getDouble(String name) {
		String val = getString(name);
		return TypeUtils.getDouble(val);
	}

	/**
	 * {@link TypeUtils#getInteger(String)}
	 */
	public Integer getInteger(String name) {
		String val = getString(name);
		return TypeUtils.getInteger(val);
	}

	/**
	 * {@link TypeUtils#getFloat(String)}
	 */
	public Float getFloat(String name) {
		String val = getString(name);
		return TypeUtils.getFloat(val);
	}

	/**
	 * {@link TypeUtils#getBoolean(String)}
	 */
	public Boolean getBoolean(String name) {
		String val = getString(name);
		return TypeUtils.getBoolean(val);
	}

	/**
	 * {@link TypeUtils#getCharacter(String)}
	 */
	public Character getCharacter(String name) {
		String val = getString(name);
		return TypeUtils.getCharacter(val);
	}

}
