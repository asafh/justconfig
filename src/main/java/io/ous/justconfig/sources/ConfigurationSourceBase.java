package io.ous.justconfig.sources;

import javax.xml.bind.DatatypeConverter;

public abstract class ConfigurationSourceBase implements ConfigurationSource {

	public abstract String getString(String name);

	/**
	 * Reads the property like a String and parses it as a Hexadecimal using DatatypeConverter.parseHexBinary.
	 */
	public Byte getByte(String name) {
		String val = getString(name);
		return val == null ? null : DatatypeConverter.parseHexBinary(val)[0];
	}

	/**
	 * Reads the property like a string and if it's not null, parses it using Short.valueOf
	 */
	public Short getShort(String name) {
		String val = getString(name);
		return val == null ? null : Short.valueOf(val);
	}
	
	/**
	 * Reads the property like a string and if it's not null, parses it using Long.valueOf
	 */
	public Long getLong(String name) {
		String val = getString(name);
		return val == null ? null : Long.valueOf(val);
	}

	/**
	 * Reads the property like a string and if it's not null, parses it using Double.valueOf
	 */
	public Double getDouble(String name) {
		String val = getString(name);
		return val == null ? null : Double.valueOf(val);
	}

	/**
	 * Reads the property like a string and if it's not null, parses it using Integer.valueOf
	 */
	public Integer getInteger(String name) {
		String val = getString(name);
		return val == null ? null : Integer.valueOf(val);
	}

	/**
	 * Reads the property like a string and if it's not null, parses it using Float.valueOf
	 */
	public Float getFloat(String name) {
		String val = getString(name);
		return val == null ? null : Float.valueOf(val);
	}

	/**
	 * Reads the property like a string and if it's not null, parses it using Boolean.valueOf
	 */
	public Boolean getBoolean(String name) {
		String val = getString(name);
		return val == null ? null : Boolean.valueOf(val);
	}

	/**
	 * Reads the property like a string and if it's not null, returns the first character
	 */
	public Character getCharacter(String name) {
		String val = getString(name);
		return val == null || val.length() == 0 ? null : val.charAt(0);
	}

}
