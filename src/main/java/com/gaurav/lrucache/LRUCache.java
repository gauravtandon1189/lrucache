package com.gaurav.lrucache;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Data structure for Least Recently Used Cache.
 */
public class LRUCache {

	private final int CACHE_CAPACITY;

	private Hashtable<Integer, Node> cache = new Hashtable<Integer, Node>();
	private int size;
	private Node front, rear;

	/**
	 * Constructor for LRUCache
	 * 
	 * @param capacity
	 */
	public LRUCache(int capacity) {
		this.CACHE_CAPACITY = capacity;
		this.size = 0;
		front = new Node();
		rear = new Node();

		front.next = rear;
		rear.prev = front;
	}

	/**
	 * Gets the associated cache value for the specified key.
	 * 
	 * @param key
	 * @return associated value in cache or -1 if no value found for key.
	 */
	public Integer get(Integer key) {
		Node node = cache.get(key);
		if (node == null) {
			return -1;
		}
		bringNodeToFront(node);
		return node.value;
	}

	/**
	 * Puts the provided <key,value> pair in the cache.
	 * 
	 * @param key
	 * @param value
	 * @return the invalidated cache entry or an empty map if no entry was
	 *         invalidated.
	 */
	public Map<Integer, Integer> put(Integer key, Integer value) {
		HashMap<Integer, Integer> map = new HashMap<>();
		Node node = cache.get(key);

		if (node == null) {
			Node newNode = new Node();
			newNode.key = key;
			newNode.value = value;
			addNode(newNode);

			cache.put(key, newNode);
			size = size + 1;
			if (size > CACHE_CAPACITY) {
				Node rear = removeRearNode();
				cache.remove(rear.key);
				size = size - 1;
				map.put(rear.key, rear.value);
				return map;
			}
		} else {
			node.value = value;
			bringNodeToFront(node);
		}
		return map;
	}

	/** Internal data structure object and helper methods for the cache */

	class Node {
		Integer key;
		Integer value;
		Node next;
		Node prev;

		@Override
		public String toString() {
			return "[key:" + key + ",value:" + value + "]";
		}
	}

	private void addNode(Node node) {
		node.prev = front;
		node.next = front.next;
		front.next.prev = node;
		front.next = node;
	}

	private void deleteNode(Node node) {
		Node prev = node.prev;
		Node next = node.next;
		prev.next = next;
		next.prev = prev;
	}

	private void bringNodeToFront(Node node) {
		deleteNode(node);
		addNode(node);
	}

	private Node removeRearNode() {
		Node res = rear.prev;
		deleteNode(res);
		return res;
	}
}
