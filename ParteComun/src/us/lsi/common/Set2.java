package us.lsi.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.streams.Stream2;

public class Set2 {
	
	public static <E> Set<E> parse(String s, String sep, Function<String,E> f_map) {
		return Arrays.stream(s.split("["+sep+"]"))
		.filter(e->e!=null && e.length()>0)
		.map(e->f_map.apply(e.trim())).collect(Collectors.toSet());
	}
	
	public static <E> Set<E> parseSet(String[] tokens, Function<String,E> f_map) {
		return Arrays.stream(tokens)
		.filter(e->e!=null && e.length()>0)
		.map(e->f_map.apply(e.trim())).collect(Collectors.toSet());
	}

	public static Set<Integer> range(Integer a, Integer b, Integer c){		
		return Stream2.range(a, b, c).boxed()
			 .collect(Collectors.toSet());
	}	
	
	public static Set<Integer> range(Integer a, Integer b){
		return IntStream.range(a,b).boxed().collect(Collectors.toSet());
	}
	
	public static <T> Set<T> empty(){
		return new HashSet<>();
	}
	
	public static <T> Set<T> copy(Collection<T> c){
		return new HashSet<>(c);
	}
	
	public static <T extends Comparable<? super T>> SortedSet<T> newTreeSet(){
		return new TreeSet<>();
	}
	
	public static <T> SortedSet<T> newTreeSet(Comparator<T> cmp){
		return new TreeSet<>(cmp);
	}
	@SafeVarargs
	public static <E> Set<E> of(E... e){
		return Arrays.stream(e).collect(Collectors.toSet());
	}

	public static <E,U extends Collection<E>> Set<E> of(U elements){
		return elements.stream().collect(Collectors.toSet());
	}
	
	public static <E> Set<E> difference(Collection<E> s1,  Collection<E> s2){
		Set<E> s = new HashSet<>(s1);
		s.removeAll(s2);
		return s;
	}
	
	public static <E> Set<E> union(Collection<E> s1,  Collection<E> s2){
		Set<E> s = new HashSet<>(s1);
		s.addAll(s2);
		return s;
	}
	
	public static <E> Set<E> intersection(Collection<E> s1,  Collection<E> s2){
		Set<E> s = new HashSet<>(s1);
		s.retainAll(s2);
		return s;
	}
}
