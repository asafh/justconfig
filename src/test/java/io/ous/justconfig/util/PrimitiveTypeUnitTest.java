package io.ous.justconfig.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import io.ous.justconfig.util.Primitives.PrimitiveType;

@RunWith(Parameterized.class)
public class PrimitiveTypeUnitTest {
	private PrimitiveType pType;
	private Class<?> _primitive;
	private Class<?> wrapping;
	@Parameters(name="{index}: {2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(PrimitivesUnitTest.TYPES);
	}
	
	public PrimitiveTypeUnitTest(PrimitiveType type, Class<?> primitive, Class<?> wrapping) {
		pType = type;
		this._primitive = primitive;
		this.wrapping = wrapping;
	}
	
	@Test
	public void testPrimitiveType() {
		assertNotNull(pType);
	}
	
	@Test
	public void testPrimitive() {
		Class<?> primitive = pType.getPrimitive();
		assertNotNull(primitive);
		
		assertTrue(primitive.isPrimitive());
		assertEquals(this._primitive, primitive);
				
		assertEquals(pType.getPrimitiveName(), primitive.getSimpleName());
		assertEquals(pType.name(), wrapping.getSimpleName().toUpperCase());
		
		assertEquals(pType, Primitives.getTypeFromPrimitive(primitive));
		assertEquals(pType, Primitives.getType(primitive));
		
		assertTrue(Primitives.getPrimitives().contains(primitive));
		assertFalse(Primitives.getWrappings().contains(primitive));
	}
	
	@Test
	public void testWrapping() {
		Class<?> wrapping = pType.getWrapping();
		assertNotNull(wrapping);
		
		assertFalse(wrapping.isPrimitive());
		assertEquals(this.wrapping, wrapping);
		
		assertEquals(pType.getWrappingName(), wrapping.getSimpleName());
		
		assertEquals(pType, Primitives.getTypeFromWrapping(wrapping));
		assertEquals(pType, Primitives.getType(wrapping));
		
		assertTrue(Primitives.getWrappings().contains(wrapping));
		assertFalse(Primitives.getPrimitives().contains(wrapping));
	}
}
