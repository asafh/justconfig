package io.ous.justconfig.sources;

import static org.junit.Assert.*;

import io.ous.justconfig.TestHelper;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class ConfigurationSourceBaseUnitTest {
	private String value;
	private ConfigurationSourceBase config;
	private Random random;
	public ConfigurationSourceBaseUnitTest() {
		
	}
	@Before
	public void init() {
		random = TestHelper.getRandom();
		config = new ConfigurationSourceBase() {
			@Override
			public String getString(String name) {
				return value;
			}
		};
	}
	@Test
	public void testNulls() {
		value = null;
		Object[] nulls = new Object[] {
				config.getByte("x"),
				config.getShort("x"),
				config.getLong("x"),
				config.getDouble("x"),
				config.getFloat("x"),
				config.getInteger("x"),
				config.getBoolean("x"),
				config.getCharacter("x")
		};
		for(Object obj : nulls) {
			assertNull(obj);
		}
	}
	
	@Test
	public void testGetByte() {
		byte val = TestHelper.getBasicValue(random, Byte.class);
		value = String.format("%02X", val);
		assertEquals(val,(byte) config.getByte("x"));
	}
	public void testGetShort() {
		short val = TestHelper.getBasicValue(random, Short.class);
		value = String.valueOf(val);
		assertEquals(val,(short) config.getShort("x"));
	}
	@Test
	public void testGetLong() {
		long val = TestHelper.getBasicValue(random, Long.class);
		value = String.valueOf(val);
		assertEquals(val,(long) config.getLong("x"));
	}
	@Test
	public void testGetDouble() {
		double val = TestHelper.getBasicValue(random, Double.class);
		value = String.valueOf(val);
		
		assertEquals(val,(double) config.getDouble("x"),0.0001d);
	}
	@Test
	public void testGetInteger() {
		int val = TestHelper.getBasicValue(random, Integer.class);
		value = String.valueOf(val);
		assertEquals(val,(int) config.getInteger("x"));
	}
	@Test
	public void testGetFloat() {
		float val = TestHelper.getBasicValue(random, Float.class);
		value = String.valueOf(val);
		assertEquals(val,(float) config.getFloat("x"),0.0001f);
	}
	@Test
	public void testGetBoolean() {
		boolean val = TestHelper.getBasicValue(random, Boolean.class);
		value = String.valueOf(val);
		assertEquals(val,(boolean) config.getBoolean("x"));
	}
	@Test
	public void testGetCharacter() {
		char val = TestHelper.getBasicValue(random, Character.class);
		value = String.valueOf(val);
		assertEquals(val,(char) config.getCharacter("x"));
	}
}
