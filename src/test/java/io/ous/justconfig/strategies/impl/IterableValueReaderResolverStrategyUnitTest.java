package io.ous.justconfig.strategies.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import io.ous.justconfig.strategies.impl.IterableValueReaderResolverStrategy;
import io.ous.justconfig.values.ValueReaderService;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class IterableValueReaderResolverStrategyUnitTest {
	@Test
	public void test() {
		ValueReaderService notAccepting = mock(ValueReaderService.class),
						accepting = mock(ValueReaderService.class);
		
		when(accepting.readable(any(Class.class))).thenReturn(true);
		when(notAccepting.readable(any(Class.class))).thenReturn(false);
		List<ValueReaderService> readers = new ArrayList<ValueReaderService>();
		readers.add(notAccepting);
		readers.add(accepting);
		IterableValueReaderResolverStrategy strategy = new IterableValueReaderResolverStrategy(readers);
		ValueReaderService service = strategy.getValueReaderService(null, String.class);
		
		Assert.assertEquals(accepting, service);
		
		verify(notAccepting).readable(String.class);
		Mockito.verifyNoMoreInteractions(notAccepting);
		
		verify(accepting).readable(String.class);
		Mockito.verifyNoMoreInteractions(accepting);
	}
}
