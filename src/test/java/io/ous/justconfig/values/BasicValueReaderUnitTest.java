package io.ous.justconfig.values;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import io.ous.TestHelper;
import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.util.Primitives;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@RunWith(Parameterized.class)
public class BasicValueReaderUnitTest extends AbstractValueReaderServiceUnitTest<BasicValueReaderService> {
	Random random;
	private Class<?> type;
	
	public BasicValueReaderUnitTest(Class<?> type) {
		super(BasicValueReaderService.class);
		this.type = type;
	}
	@Parameters(name="{index}: {0}")
	public static Collection<Object[]> data() {
		List<Object[]> ret = new ArrayList<Object[]>();
		for(Class<?> type : _getReadableTypes()) {
			ret.add(new Object[] {type});
		}
		return ret;
	}
	
	@Before
	public void init() {
		random = new Random();
	}
	@Override
	public Set<Class<?>> getReadableTypes() {
		return _getReadableTypes();
	}
	
	public static Set<Class<?>> _getReadableTypes() {
		HashSet<Class<?>> ret = new HashSet<Class<?>>();
		ret.addAll(Primitives.getPrimitives());
		ret.addAll(Primitives.getWrappings());
		ret.add(String.class);
		ret.remove(Void.class);
		ret.remove(Void.TYPE);
		return ret;
	}
	
	protected ConfigurationSource mockAlwaysReturn(final Object ret) {
		return mock(ConfigurationSource.class, new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return ret;
			}
		});
	}
	@Test
	public void testType() {
		Object value = TestHelper.getBasicValue(random, type);
		ConfigurationSource config = mockAlwaysReturn(value);
		
		Object readValue = service.readValue(Thread.currentThread().getContextClassLoader(), config, "any", type);
		assertEquals(value, readValue);
	}
	
	
	@Test
	public void testNull() {
		ConfigurationSource config = mockAlwaysReturn(null);
		Object ret = service.readValue(Thread.currentThread().getContextClassLoader(), config, "any", type);
		Assert.assertNull(ret);
	}
}
