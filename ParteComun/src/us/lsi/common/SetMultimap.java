package us.lsi.common;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SetMultimap<K, V>  {

		
	public static <K, V> SetMultimap<K, V> create() {
		return new SetMultimap<K, V>();
	}

	public static <K, V> SetMultimap<K, V> create(SetMultimap<K, V> m) {
		return new SetMultimap<K, V>(m);
	}

	private Map<K,Set<V>> map;

	private SetMultimap(){
		this.map = new HashMap<>();
	}
		
	private SetMultimap(SetMultimap<K,V> m){
		this.map = new HashMap<>(m.map);;
	}

	public Map<K, Set<V>> asMap() {
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


	public Set<V> get(K key) {
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
		if(!map.containsKey(key)) map.put(key, new HashSet<>());
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
