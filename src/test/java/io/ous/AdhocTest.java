package io.ous;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class AdhocTest {
	private static @interface Anot {}
	public Class bla;
	public Integer blat;
	@Test
	public void test() throws NoSuchFieldException, SecurityException {
		assertTrue(Anot.class.isInterface());
		assertEquals(Character.TYPE.getName(), "char");
		assertEquals(Character.TYPE.getSimpleName(), "char");
		assertEquals(Character.class.getSimpleName(), "Character");
		Class<?> type = getClass().getField("bla").getType();
		System.out.println(type);
		System.out.println(Class.class.isAssignableFrom(type));
		Class<?> prim = getClass().getField("blat").getType();
		System.out.println(prim);
		System.out.println(Class.class.isAssignableFrom(prim));
	}
}
