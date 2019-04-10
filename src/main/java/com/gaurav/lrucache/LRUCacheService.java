package com.gaurav.lrucache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Main service class with GET/PUT methods on the LRUCache.
 */
@Path("v1")
public class LRUCacheService {

	// Update the below CACHE_CAPACITY value for increasing/decreasing the cache
	// capacity as required.
	private static final Integer CACHE_CAPACITY = 2;
	private static final LRUCache CACHE = new LRUCache(CACHE_CAPACITY);

	private static final Logger log = LoggerFactory.getLogger(LRUCacheService.class);

	/**
	 * GET API that returns the value stored in the cache for a provided key. In
	 * case the key is not found, a 404 exception is thrown.
	 * 
	 * @param key for which to fetch the cache value.
	 * @return the cache value for the provided key.
	 * @throws Exception
	 */
	@GET
	@Path("get/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("key") Integer key) {

		log.info("GET API called for key: {}.", key);

		HashMap<Integer, Integer> map = new HashMap<>();
		Integer value = CACHE.get(key);

		if (value == -1) {
			log.info("Did not find cache value for key: {}.", key);
			return Response.status(404, "Key not found in cache!").build();
		} else {
			log.info("Found cache value: {}, for key: {}.", value, key);
			map.put(key, value);
			return Response.ok(map, MediaType.APPLICATION_JSON).build();
		}
	}

	/**
	 * PUT API that takes in a key and a value and sets the key to the specified
	 * value OR inserts the key-value pair in the cache if the key is not already
	 * present. When the cache reached its capacity, it invalidates the least
	 * recently used item before inserting a new item and returns the invalidated
	 * item.
	 * 
	 * @param key for which to set/insert the cache value.
	 * @return the value to be set for the provided key.
	 */
	@PUT
	@Path("put/{key}/{value}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response put(@PathParam("key") Integer key, @PathParam("value") Integer value) {

		log.info("PUT API called for key: {}, value: {}.", key, value);

		Map<Integer, Integer> invalidatedItem = CACHE.put(key, value);
		log.info("Value {} successfully set for key {} in the cache.", value, key);

		if (!invalidatedItem.isEmpty()) {
			log.info("Invalidated cache entry - {}", invalidatedItem);
		}
		return Response.ok(invalidatedItem, MediaType.APPLICATION_JSON).build();
	}
}
