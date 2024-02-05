package us.lsi.trees;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.colors.GraphColors;
import us.lsi.common.Set2;
import us.lsi.common.String2;
import us.lsi.iterativorecursivos.IterativosyRecursivosSimples;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;


public class TreesTest {
	
	public static <E> Integer size(Tree<E> tree) {
		return switch(tree) {
		case TEmpty<E> t -> 0 ; 
		case TLeaf<E> t -> 1; 
		case TNary<E> t -> 1 + t.children().stream().mapToInt(x->size(x)).sum(); 
		};	
	}
	
	public static <E> Integer size2(Tree<E> tree) {
		return (int)tree.byDepth().filter(t->!t.isEmpty()).count();
	}
	
	public static <E> Integer height(Tree<E> tree) {
		return switch(tree) {
		case TEmpty<E> t -> -1; 
		case TLeaf<E> t -> 0; 
		case TNary<E> t -> 1+ tree.children().stream().mapToInt(x->x.height()).max().getAsInt(); 
		};	
	}
	
	public static <E> E minLabel(Tree<E> tree, Comparator<E> cmp) {
		return tree.byDepth()
		.map(tt -> tt.optionalLabel())
		.filter(x-> x.isPresent())
		.map(x->x.get())
		.min(cmp).get();
	}
	
	
	public static <E> Boolean containsLabel(Tree<E> tree, E label) {
		return switch (tree) {
		case TEmpty<E> t -> false;
		case TLeaf<E> t -> t.label().equals(label);
		case TNary<E> t -> t.label().equals(label)
				|| t.children().stream().anyMatch(x -> containsLabel(x, label));
		};
	}
	
	public static <E> Boolean containsLabel2(Tree<E> tree, E label)  {
		return tree.byDepth()
				.anyMatch(tt->tt.optionalLabel().isEmpty()?false:tt.optionalLabel().get().equals(label));
	}
	
	public static <E> Boolean equals(Tree<E> t1, Tree<E> t2) {
		return switch(t1) {
		case TEmpty<E> t when t2 instanceof TEmpty<E> s -> true;
		case TLeaf<E> t when t2 instanceof TLeaf<E> s-> t.label().equals(s.label());
		case TNary<E> t when t2 instanceof TNary<E> s -> 
					t.label().equals(s.label()) 
					&& t.childrenNumber() == s.childrenNumber()
					&& IntStream.range(0, t.childrenNumber()).allMatch(i -> t.element(i).equals(s.element(i)));
		default -> false;
		};
	}
	
	public static <E> Tree<E> copy(Tree<E> tree) {
		return switch (tree) {
		case TEmpty<E> t -> Tree.empty();
		case TLeaf<E> t -> Tree.leaf(t.label());
		case TNary<E> t -> {
			List<Tree<E>> ch = t.children().stream().map(x -> copy(x)).collect(Collectors.toList());
			yield Tree.nary(t.label(), ch);
		}
		};
	}
	
	public static <E> List<E> toListPostOrden(Tree<E> tree) {
		return switch(tree) {
		case TEmpty<E> t  -> new ArrayList<>(); 
		case TLeaf<E> t -> {
			List<E> r = new ArrayList<>();
			r.add(t.label());
			yield r;
		}
		case TNary<E> t  -> { 
			List<E> r = t.children().stream().flatMap(x->toListPostOrden(x).stream()).collect(Collectors.toList());
			r.add(t.label());	
			yield r;
		}
		};
	}
	
	public static Integer sumIfPredicate(Tree<Integer> tree, Predicate<Integer> predicate) {
		return switch (tree) {
		case TEmpty<Integer> t -> 0;
		case TLeaf<Integer> t -> predicate.test(t.label()) ? t.label() : 0;
		case TNary<Integer> t -> predicate.test(t.label()) ? t.label()
				: 0 + tree.children().stream().mapToInt(x -> sumIfPredicate(x, predicate)).sum();
		};
	}

	public static <E> List<Boolean> niveles(Tree<E> tree, Predicate<Tree<E>> pd) {
		Map<Integer,Boolean> m = tree.byLevel()
				.collect(Collectors.groupingBy(p->p.level(),
						Collectors.collectingAndThen(Collectors.mapping(p->p.tree(),Collectors.toList()),
								ls->ls.stream().allMatch(pd))));
		return m.entrySet()
				.stream().sorted(Comparator.comparing(e->e.getKey()))
				.map(e->e.getValue()).toList();
	}
	
	public static <E> Map<Integer,List<Tree<E>>> parNumeroDeHijos(Tree<E> tree) {
		return tree.byDepth().collect(Collectors.groupingBy(t->t.childrenNumber()));
	}

	
	public static Set<String> palindromas(Tree<Character> tree) {
		Set<String> st = Set2.of();
		palindromas(tree, "", st);
		return st;
	}
	
	public static void palindromas(Tree<Character> tree, String camino, Set<String> st) {
		switch(tree) {	
		case TEmpty<Character> t: break;
		case TLeaf<Character> t: 
			Character label = t.label();
			camino = camino+label;
			if(IterativosyRecursivosSimples.esPalindromo1(camino)) st.add(camino);
			break;
		case TNary<Character> t: 
			label = t.label();
			camino = camino+label;
			for(Tree<Character> tt: t.children()) 
				palindromas(tt,camino,st);
			break;
		}
	}
	
	public static Set<String> palindromas2(Tree<Character> tree) {
		return palindromas2(tree, "");
	}
	
	public static Set<String> palindromas2(Tree<Character> tree, String camino) {
		return switch(tree) {	
		case TEmpty<Character> t -> Set2.of();
		case TLeaf<Character> t -> {
			Character label = t.label();
			camino = camino+label;
			if(IterativosyRecursivosSimples.esPalindromo1(camino)) yield Set2.of(camino);
			else yield Set2.of();
		}
		case TNary<Character> t -> {
			Character label = t.label();
			camino = camino+label;
			Set<String> st = Set2.of();
			for(Tree<Character> tt: t.children()) {
				Set<String> st1 = palindromas2(tt,camino);
				st.addAll(st1);
			}
			yield st;
		}
		};
	}
	
	public static void test1() {
		Tree<Integer> t7 = Tree.parse("34(2,27(_,2,3,4),9(8,_))").map(e->Integer.parseInt(e));	
		System.out.println(t7);
		System.out.println(minLabel(t7,Comparator.naturalOrder()));
		Predicate<Tree<Integer>> pd = t->t.isEmpty() || t instanceof TLeaf<Integer> s &&  s.label()%2==0
				|| t instanceof TNary<Integer> s &&  s.label()%2==0;
		String2.toConsole("%s",niveles(t7,pd));
	}
	
	public static void test2() {
		Tree<Character> tree = Tree.parse("r(a(p(a(r)),d(a(_,r),i(o,_)),t),t,u(t(a)))").map(t->t.charAt(0));
		String2.toConsole("%s",tree);
		String2.toConsole("%s",palindromas(tree));
		String2.toConsole("%s",palindromas2(tree));
		Tree<String> tree2 = Tree.parse("[2.;3.](a(p(a(r)),d(a(_,r),i(o,_)),t),t,u(t(a)))");
		String2.toConsole("%s",tree2);
		GraphColors.toDot(tree2,"ficheros/tree3.gv");
	}
	
	public static void main(String[] args) {
		test2();
	}

}
