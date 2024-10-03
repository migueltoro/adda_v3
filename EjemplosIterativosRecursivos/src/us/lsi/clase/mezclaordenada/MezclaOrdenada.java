package us.lsi.clase.mezclaordenada;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import us.lsi.common.Comparator2;
import us.lsi.common.List2;
import us.lsi.common.Printers;
import us.lsi.iterables.Iterables;
import us.lsi.iterables.IteratorFusionOrdered;
import us.lsi.iterables.IteratorMap;

public class MezclaOrdenada {
	
	public static <E> List<E> mezclaOrdenada(List<E> ls1, List<E> ls2, Comparator<E> cmp) {
		List<E> ls3 = new ArrayList<>();
		Integer i1, i2;
		Integer n1 = ls1.size();
		Integer n2 = ls2.size();
		i1 = 0;
		i2 = 0;
		while (n1 + n2 - i1 -i2 > 0) {
			E e;
			if (i2 >= n2 || i1 < n1 && Comparator2.isLE(ls1.get(i1), ls2.get(i2), cmp)) {
				e = ls1.get(i1);
				i1 = i1 + 1;
			} else {
				e = ls2.get(i2);
				i2 = i2 + 1;
			}
			ls3.add(e);
		}
		return ls3;
	}

	public static <E>  void mezclaOrdenada(Iterable<E> it1, Iterable<E> it2, String fileOut, Comparator<E> cmp) {
		Iterator<E> itt1 = it1.iterator();
		Iterator<E> itt2 = it2.iterator();
		PrintStream f = Printers.file(fileOut);
		E e1 = itt1.hasNext()?itt1.next():null;
		E e2 = itt2.hasNext()?itt2.next(): null;
		while(e1 != null || e2 != null){
			E e=null;
			if(e2==null || Comparator2.isLE(e1,e2,cmp)){
				e = e1;
				e1 = itt1.hasNext()?itt1.next():null; 
			} else {
				e = e2;
				e2 = itt2.hasNext()?itt2.next():null; 
			}		
			f.println(e.toString());
		}
		f.close();
	}
	
	public static void test7() throws IOException {
		List<Integer> l1 = List2.of(1,3,5,7,9,11);
		List<Integer> l2 = List2.of(0,2,4,10,19,21,23,45);
		List<Integer> l3 = mezclaOrdenada(l2,l1, Comparator.naturalOrder());
		Iterator<Integer> it1 = Files.lines(Path.of("ficheros/numeros.txt")).map(x->Integer.parseInt(x)).iterator();
		Iterator<Integer> it2 = Files.lines(Path.of("ficheros/numeros4.txt")).map(x->Integer.parseInt(x)).iterator();
		mezclaOrdenada(()->it1,()->it2,"ficheros/numeros_sal.txt",Comparator.naturalOrder());	
		System.out.println(String.format("5: %s",l3));
	}
	
	public static void test8() {
		Iterable<String> f1 = Iterables.file("ficheros/numeros3.txt");
		Iterable<Integer> f11 = IteratorMap.of(f1, x->Integer.parseInt(x));
		Iterable<String> f2 = Iterables.file("ficheros/numeros4.txt");
		Iterable<Integer> f22 = IteratorMap.of(f2, x->Integer.parseInt(x));
		mezclaOrdenada(f11,f22,"ficheros/numeros5.txt",Comparator.naturalOrder());
		f1 = Iterables.file("ficheros/numeros3.txt");
		f11 = IteratorMap.of(f1, x->Integer.parseInt(x));
		f2 = Iterables.file("ficheros/numeros4.txt");
		f22 = IteratorMap.of(f2, x->Integer.parseInt(x));
		Iterable<Integer> f3 = IteratorFusionOrdered.of(f11,f22,Comparator.naturalOrder());
		PrintStream p = Printers.file("ficheros/numeros6.txt");
		for(Integer er:f3) p.println(er.toString());
	}

}
