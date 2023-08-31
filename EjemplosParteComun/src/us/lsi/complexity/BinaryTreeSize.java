package us.lsi.complexity;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem.Evaluation;

import us.lsi.colors.GraphColors;
import us.lsi.common.Files2;
import us.lsi.common.Trio;
import us.lsi.curvefitting.DataFile;
import us.lsi.curvefitting.Fitting;
import us.lsi.curvefitting.FittingType;
import us.lsi.graphics.MatPlotLib;
import us.lsi.tiposrecursivos.BinaryTree;

public class BinaryTreeSize {
	
	public static BinaryTree<Long> btSize(Long n,String file,Predicate<Map.Entry<Long,BinaryTree<Long>>> p){
		Map<Long,BinaryTree<Long>> mem = new HashMap<>();
		BinaryTree<Long> r = btSize(n, mem);
		Stream<String> st = mem.entrySet().stream()
				.filter(p)
				.sorted(Comparator.comparing(e->e.getKey()))
				.map(e->String.format("%d,%d",e.getKey(),e.getValue().sizeDifferent()));	
		Files2.toFile(st,file);
		return r;
	}

	private static BinaryTree<Long> btSize(Long n, Map<Long,BinaryTree<Long>> mem) {
		BinaryTree<Long> r = null;
		if(mem.containsKey(n)) {
			r = mem.get(n);
		} else if(n<=1) {
			r = BinaryTree.leaf(n);
			mem.put(n, r);
		} else {
			BinaryTree<Long> left= btSize(n/2,mem);
			BinaryTree<Long> right= btSize(n/3,mem);
			r = BinaryTree.binary(n,left,right);
			mem.put(n, r);
		}
		return r;
	}
	
	public static void test0(Long n,String file) {	
		BinaryTree<Long> t = BinaryTreeSize.btSize(n,new HashMap<>());
		GraphColors.toDot(t,file,v->v.optionalLabel().get().toString());
	}
	
	public static void test1(Long n,String file,Predicate<Map.Entry<Long,BinaryTree<Long>>> p) {		
		System.out.println(btSize(n,file,p).sizeDifferent());
	}

	public static void test2(String file) {
		List<WeightedObservedPoint> data = DataFile.points(file);
		Trio<Function<Double, Double>,String,Evaluation> f = Fitting.fitCurve(data, FittingType.LOG3);
		MatPlotLib.show(file, f.first(), f.second());
	}
	
	public static void main(String[] args) {
		String file = "ficheros/tree1.gv";
//		test0(1000L,file);
		test1(10000L,file,e->e.getKey()>1000);
//		test2(file);
	}

}
