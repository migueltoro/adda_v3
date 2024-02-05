package us.lsi.enero_2024.e4;

import us.lsi.common.Multiset;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Familia {

	public static Multiset<Datos> proporcionPadre(Tree<Datos> tree) {
		return switch (tree) {
		case TEmpty<Datos> t -> Multiset.empty();
		case TLeaf<Datos> t -> Multiset.of(t.label());	
		case TNary<Datos> t -> t.children().stream()
			.map(h->proporcionPadre(h))
			.reduce(Multiset.of(t.label()),(m1,m2)->m1.add(m2));	
		};		
	}
	
	public static Boolean enraizada(Tree<Datos> tree) {
		return switch (tree) {
		case TEmpty<Datos> t -> true;
		case TLeaf<Datos> t -> true;	
		case TNary<Datos> t -> 
			t.children().stream()
				.anyMatch(h->h.optionalLabel().get().nombre().equals(t.label().nombre()))
			&& t.children().stream()
				.allMatch(h->Familia.enraizada(h));		
		};		
	}
}
