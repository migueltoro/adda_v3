package us.lsi.trees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import us.lsi.colors.GraphColors;
import us.lsi.common.List2;
import us.lsi.common.String2;
import us.lsi.tiposrecursivos.AVLTree;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.BinaryTrees; 


public class BinaryTreesTest {
	
	public static <E> Integer size(BinaryTree<E> tree) {
		return switch (tree) {
		case BEmpty<E>  t-> 0;
		case BLeaf<E>  t -> 1;
		case BTree<E>  t -> 1 + size(t.left()) + size(t.right());
		};
	}
		
	public static <E> BinaryTree<E> copy(BinaryTree<E> tree) {
		return switch(tree) {
		case BEmpty<E> t -> BinaryTree.empty(); 
		case BLeaf<E> t -> BinaryTree.leaf(t.label()); 
		case BTree<E> t -> BinaryTree.binary(t.label(), copy(t.left()), copy(t.right()));	
		};	
	}
		
	public static <E> BinaryTree<E> reverseCopy(BinaryTree<E> tree) {
		return switch(tree) {
		case BEmpty<E> t -> BinaryTree.empty(); 
		case BLeaf<E> t -> BinaryTree.leaf(t.label()); 
		case BTree<E> t -> BinaryTree.binary(t.label(), reverseCopy(t.right()), reverseCopy(t.left()));
		};	
	}
	
	public static <E> List<E> toListPostOrden(BinaryTree<E> tree) {
		return switch(tree) {
		case BEmpty<E> t -> new ArrayList<>(); 
		case BLeaf<E> t -> {
			List<E> r = new ArrayList<>();
			r.add(t.label());
			yield r;
		}
		case BTree<E> t -> { 
			List<E> r = toListPostOrden(t.left());
			r.addAll(toListPostOrden(t.right()));
			r.add(t.label());	
			yield r;
		}
		};
	}

	public static Integer sumIfPredicate(BinaryTree<Integer> tree, Predicate<Integer> predicate) {
		return switch (tree) {
		case BEmpty<Integer> t -> 0;
		case BLeaf<Integer> t -> predicate.test(t.label()) ? t.label() : 0;
		case BTree<Integer> t -> predicate.test(t.label()) ? t.label()
				: 0 + sumIfPredicate(t.left(), predicate) + sumIfPredicate(t.right(), predicate);
		};
	}
	
	public static Integer sumIfPredicate2(BinaryTree<Integer> tree, Predicate<Integer> predicate) {
		return tree.byDepth()
		.map(t -> t.optionalLabel().orElse(0))
		.filter(predicate)
		.mapToInt(lb -> lb).sum();
	}

	public static Boolean sumaEtiquetas(BinaryTree<Integer> tree) {
		return tree.byDepth()
				.filter(e->e instanceof BTree<Integer> t && !t.left().isEmpty() && !t.right().isEmpty())
				.allMatch(e->e instanceof BTree<Integer> t && 
						t.label().equals(t.left().optionalLabel().get()+t.right().optionalLabel().get()));
	}
	
	public static Boolean existeLista(BinaryTree<Character> tree, List<Character> ls) {
		Integer n = ls.size();
		return switch (tree) {
		case BEmpty<Character> t when ls.isEmpty() -> true;
		case BLeaf<Character> t when !ls.isEmpty() -> ls.get(0).equals(t.label());
		case BTree<Character> t when !ls.isEmpty() -> ls.get(0).equals(t.label())
				&& (existeLista(t.left(), ls.subList(1, n)) || existeLista(t.right(), ls.subList(1, n)));
		};
	}
	
	public static record TT(Integer p, List<Integer> ls) {
		public static TT of(List<Integer> ls) {
			Integer p = ls.stream().reduce(1,(x,y)->x*y);
			return new TT(p,ls);
		}
	}
	
	public static TT maxCamino(BinaryTree<Integer> tree) {
		return maxCamino(tree,List.of());
	}
	
	public static TT maxCamino(BinaryTree<Integer> tree, List<Integer> camino) {
		return switch(tree) {	
		case BEmpty<Integer> t -> null;
		case BLeaf<Integer> t -> TT.of(List2.addLast(camino,t.label()));
		case BTree<Integer> t -> {
			camino = List2.addLast(camino,t.label());
			TT left = maxCamino(t.left(),camino);
			TT right = maxCamino(t.right(),camino);
			yield Stream.of(left,right).filter(x->x!=null).max(Comparator.comparing(x->x.p())).orElse(null);
		}
		};
	}
	
	public static void test1() {
		BinaryTree<Integer> t = BinaryTree.empty();
		List<Integer> ls = List.of(1,7,2,5,10,9,-1,14,67,67);
		Comparator<Integer> cmp = Comparator.naturalOrder();
		BinaryTree<Integer> tree = BinaryTrees.addOrdered(t,ls,cmp);
		System.out.println(tree.toString());
		System.out.println(tree.isOrdered(Comparator.naturalOrder()));
		Integer e = 10;
		BinaryTree<Integer> tree2 = BinaryTrees.removeOrdered(tree,e,Comparator.naturalOrder());
		System.out.println(tree2.toString());
		System.out.println(tree2.isOrdered(Comparator.naturalOrder()));
		BinaryTree<Integer> tree3 = BinaryTrees.removeOrdered(tree2,9,Comparator.naturalOrder());
		System.out.println(tree3.toString());
		System.out.println(tree3.isOrdered(Comparator.naturalOrder()));
		System.out.println(sumIfPredicate(tree3,x->x%2==0));
		BinaryTree<Integer> tree4 = copy(tree);
		System.out.println(tree.equals(tree4));
	}
	
