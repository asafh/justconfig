package io.ous.justconfig.sources;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import io.ous.TestHelper;
import io.ous.justconfig.util.Primitives;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@RunWith(Parameterized.class)
public class ConfigurationSourceChainUnitTest {
	@Parameters(name="{index}: Size={0}, Type={1}")
	public static Collection<Object[]> data() {
		int[] sizes = {0,1,2,3,16,13,75};
		Set<Class<?>> primitives = Primitives.getPrimitives();
		primitives.remove(Void.TYPE);
		List<Object[]> ret = new ArrayList<Object[]>();
		for(int size : sizes) {
			for(Class<?> prim : primitives) {
				ret.add(new Object[] {size, prim});				
			}
		}
		
		return ret;
	}
//	private Object value;
	private ConfigurationSourceChain config;
	private Random random;
	private final ConfigurationSourceMock[] mocks;
	private final Class<?> type;
	public ConfigurationSourceChainUnitTest(int size, Class<?> type) {
		mocks = new ConfigurationSourceMock[size];
		this.type = type;
	}
	static class ConfigurationSourceMock {
		final ConfigurationSource mock;
		Answer<Object> answer;
		
		ConfigurationSourceMock() {
			mock = mock(ConfigurationSource.class, new Answer<Object>() {
				@Override
				public Object answer(InvocationOnMock invocation)
						throws Throwable {
					return answer.answer(invocation);
				}
			});
			always(null);
		}
		
		public ConfigurationSourceMock always(final Object object) {
			return answer(new Answer<Object>() {
				@Override
				public Object answer(InvocationOnMock invocation)
						throws Throwable {
					return object;
				}
			});
		}

		public ConfigurationSourceMock answer(Answer<Object> answer) {
			this.answer = answer;
			return this;
		}
	}
	@Before
	public void init() {
		random = new Random(1);
		
		ConfigurationSource[] sources = new ConfigurationSource[mocks.length];
		for(int i = 0 ; i < mocks.length; ++i) {
			mocks[i] = new ConfigurationSourceMock();
			sources[i] = mocks[i].mock;
		}
		
		config = new ConfigurationSourceChain(sources);
	}
	
	
	@Test
	public void testGetObject() {
		if(mocks.length == 0) {
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
			return;
		}
		Object val = TestHelper.getBasicValue(random, type);
		ConfigurationSourceMock randMock = getRandomSource();
		randMock.always(val);
		
		assertEquals(val, TestHelper.getBasicValue(config, type));
		
		int times = 1;
		for(ConfigurationSourceMock mock : mocks) {
			TestHelper.getBasicValue(verify(mock.mock,times(times)), type);
			if(mock == randMock) { //hard equality ok, from here onwards no invocations should have occurred
				times = 0;
			}
			for(Class<?> primitive : Primitives.getPrimitives()) {
				if(Void.TYPE.equals(primitive) || type.equals(primitive)) { //For any other type except this one and Void 
					continue;
				}
				TestHelper.getBasicValue(verify(mock.mock, never()), primitive); //should never be retreived
			}
		}
		
		
	}
	private ConfigurationSourceMock getRandomSource() {
		int target = random.nextInt(mocks.length);
		return mocks[target];
	}
}
