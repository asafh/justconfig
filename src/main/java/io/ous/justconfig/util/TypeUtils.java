package io.ous.justconfig.util;


import io.ous.justconfig.util.Primitives.PrimitiveType;

import java.math.BigInteger;

public class TypeUtils {
	private TypeUtils() {}
	public static<T> T getValue(String val, Class<T> type) {
		if(String.class.equals(type)) {
			return type.cast(val);
		}
		Object ret;
		PrimitiveType pType = Primitives.getType(type);
		switch(pType) {
		case INTEGER:
			ret = getInteger(val);
			break;
		case LONG:
			ret = getLong(val);
			break;
		case DOUBLE:
			ret = getDouble(val);
			break;
		case FLOAT:
			ret = getFloat(val);
			break;
		case BOOLEAN:
			ret = getBoolean(val);
			break;
		case CHARACTER:
			ret = getCharacter(val);
			break;
		case BYTE:
			ret = getByte(val);
			break;
		case SHORT:
			ret = getShort(val);
			break;
		default: //Will never be default... just for the compiler
		case VOID:
			throw new IllegalArgumentException("Cannot retreive void value: "+val);
		}
		return type.cast(ret);
	}
	/**
	 * Reads the property like a String and parses it as a Hexadecimal using DatatypeConverter.parseHexBinary.
	 */
	public static Byte getByte(String val) {
		return val == null ? null : new BigInteger(val, 16).byteValue();// Byte.parseByte(val, 16);
	}

	/**
	 * Reads the property like a string and if it's not null, parses it using Short.valueOf
	 */
	public static Short getShort(String val) {
		return val == null ? null : Short.valueOf(val);
	}
	
	/**
	 * Reads the property like a string and if it's not null, parses it using Long.valueOf
	 */
	public static Long getLong(String val) {
		return val == null ? null : Long.valueOf(val);
	}

	/**
	 * Reads the property like a string and if it's not null, parses it using Double.valueOf
	 */
	public static Double getDouble(String val) {
		return val == null ? null : Double.valueOf(val);
	}

	/**
	 * Reads the property like a string and if it's not null, parses it using Integer.valueOf
	 */
	public static Integer getInteger(String val) {
		return val == null ? null : Integer.valueOf(val);
	}

	/**
	 * Reads the property like a string and if it's not null, parses it using Float.valueOf
	 */
	public static Float getFloat(String val) {
		return val == null ? null : Float.valueOf(val);
	}

	/**
	 * Reads the property like a string and if it's not null, parses it using Boolean.valueOf
	 */
	public static Boolean getBoolean(String val) {
		return val == null ? null : Boolean.valueOf(val);
	}

	/**
	 * Reads the property like a string and if it's not null, returns the first character
	 */
	public static Character getCharacter(String val) {
		return val == null || val.length() == 0 ? null : val.charAt(0);
	}
}