	public static void test2() {
		BinaryTree<Integer> tree =  BinaryTree.parse("1(2(-1,-4(3,/_)),10(-5(7(/_,-2),4),-6))")
				.map(label->Integer.parseInt(label));
		GraphColors.toDot(tree,"ficheros/tree.gv");
		String2.toConsole("%s",maxCamino(tree));	
	}
	
	public static void test4() {
		String ex = "-43.7(2.1,56.7(-27.3(_,2),78.2(3,4)))";
		String ex2 = "-43.7(2.1,5(_,8.(3.,5.)))";
		BinaryTree<String> t7 = BinaryTree.parse(ex);	
		BinaryTree<String> t8 = BinaryTree.parse(ex2);	
		System.out.println(t7);
		System.out.println(t8);
		System.out.println(BinaryTrees.equilibrateType(t7));
		System.out.println(BinaryTrees.equilibrateType(t8));
		GraphColors.toDot(t7,"ficheros/binary_tree.gv");
		BinaryTree<String> t9 = t7.equilibrate();
		BinaryTree<String> t10 = t8.equilibrate();
		GraphColors.toDot(t9,"ficheros/binary_tree_equilibrate.gv");
		System.out.println(t9);
		System.out.println(t10);
		BinaryTree<Double> t11 =  t7.map(e->Double.parseDouble(e));
		System.out.println(t11);
	}
	
	public static void test5() {
		String ex = "4(2(1(0,_),3),7(5(_,6),10(9(8,_),11(_,12))))";
		BinaryTree<Integer> t0 = BinaryTree.parse(ex, e->Integer.parseInt(e));
		System.out.println(BinaryTrees.equilibrateType(t0));
		System.out.println(BinaryTrees.isOrdered(t0,Comparator.naturalOrder()));
		BinaryTree<Integer> t1 = BinaryTrees.addOrdered(t0,13,Comparator.naturalOrder());
		System.out.println("t1 = "+t1);
		System.out.println(BinaryTrees.equilibrateType(t1));
		System.out.println(BinaryTrees.isOrdered(t1,Comparator.naturalOrder()));
		GraphColors.toDot(t1,"ficheros/t1.gv");
		BinaryTree<Integer> t2 = switch(t1) {
		case BEmpty<Integer> t -> t;
		case BLeaf<Integer> t  -> t;
		case BTree<Integer> t -> BinaryTree.binary(t.label(),t.left(),t.right().equilibrate());
		};
		System.out.println("t2 = "+t2);
		System.out.println(BinaryTrees.equilibrateType(t2));
		System.out.println(BinaryTrees.isOrdered(t2,Comparator.naturalOrder()));
		GraphColors.toDot(t2,"ficheros/t2.gv");
		String t4 = "10(_,11(_,12(_,13)))";
		BinaryTree<Integer> t42 = BinaryTree.parse(t4, e->Integer.parseInt(e));
		BinaryTree<Integer> t43 = t42.equilibrate();
		System.out.println("t43 = "+t43);
		GraphColors.toDot(t43,"ficheros/t43.gv");
	}
	
	public static void test6() {
		BinaryTree<Integer> tree = BinaryTree.empty();
		for (int i = 0; i < 50 ; i++) {
			tree = BinaryTrees.addOrdered(tree, i, Comparator.naturalOrder());
//			System.out.println(tree.tree().height()+" == "+tree.tree());
		}	
		tree = BinaryTrees.equilibrate(tree);
		GraphColors.toDot(tree,"ficheros/avl_tree.gv");
	}
	
	public static void testAVLTree1() {
		AVLTree<Integer> tree = AVLTree.of();
		for (int i = 0; i < 500 ; i++) {
			tree.add(i);
		}	
		for (int i = 0; i < 50 ; i++) {
			tree.remove(i);
		}
		for (int i = 600; i > 450 ; i--) {
			tree.remove(i);
		}	
		String2.toConsole(String.format("%d,%d,%d", tree.firstLabel().get(),tree.lastLabel().get(),tree.height()));
		String2.toConsole(String.format("%s",tree.toString()));
		GraphColors.toDot(tree.tree(),"ficheros/avl_tree.gv");
	}
	
	public static void testAVLTree2() {
		AVLTree<Integer> tree = AVLTree.of();
		for (int i = 0; i < 50 ; i++) {
			tree.add(i);
		}
		tree.remove(30);
		GraphColors.toDot(tree.tree(),"ficheros/avl_tree.gv");
	}

	public static void main(String[] args) {
		test2();
	}	
	
}
