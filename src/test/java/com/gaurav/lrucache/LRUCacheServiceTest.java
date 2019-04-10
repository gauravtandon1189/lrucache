package com.gaurav.lrucache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LRUCacheServiceTest {

	private LRUCacheService classUnderTest;

	@Before
	public void setup() {
		classUnderTest = new LRUCacheService();
	}

	@Test
	public void testPut() {
		// Act
		Response res = classUnderTest.put(1, 10);
		// Assert
		assertEquals(200, res.getStatus());
	}

	@Test
	public void testPutWithCacheInvalidate() {
		// Setup
		classUnderTest.put(1, 10);
		classUnderTest.put(2, 10);

		// Act
		Response res = classUnderTest.put(3, 10);
		HashMap<Integer, Integer> resMap = (HashMap) res.getEntity();

		// Assert
		assertEquals(200, res.getStatus());
		assertEquals(1, resMap.size());

		Map.Entry<Integer, Integer> invalidatedEntry = resMap.entrySet().iterator().next();
		assertEquals(1, invalidatedEntry.getKey().intValue());
		assertEquals(10, invalidatedEntry.getValue().intValue());
	}

	@Test
	public void testGet_EntryFoundInCache() {
		// Setup
		classUnderTest.put(1, 10);
		// Act
		Response res = classUnderTest.get(1);
		HashMap<Integer, Integer> resMap = (HashMap) res.getEntity();
		// Assert
		assertEquals(200, res.getStatus());
		assertEquals(1, resMap.size());

		Map.Entry<Integer, Integer> cacheEntry = resMap.entrySet().iterator().next();
		assertEquals(1, cacheEntry.getKey().intValue());
		assertEquals(10, cacheEntry.getValue().intValue());

	}

	@Test
	public void testGet_EntryNotFoundInCache() {
		// Act
		Response res = classUnderTest.get(10);
		// Assert
		assertEquals(404, res.getStatus());
	}
}
