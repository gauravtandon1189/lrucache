package com.gaurav.lrucache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LRUCacheTest {

	private LRUCache classUnderTest;

	@Before
	public void setup() {
		classUnderTest = new LRUCache(2);
	}

	@Test
	public void testPut() {
		// Act
		Map<Integer, Integer> invalidatedItem = classUnderTest.put(1, 10);
		// Assert
		assertEquals(0, invalidatedItem.size());
	}

	@Test
	public void testPutWithCacheInvalidate() {
		Map<Integer, Integer> invalidatedItem = new HashMap<>();

		// Act
		invalidatedItem = classUnderTest.put(1, 10);
		// Assert
		assertEquals(0, invalidatedItem.size());

		// Act
		invalidatedItem = classUnderTest.put(2, 20);
		// Assert
		assertEquals(0, invalidatedItem.size());

		// Act
		invalidatedItem = classUnderTest.put(3, 30);
		// Assert
		assertEquals(1, invalidatedItem.size());
		assertEquals(10, invalidatedItem.get(1).intValue());
	}

	@Test
	public void testGet_EntryFoundInCache() {
		// Setup
		classUnderTest.put(1, 10);
		// Act
		Integer cacheValue = classUnderTest.get(1);
		// Assert
		assertEquals(10, cacheValue.intValue());
	}

	@Test
	public void testGet_EntryNotFoundInCache() {
		// Act
		Integer cacheValue = classUnderTest.get(10);
		// Assert
		assertEquals(-1, cacheValue.intValue());
	}
}
