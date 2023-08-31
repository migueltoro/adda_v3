package us.lsi.p3_21_22;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejercicio2_21_22 {
	
	public static String caminoStringMinimo1(Tree<String> t, Predicate<String> p) {
		return caminoStringMinimo1(t,p,"");
	}
	
	private static String caminoStringMinimo1(Tree<String> tree,Predicate<String> p,String ct) {
		return switch(tree) {
		case TEmpty<String> t-> null;
		case TLeaf<String> t-> {
			String r = null;
			if(!p.test(t.label()))
				r = ct+t.label();
			yield r;
		}	
		case TNary<String> t -> {
			String r = null;
			if(!p.test(t.label()))
				r = t.children().stream()
						.map(x->caminoStringMinimo1(x,p,ct+t.label()))
						.filter(x->x!=null)
						.min(Comparator.comparing(x->x.length()))
						.get();	
			yield r;
		}
		};
	}
	
	public static String caminoStringMinimo2(Tree<String> tree, Predicate<String> p) {
		List<String> r = new ArrayList<>();
		caminoStringMinimo2(tree,p,r,"");
		return r.isEmpty()?null:r.get(0);
	}
	
	private static void actualizaLista(List<String> ls, String c) {
		if(ls.isEmpty()) ls.add(c);
		else {
			String r = ls.remove(0);
			String rm = Stream.of(r,c).min(Comparator.comparing(x->x.length())).get();
			ls.add(rm);
		}
	}
	
	private static void caminoStringMinimo2(Tree<String> tree,Predicate<String> p,List<String> a,String ct) {
		switch(tree) {
		case TLeaf<String> t when !p.test(t.label()) -> {actualizaLista(a,ct+t.label());}	
		case TNary<String> t when !p.test(t.label()) -> 
			{t.children().stream().forEach(x->caminoStringMinimo2(x,p,a,ct+t.label()));}
		default -> {;}
		}
	}
	
	public static void main(String[] args) {
		Tree<String> tree = Tree.parse("ab(bb(_,acc(d,ec,f),ffff),abf(b(f),c))");
		String r = caminoStringMinimo1(tree,c->c.contains("c"));
		System.out.println(r);
		r = caminoStringMinimo2(tree,c->c.contains("c"));
		System.out.println(r);
	}

}


