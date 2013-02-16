package io.ous.justconfig.proxy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

@RunWith(Parameterized.class)
public class ConfigurationProxyHandlerWrapperUnitTest {
	private static interface ConfigSpecs {}
//	private SimpleMock<ConfigurationProxyHandler> mock;
	private ConfigurationProxyWrapper<ConfigSpecs> wrapper;
	private ConfigurationProxyHandler<ConfigSpecs> wrapped;
	private Method target;
	private Set<Method> otherMethods;
	
	@Parameters(name="{index}: Method={1}")
	public static Collection<Object[]> data() {
		List<Object[]> ret = new ArrayList<Object[]>();
		for(Method method : ConfigurationProxyHandler.class.getMethods()) {
			ret.add(new Object[] {method, method.getName()});				
		}
		
		return ret;
	}
	public ConfigurationProxyHandlerWrapperUnitTest(Method target, String name) {
		this.target = target;
		this.otherMethods = new HashSet<Method>(Arrays.asList(ConfigurationProxyHandler.class.getMethods()));
		otherMethods.remove(target);
	}
	
	@SuppressWarnings("unchecked")
	@Before
	public void init() {
		wrapped = (ConfigurationProxyHandler<ConfigSpecs>) mock(ConfigurationProxyHandler.class);
		wrapper = new ConfigurationProxyWrapper<ConfigSpecs>(wrapped);
	}
	
	
	@Test
	public void testPassthrough() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		invoke(wrapper, target);
		for(Method other : otherMethods) {
			invoke(verify(wrapped,Mockito.never()),other);
		}
		invoke(verify(wrapped,Mockito.times(1)),target);
	}
	
	private static Object invoke(Object on, Method method) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		Class<?>[] types = method.getParameterTypes();
		int len = types.length;
		return method.invoke(on, new Object[len]);
	}
}
