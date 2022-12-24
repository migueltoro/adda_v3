package us.lsi.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class ListMultimap<K, V>  {

	private HashMap<K,List<V>> map;

	public ListMultimap() {
		super();
		this.map = new HashMap<K, List<V>>();
	}

	public Map<K, List<V>> asMap() {
		return map;
	}

	public void clear() {
		map.clear();
	}


	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsEntry(Object key, Object value) {
		return map.containsKey(key) && map.get(key).contains(value);
	}
	
	public boolean containsValue(Object v) {
		return this.values().contains(v);
	}


	public boolean equals(Object object) {
		return map.equals(object);
	}


	public List<V> get(K key) {
		return map.get(key);
	}

	public int hashCode() {
		return map.hashCode();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public boolean put(K key, V value) {
		if(!map.containsKey(key)) map.put(key, new ArrayList<>());
		return map.get(key).add(value);
	}

	public int size() {
		return map.size();
	}

	public String toString() {
		return map.toString();
	}

	public Set<V> values() {
		return map.keySet()
				.stream()
				.flatMap(x->map.get(x).stream())
				.collect(Collectors.toSet());
	}
	
}
