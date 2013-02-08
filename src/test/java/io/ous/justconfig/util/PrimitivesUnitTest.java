package io.ous.justconfig.util;

import io.ous.justconfig.util.Primitives.PrimitiveType;

import org.junit.Test;
import static org.junit.Assert.*;

public class PrimitivesUnitTest {
	static final Object[][] TYPES = {
			{PrimitiveType.INTEGER, Integer.TYPE, Integer.class},
			{PrimitiveType.FLOAT, Float.TYPE, Float.class},
			{PrimitiveType.LONG, Long.TYPE, Long.class},
			{PrimitiveType.DOUBLE, Double.TYPE, Double.class},
			{PrimitiveType.BOOLEAN, Boolean.TYPE, Boolean.class},
			{PrimitiveType.VOID, Void.TYPE, Void.class},
			{PrimitiveType.CHARACTER, Character.TYPE, Character.class},
			{PrimitiveType.SHORT, Short.TYPE, Short.class},
			{PrimitiveType.BYTE, Byte.TYPE, Byte.class},
	};
	@Test
	public void testSizes() {
		assertEquals("getPrimitives.size",TYPES.length, Primitives.getPrimitives().size());
		assertEquals("getWrappings.size",TYPES.length, Primitives.getWrappings().size());
	}
}
