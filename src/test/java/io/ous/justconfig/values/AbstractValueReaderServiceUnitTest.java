package io.ous.justconfig.values;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public abstract class AbstractValueReaderServiceUnitTest<T extends ValueReaderService> {
	protected final T service;
	public AbstractValueReaderServiceUnitTest(Class<T> serviceClass){
		try {
			service = serviceClass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("Service reader class must be instantiable through newInstance()",e);
		}
	}
	public abstract Set<Class<?>> getReadableTypes();
	
	public Set<Class<?>> getUnreadableTypes() {
		Collection<Class<?>> unreadable = Arrays.asList(new Class<?>[] { Object.class,Document.class,ValueReaderService.class});
		return new HashSet<Class<?>>(unreadable);
	}
	
	@Test
	public void testReadableTypes() {
		Set<Class<?>> types = getReadableTypes();
		for(Class<?> type : types) {
			Assert.assertTrue(service.readable(type));
		}
		
		Set<Class<?>> unreadable = getUnreadableTypes();
		Assert.assertFalse(unreadable.isEmpty());
		unreadable.removeAll(types);
		for(Class<?> badType : unreadable) {
			Assert.assertFalse(badType+" should not be readable by "+service.getClass(),service.readable(badType));
		}
	}
}
