package us.lsi.iterativorecursivos;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.common.Comparator2;
import us.lsi.common.Enumerate;
import us.lsi.common.IntPair;
import us.lsi.common.List2;
import us.lsi.common.LongPair;
import us.lsi.common.Pair;
import us.lsi.common.Preconditions;
import us.lsi.common.Printers;
import us.lsi.common.String2;
import us.lsi.common.View2E;
import us.lsi.streams.Stream2;
import us.lsi.iterables.Iterables;
import us.lsi.iterables.IteratorFusionOrdered;
import us.lsi.iterables.IteratorMap;
import us.lsi.math.Math2;

public class IterativosyRecursivosSimples {
	
	
	
	public static record Cr(Integer c, Integer r) {}
	
	public static Cr cr(Integer a, Integer b) {
		return cr(a,b,0,a);
	}
	public static Cr cr(Integer a, Integer b, Integer c, Integer r) {
		Cr s = new Cr(c,r);
		if(r-b>0){
			s = cr(a,b,c+1,r-b);
		}
		return s;
	}
	
	public static Stream<Long> divisores(Long n){
		return Stream.iterate(2L, x-> x <= (long) Math.sqrt(n), x -> x+1).filter(x->n%x==0);
	}
	
	public static Integer sumaPrimos(String file) {
		return Stream2.file(file)
				.flatMap(e->Stream2.split(e,"[ ,]"))
				.mapToInt(e->Integer.parseInt(e))
				.filter(e->Math2.esPrimo(e))
				.sum();
	}
	
	public static Integer sumaPrimos1(String file) {
		Iterator<String> fileIt = Iterables.file(file).iterator();
		Integer suma = 0;
		while(fileIt.hasNext()){
			String linea = fileIt.next();
			Iterator<String> lineaIt = Iterables.split(linea,"[ ,]").iterator();
			while(lineaIt.hasNext()){
	     		String p = lineaIt.next();
				Integer e = Integer.parseInt(p);
				if(Math2.esPrimo(e)){
	 				suma = suma + e;
				}
	 		}
		}
	 	return suma;
	}

	
	public static Integer sumaPrimos2(String file) {
		Iterable<String> fileIt = Iterables.file(file);
		Integer suma = 0;
		for(String linea: fileIt) {		
			for(String e: Iterables.split(linea, "[ ,]")) {
				Integer en = Integer.parseInt(e);
				if (Math2.esPrimo(en)) {
					suma = suma + en;
				}
			}
		}
		return suma;
	}

	public static Integer sumaPrimos3(String file) {
		Iterable<String> fileIt = Iterables.file(file);
		Iterable<String> ff = Iterables.flatMap(fileIt,linea->Iterables.split(linea,"[ ,]"));
		return sumaPrimos3(ff.iterator(),0);
	}
	private static Integer sumaPrimos3(Iterator<String> ff, Integer b) {
		if(ff.hasNext()) {
			String p = ff.next();
			Integer en = Integer.parseInt(p);
			if (Math2.esPrimo(en)) {
				b = b + en;
			}
			b = sumaPrimos3(ff,b);
		}
		return b;
	}
	

	public static Map<Integer,List<Integer>> agrupaPorResto1(String file,Integer n) {
		return Stream2.file(file)
				.filter(ln->!ln.isEmpty())
				.flatMap(e->Stream2.split(e,"[ ,]"))
				.map(e->Integer.parseInt(e))
				.collect(Collectors.groupingBy(e->e%n));
	}
	
	public static Map<Integer, List<Integer>> agrupaPorResto2(String file, Integer n) {
		Iterable<String> fileIt = Iterables.file(file);
		Map<Integer, List<Integer>> grupos = new HashMap<>();
		for(String linea:fileIt) {
			for(String e:Iterables.split(linea, "[ ,]")) {
				Integer en = Integer.parseInt(e);
				Integer key = en % n;
				List<Integer> ls;
				if (grupos.containsKey(key)) {
					ls = grupos.get(key);
				} else {
					ls = new ArrayList<>();
					grupos.put(key, ls);
				}
				ls.add(en);
			}
		}
		return grupos;
	}
	
	public static Map<Integer, List<Integer>> agrupaPorResto3(String file, Integer n) {
		Iterator<String> fileIt = Iterables.file(file).iterator();
		Map<Integer, List<Integer>> grupos = new HashMap<>();
		while(fileIt.hasNext()) {
			String linea = fileIt.next();
			Iterator<String> lineaIt = Iterables.split(linea, "[ ,]").iterator();
			while(lineaIt.hasNext()) {
				String e = lineaIt.next();
				Integer en = Integer.parseInt(e);
				Integer key = en % n;
				List<Integer> ls;
				if (grupos.containsKey(key)) {
					ls = grupos.get(key);
				} else {
					ls = new ArrayList<>();
					grupos.put(key, ls);
				}
				ls.add(en);
			}
		}
		return grupos;
	}
	
	public static Map<Integer, List<Integer>> agrupaPorResto4(String file, Integer n) {
		Iterator<String> fileIt = Iterables.file(file).iterator();
		String linea = fileIt.next();
		Iterator<String> lineaIt = Iterables.split(linea, "[ ,]").iterator();
		Map<Integer, List<Integer>> grupos = new HashMap<>();
		Map<Integer, List<Integer>> r = agrupaPorResto4(file, n, fileIt,lineaIt, grupos);
		return r;
	}
	
	public static Map<Integer, List<Integer>> agrupaPorResto4(String file, Integer n, Iterator<String> fileIt,
			Iterator<String> lineaIt, Map<Integer, List<Integer>> grupos) {
		Map<Integer, List<Integer>> r = grupos;
		if (fileIt.hasNext() || lineaIt.hasNext()) {
			if (lineaIt.hasNext()) {
				String e = lineaIt.next();
				Integer en = Integer.parseInt(e);
				Integer key = en % n;
				List<Integer> ls;
				if (grupos.containsKey(key)) {
					ls = grupos.get(key);
				} else {
					ls = new ArrayList<>();
					grupos.put(key, ls);
				}
				ls.add(en);
			} else {
				String linea = fileIt.next();
				lineaIt = Iterables.split(linea, "[ ,]").iterator();
			}
			r = agrupaPorResto4(file, n, fileIt, lineaIt, grupos);
		}
		return r;
	}

	public static <E> Integer index0(List<E> ls, E elem) {
		Optional<Integer> r = IntStream.range(0,ls.size()).boxed()
				.filter(i->ls.get(i).equals(elem))
				.findFirst();
		return r.isPresent()?r.get():-1;
	}

	public static <E> Integer index1(List<E> ls, E elem) {
		Integer i = 0;
		Integer n = ls.size();
		Integer b = -1;
		while(i < n && b == -1){
		   if(elem.equals(ls.get(i))) b = i;
		   i = i+1;
		}
		return b;
	}
	
	public static <E> Integer index3(List<E> ls, E elem) {
		return index3(0,ls,elem);
	}
	private static <E> Integer index3(Integer i, List<E> ls, E elem) {
		Integer b = -1;
		Integer n = ls.size();
		if(n-i> 0){
			if (ls.get(i).equals(elem)) b = i;
		    else b = index3(i+1,ls,elem);
		}
		return b;
	}

	public static <E> Integer index4(List<E> ls, E elem, Comparator<E> cmp) {
		return index4(0,ls.size(),ls,elem,cmp);
	}
	private static <E> Integer index4(Integer i, Integer j, List<E> ls,E elem, Comparator<E> cmp) {
		Integer r = -1;
		if(j-i==1) {
			r = cmp.compare(elem,ls.get(i)) == 0 ? i:-1;
		} else if(j-i>1){
		   Integer k = (j+i)/2;
		   if (cmp.compare(elem,ls.get(k)) == 0) r = k;
		   else if (cmp.compare(elem,ls.get(k)) < 0) r = index4(i,k,ls,elem,cmp);
		   else r = index4(k+1,j,ls,elem,cmp);
		}
		return r;
	}
	

	public static <E> Integer index5(List<E> ls,E elem, Comparator<E> cmp) {
		Integer b = -1;
		Integer i = 0;
		Integer j = ls.size();	
		while(j-i>1 && b==-1){
		   Integer k = (j+i)/2;
		   if (Comparator2.isEQ(ls.get(k),elem,cmp)) b = k;
		   else if (Comparator2.isLT(ls.get(k),elem,cmp)) i = k;
		   else j = k;		   
		}
		if(j-i==1) b = cmp.compare(elem,ls.get(i)) == 0 ? i:-1;
		return b;
	}

	
	public static record  P2(Integer i, Integer j) {
		public static P2 of(Integer i, Integer j) {
			return new P2(i,j);
		}
	}

	public static record P3(Integer i, Integer j) {
		public static P3 of(Integer i, Integer j) {
			return new P3(i,j);
		}
		public Integer k() {
			return (i+j)/2;
		}
	}

	public static <T> Integer index6(List<T> ls, T elem, Comparator<T> cmp) {
		P3 p = P3.of(0, ls.size());
		while ((p.j - p.i) > 0 && !Comparator2.isEQ(elem, ls.get(p.k()), cmp)) {
			if (Comparator2.isLT(elem, ls.get(p.k()), cmp))
				p = P3.of(p.i(), p.k());
			else
				p = P3.of(p.k() + 1, p.j);
		}
		return (p.j() - p.i() > 0 && Comparator2.isEQ(elem, ls.get(p.k()), cmp)) ? p.k() : -1;
	}
	
	public static <E> Integer index2(Iterable<E> it, E e) {
		Stream<Enumerate<E>> s = Stream2.enumerate(Stream2.of(it));
		Optional<Enumerate<E>> entry = s.filter(p->p.value().equals(e)).findFirst();
		return entry.isPresent()?entry.get().counter():-1;
	}
	
	public static <T> Integer index8(Iterable<T> it, T e) {
		Integer i = 0;
		Integer b = -1;
		Iterator<T> itt = it.iterator();
		while(itt.hasNext() && b == -1){
		   T a = itt.next();
		   if(a.equals(e)) b = i;
		   i = i + 1;
		}
		return b;
	}
	
	public static <E> Integer index9(Iterable<E> it, E elem, Comparator<E> cmp) {
		Integer i = 0;
		Integer b = -1;
		Iterator<E> itt = it.iterator();
		E a = null;
		while(itt.hasNext() && b == -1 && ((a==null) || cmp.compare(a,elem)<=0)){
		   a = itt.next();
		   if(a.equals(elem)) b = i;
		   i = i +1;
		}
		return b;
	}
	
	public static Integer cercano(List<Integer> ls, Integer e){
		if(ls.isEmpty()) Preconditions.checkArgument(!ls.isEmpty());
		int n = ls.size();
		return cercano(ls, e, 0, n, n/2);
	}
	private static Integer cercano(List<Integer> ls, Integer e, Integer i,Integer j,Integer k) {	
		Integer r;
	 	if(j-i == 1) {
	 		r = ls.get(i);
	 	} else if(j-i == 2){
	 		r = masCercano(e,ls.get(i),ls.get(i+1));
		} else if(e == ls.get(k)) {
	 		r = ls.get(k);
	 	} else if(e < ls.get(k)){
	 		r = cercano(ls,e,i,k+1,(i+k+1)/2);
	 	} else {
			r = cercano(ls,e,k,j,(k+j)/2);
	 	}
		return r;
	}
	public static Integer masCercano(Integer e, Integer e1, Integer e2){
	 	Integer r;
		if(Math.abs(e-e1) <= Math.abs(e-e2)) r = e1;
	 	else r = e2;
	 	return r;
	}
	
	public static Integer cercano2(List<Integer> ls, Integer e) {
		int n = ls.size();
		Integer r = null;
		if(n<1) Preconditions.checkArgument(!ls.isEmpty());
		else if (n == 1) r = ls.get(0);
		else if (n == 2) r = masCercano(e, ls.get(0), ls.get(1));
		else if (n > 2) {
			View2E<List<Integer>, Integer> w = List2.view2eOverlap(ls);
			if (e == w.e()) r = w.e();
			else if (e < w.e()) r = cercano2(w.left(), e);
			else r = cercano2(w.right(), e);
		}
		return r;
	}
	
	public static boolean esPalindromo1(String st) {
		UnaryOperator<IntPair> next = p -> IntPair.of(p.first()+1, p.second()-1);
		Stream<IntPair> s = Stream.iterate(IntPair.of(0,st.length()),p->(p.second()-p.first())> 1, next);
		return s.allMatch(p->st.charAt(p.first()) == st.charAt(p.second()-1));
	}
	
	public static boolean esPalindromo2(String p) {
		int i = 0;
		int j = p.length();
		Boolean b = true;
		while(j - i > 1 && b) {	
			i = i + 1;
			j = j - 1;
			b = p.charAt(i) == p.charAt(j-1);
		}
		return b;
	}
	
	public static boolean esPalindromo3(String p) {
		return esPalindromo3(p,0,p.length(),true);
	}
	private static boolean esPalindromo3(String p, int i, int j, Boolean b) {
		if (j - i > 1 & b) {
			b = esPalindromo3(p, i + 1, j - 1, p.charAt(i) == p.charAt(j-1));
		}
		return b;
	}
	
	public static boolean esPalindromo4(String p) {
		return esPalindromo4(0,p.length(),p);
	}
	private static boolean esPalindromo4(int i, int j, String p) {
		boolean b = true;
		if (j - i > 1) {
			b = p.charAt(i) == p.charAt(j-1)  && esPalindromo4(i + 1, j - 1, p);
		}
		return b;
	}
	
	public static boolean esPalindromo5(String p) {
		return esPalindromo5(0,p.length(),p);
	}
	private static boolean esPalindromo5(int i, int j, String p) {
		boolean b = true;
		if (j - i > 1) {
			if(b = p.charAt(i) != p.charAt(j-1)) b = false;
			else b = esPalindromo5(i + 1, j - 1, p);
		}
		return b;
	}

	public static Boolean esAritmetica1(List<Integer> ls) {
		Integer n = ls.size();
		if(n<=2) return true;
		Integer r = ls.get(1)-ls.get(0);
		return IntStream.range(0,n-1)
	 		.allMatch(i->(ls.get(i+1)-ls.get(i)) == r);
	}

	public static Boolean esAritmetica2(List<Integer> ls) {
		Integer n = ls.size();
		if(n <=2) return true;
		Integer i = 0;
		Boolean b = true;
		Integer r = ls.get(1)-ls.get(0);
		while(n-i>1  && b){
		   Integer e1 = ls.get(i), e2 = ls.get(i+1);
		   i++;
		   b = e2-e1 == r;
		}
		return b;
	}
	
	public static Boolean esAritmetica3(List<Integer> ls) {
		Integer n = ls.size();
		if(n <=2) return true;
		Integer r = ls.get(1)-ls.get(0);
		return esAritmetica3(0,ls,r,n);
	}
	private static Boolean esAritmetica3(Integer i, List<Integer> ls, Integer r, Integer n) {
		Boolean b = true;
		if(n-i>1) {
			b = ls.get(i+1)-ls.get(i) == r && esAritmetica3(i+1,ls,r,n);
		}
		return b;
	}
	
	public static Boolean esAritmetica4(Iterable<Integer> iterable) {
			Iterator<Integer> it = iterable.iterator();
			Integer last,e;
			if(it.hasNext()) e = it.next(); else return true;
			Boolean b = true;
			Integer r =null;
			while(it.hasNext()  && b){
				last = e;
				e = it.next();
				if(r == null) r = e-last;
				b = e-last == r;	
			}
			return b;
	}
	
	public static Boolean esAritmetica5(Iterable<Integer> iterable) {
		Iterator<Integer> it = iterable.iterator();
		Integer a1, a2;
		if(it.hasNext()) 	a1 = it.next(); else return true;
		if(it.hasNext()) 	a2 = it.next(); else return true;
		Integer r = a2-a1;
	    return Stream2.consecutivePairs(Stream2.of(()->it))
	    	.allMatch(p->p.second()-p.first()==r);		
	}
	
	public static Boolean esAritmetica6(Stream<Integer> st) {	
		Stream<Integer> stc = Stream2.consecutivePairs(st).map(p->p.second()-p.first());
	    return Stream2.allEquals(stc);		
	}

	
	// Coeficientes de menor a mayor
	
	public static Double polF(List<Double> c, Double v){
		return Stream2.zip(c.stream(),Stream.iterate(1.,e->e*v),(e1,e2)->e1*e2)
				.mapToDouble(x->x).sum();
	}
	
	// Coeficientes de menor a mayor
	
	public static Double polP(List<Double> c, Double v) {
		Double b = 0.;
		Integer i = 0;
		Double p = 1.;
		Integer n = c.size();
		while (i < n) {
			Double cf = p * c.get(i);
			i = i + 1;
			p = p * v;
			b = b + cf;
		}
		return b;
	}
	
	// Coeficientes de menor a mayor
	
	public static Double prodI(List<Double> c, Double v){
		Double b = 1.;
		Integer i = 0;
		Double p = 1.;
		Integer n = c.size();
		while(i<n) {
			Double cf = p*c.get(i);
			i = i+1;
			p = p*v;
			b = b * cf;
		} 
		return b;	
	}

	// Coeficientes de mayor a menor
	
	public static Double polHI(List<Double> d, Double v){
		Double b = 0.;
		Integer i = 0;
		Integer r = d.size();
		while(i<r) {
			Double dd = d.get(i);
		    i = i +1;
		    b = b*v + dd;
		} 
		return b;	
	}

	// Coeficientes de menor a mayor
	
	public static Double polHD(List<Double> c, Double v){
		return polHD(c,v,0,c.size());
	}
	public static Double polHD(List<Double> c,Double v,Integer i,Integer r){
		Double b = 0.;
		if(i<r) {
			b = polHD(c,v,i+1,r);
			Double d = c.get(i);
			b = b*v + d;
		} 
		return b;	
	}
	
	public static Integer ml1(Integer x, Integer y){
		Integer r = 0;
		if(y > 0) {
			r = 2*ml1(x,y/2)+(y % 2 == 0? 0: x);
		} 
		return r;
	}
	
	public static Integer ml2(Integer x, Integer y){
		Integer b = 0;
		Integer p = 1;
		while(y>0) {
			Integer cf = y % 2 == 0? 0: x;
			p = p*2;
			b = b + cf;
		} 
		return b;	
	}
	
	/**
	 * @param x Numero real entre 0 y 1
	 * @param eps Error
	 * @return e^x-1
	 */
	public static Double exponential(Double x, Double eps){
		Double r = 0.;
		Integer i = 0;
		Double t = 1.;
		while(t>eps) {	
			i = i+1;
			t = t*x/i;
			r = r + t;
		}
		return r;
	}

	public static List<Integer> digitos(Integer n, Integer b){
		List<Integer> r;
		if(n>b) {
			r = digitos(n/b,b);
			Integer d = n%b;
		    r.add(d);
		} else {
			r = new ArrayList<>();
			r.add(n);		}
		return r;	
	}
	
	public static List<Integer> digitosI(Integer n, Integer b){
		List<Integer> r = new ArrayList<>(); 
		while(n>b) {
			Integer d = n%b;
		    r.add(0,d);
			n = n/b;
		}
		r.add(0,n);
		return r;	
	}

	public static Integer numeroDeCeros(Integer n, Integer a){
		Integer b = 0;
		while(n>0) {
		     Integer d = n%a;
		     if(d == 0){
		    	 b = b+1;
		     }
		     n = n/a;
		} 
		return b;	
	}
	
	
	public static Long entero(List<Integer> digitos,Integer a){
		Long b = 0L;
		Integer i = 0;
		Integer n = digitos.size();
		while(n-i > 0){
			Integer d = digitos.get(i);
			b = b * a + d;
			i = i+1;
		} 
		return b;
	}

	public static Integer inverso(Integer n, Integer a){
		Integer b = 0;
		while(n > 0){
			int d =n%a;
			b = b * a + d;
			n = n/a;
		}
		return b;
	}
	
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
	
	/**
	 * 
	 * @param base Base
	 * @param n Exponente
	 * @return Valor de base^n de forma iterativa
	 */
	public static Long pot(Long base, Integer n){
		Long r = base;
		Long u = 1L;
		while( n > 0){
	       if(n%2==1){
			     u = u * r;
		   }
		   r = r * r;
		   n = n/2;
		}
		return u;
	}
	
	
	public static List<Pair<Long,Long>> primosPar(Long m, Long n, Integer k) {
		Stream<Long> r = Stream.iterate(Math2.siguientePrimo(m - 1), x -> x < n, x -> Math2.siguientePrimo(x));
		List<Pair<Long,Long>> r2 = 
				Stream2.consecutivePairs(r)
				.filter(t -> t.second() - t.first() == k)
				.toList();
		return r2;
	}


	public static List<Pair<Long,Long>> primosPar2(Long m, Long n, Integer k) {
		List<Pair<Long,Long>> r = new ArrayList<>();
		Long e1 = null;
		Long e2 = Math2.siguientePrimo(m - 1);		
		while(e2<n) {
			Pair<Long,Long> p = Pair.of(e1, e2);
			e1 = e2;
			e2 = Math2.siguientePrimo(e2);
			if(p.first() != null && p.second() - p.first() == k) {
				r.add(p);
			}			
		}
		return r;
	}
	
//	Dado una lista ordenada y rotada k veces, diseñar un algoritmo Θ(log n) 
//	que encuentre el elemento mayor de la lista, suponiendo que no conocemos el valor de k. 
	
	public static Integer mayor_r(List<Integer> ls) {
		Preconditions.checkArgument(!ls.isEmpty());
		return mayor_r(ls,0,ls.size());
	}
	
	private static Integer mayor_r(List<Integer> ls, int i, int j) {
		Integer r;
		if(j-i == 1)
			r = ls.get(i);
		else {
			int k =(j+i)/2;
			if(ls.get(i) >= ls.get(k))
				r = mayor_r(ls, i, k);
			else
				r = mayor_r(ls, k, j);
		}
		return r;
	}
	
	public static Integer mayor_i(List<Integer> ls) {
		Preconditions.checkArgument(!ls.isEmpty());
		int i=0;
		int j=ls.size();
		while(j-i > 1) {
			int k =(j+i)/2;
			if(ls.get(i) >= ls.get(k)) j = k;
			else i = k;
		}
		return ls.get(i);
	}
	
	public static record MM(Integer i, Integer j, List<Integer> ls) {
		public static MM of(Integer i, Integer j, List<Integer> ls) { return new MM(i,j,ls);}
		public MM next() {
			int i = this.i();
			int j = this.j();
			List<Integer> ls = this.ls();
			int k =(j+i)/2;
			if(ls.get(i) >= ls.get(k)) j = k;
			else i = k;
			return MM.of(i, j, ls);
		}
		public Boolean guard() {
			int i = this.i();
			int j = this.j();
			return j-i == 1;
		}
		
		Integer value() {
			return this.ls().get(this.i());
		}
	}
	
	public static Integer mayor_f(List<Integer> ls) {
		Preconditions.checkArgument(!ls.isEmpty());
		MM v = Stream.iterate(MM.of(0,ls.size(),ls),m->m.next()).filter(m->m.guard()).findFirst().get();
		return v.value();
	}

	public static void test1() throws IOException {
		Integer n = 2345789;
		Integer a = 10;
		List<Integer> ls = digitos(n,a);
		System.out.println("Digitos = "+ls);
		ls = digitosI(n,a);
		System.out.println("Digitos I = "+ls);
		System.out.println("Ceros = "+numeroDeCeros(n,a));
		System.out.println("Entero = "+n+","+entero(ls,a));
		System.out.println("Entero = "+n+","+inverso(n,a));
	}
	
	public static void test2() {
		System.out.println(primosPar(30L,100L,2));
		System.out.println(primosPar2(30L,100L,2));
	}
	
	public static void test3() {
//		List<Integer> ls = List.of(4,6,8,11,12);
		List<Integer> ls = List.of(4,6);
		System.out.println(esAritmetica1(ls));
		System.out.println(esAritmetica2(ls));
		System.out.println(esAritmetica3(ls));
		System.out.println(esAritmetica4(ls));
		System.out.println(esAritmetica5(ls));
		System.out.println(esAritmetica6(ls.stream()));
	}
	
	public static void test4() {
		String text = "reordenanedroer";
		String text2 = "reordenanefroer";
		System.out.println(String.format("4: %b, %b, %b, %b, %b",
				esPalindromo1(text),esPalindromo2(text),esPalindromo3(text),esPalindromo4(text),esPalindromo3(text),esPalindromo5(text)));
		System.out.println(String.format("5: %b, %b, %b, %b, %b",
				esPalindromo1(text2),esPalindromo2(text2),esPalindromo3(text2),esPalindromo4(text2),esPalindromo5(text2)));
	}
	
	public static void test5() throws IOException {
		System.out.println(String.format("3.0 %d",sumaPrimos("ficheros/numeros2.txt")));
		System.out.println(String.format("3.1 %d",sumaPrimos1("ficheros/numeros2.txt")));
		System.out.println(String.format("3.2 %d",sumaPrimos2("ficheros/numeros2.txt")));
		System.out.println(String.format("3.3 %d",sumaPrimos3("ficheros/numeros2.txt")));
		var r1 = agrupaPorResto1("ficheros/numeros2.txt",5);
		var r2 = agrupaPorResto2("ficheros/numeros2.txt",5);
		var r3 = agrupaPorResto3("ficheros/numeros2.txt",5);
		var r4 = agrupaPorResto4("ficheros/numeros2.txt",5);
		System.out.println("4 "+r1);
		System.out.println("5 "+r2);
		System.out.println("6 "+r3);
		System.out.println("7 "+r4);
		
	}
	
	public static void test6() throws IOException {
		List<Integer> ls1 = List2.of(-1,3,5,5,7,9,13,15,15,17,19);
		System.out.println(index1(ls1,11));
		System.out.println(index4(ls1,11,Comparator.naturalOrder()));
		System.out.println(index5(ls1,11,Comparator.naturalOrder()));
		System.out.println(index9(ls1,11,Comparator.naturalOrder()));
		Integer e = 13;
		Iterator<Integer> it1 = Files.lines(Path.of("ficheros/numeros.txt")).map(x->Integer.parseInt(x)).iterator();
		Iterator<Integer> it2 = Files.lines(Path.of("ficheros/numeros.txt")).map(x->Integer.parseInt(x)).iterator();
		Iterator<Integer> it3 = Files.lines(Path.of("ficheros/numeros.txt")).map(x->Integer.parseInt(x)).iterator();
		System.out.println(String.format("1: %d, %d, %d",index2(()->it1,e), index8(()->it2,e), index9(()->it3,e,Comparator.naturalOrder())));
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
	
	public static void test9() {
		Locale.setDefault(Locale.of("en", "us"));
		List<Double> c = List2.of(0.,0.,0.,0.,1.);		
		Double v1 = polHD(c,2.);
		Double v2 = polF(c,2.);
		Double v3 = polP(c,2.);
		List<Double> d = List2.reverse(c);
		Double v4 = polHI(d,2.);	
		
		System.out.printf("2: = %.2f,%.2f,%.2f,%.2f\n",v1,v2,v3,v4);
		List<Double> c2 = List2.of(1.,1.,1.);		
		Double v5 = prodI(c2,2.);
		System.out.printf("3: = %.2f\n",v5);
	}
	
	public static void test10() {
		List<Integer> l1 = List2.of(1,3,5,7,9,12);
		List<Integer> l2 = List2.of(0,2,4,10,19,21,23,45);
		System.out.println(String.format("5: %s, %s",cercano(l1,11),cercano2(l1,11)));
		System.out.println(String.format("6: %s, %s",cercano(l2,11),cercano2(l2,11)));
	}
	
	public static void test11() {
		Integer x = 1234;
		Integer y = 456;
		System.out.printf("%d,%d\n",x*y,ml1(x,y));
		System.out.printf("%d,%d\n",x*y,ml2(x,y));
	}
	
	public static void test12() {
		Locale.setDefault(Locale.of("en", "us"));
		System.out.printf("%.4f,%.4f\n",Math.exp(0.567),1+IterativosyRecursivosSimples.exponential(0.567,0.0001));
	}
	
	public static void test13() {
		Cr s = cr(100,37);
		String2.toConsole("%s,%s",s,37*s.c()+s.r());
	}
	
	public static String ts(List<String> ls) {
		return ls.stream().collect(Collectors.reducing("",s->s.substring(0, 1),(x,y)->x+y));
	}
	
	public static void test14() {
		String2.toConsole(ts(List.of("Juan","Pedro","Antonio")));
	}
	
	public static Optional<Integer> mayorEntre(Integer m) {
		Integer b = null;
		Integer np = 2;
		while(np<m){
			b = np;	
		   	np = Math2.siguientePrimo(np);			      	   
		}
		return Optional.ofNullable(b);
	}

	
	public static void test15() {
		Optional<Integer> p1 = Stream2.findLast(Stream.iterate(2,e->e<32,e->Math2.siguientePrimo(e)));
		Optional<Integer> p2 = mayorEntre(32);
		String2.toConsole("%d,%d",p1.get(),p2.get());
	}
	
	public static <E,K> Map<K,Integer> groupsSize(Stream<E> st, Function<E,K> key) {
	 	return st.collect(
		   Collectors.groupingBy(key,
		      Collectors.collectingAndThen(
		          Collectors.counting(),Long::intValue)));
	}
	
	

	record Ciudad(String nombre, String provincia) {
		public static Ciudad of(String nombre, String provincia) {
			return new Ciudad(nombre, provincia);
		}
	}
	
	public static void test16() {
		List<Ciudad> ls = List.of(Ciudad.of("A1","PA"),Ciudad.of("A2","PB"),Ciudad.of("A3","PC"),Ciudad.of("A4","PA"),
				Ciudad.of("A","PC"));
		Map<String,Integer> m =groupsSize(ls.stream(),c->c.provincia());
		String2.toConsole("%s",m);
	}

	public static <E,K,R> Map<K,R> groupingReduce(
			Stream<E> st, 
			Function<E,K> key, 
			Function<E,R> f,
			BinaryOperator<R> op){
	 	return st.collect(Collectors.groupingBy(key,
	             Collectors.mapping(f,
	            		 Collectors.collectingAndThen(
	            				 Collectors.reducing(op),g->g.get()))));
	}
	
	public static <E,K,R> Map<K,R> groupingReduce2(Stream<E> st, Function<E,K> key,
	 		Function<E,R> f,BinaryOperator<R> op){
		Iterator<E> it = st.iterator();
		Map<K,R> b = new HashMap<>();
		while(it.hasNext()){	   
		   E e = it.next();
		   K k = key.apply(e);
		   R r = f.apply(e);
		   if(b.containsKey(k))
			b.put(k,op.apply(b.get(k),r));
	       else 
		 	b.put(k,r);
		}
		return b;
	}


	public static record Persona(String nombre, Integer edad) {
		public static Persona of(String nombre, Integer edad) {
			return new Persona(nombre,edad);
		}
	}
	
	public static void test17() {
		List<Persona> ls = List.of(Persona.of("A1",32),Persona.of("A2",64),Persona.of("A3",31),Persona.of("A1",45),
				Persona.of("A2",33));
		Map<String,Integer> m1 = groupingReduce(ls.stream(),p->p.nombre(),p->p.edad(),
				BinaryOperator.maxBy(Comparator.naturalOrder()));
		Map<String,Integer> m2 = groupingReduce2(ls.stream(),p->p.nombre(),p->p.edad(),
				BinaryOperator.maxBy(Comparator.naturalOrder()));
		String2.toConsole("%s",m1);
		String2.toConsole("%s",m2);
	}
	
	public static List<Long> primos(Long n) {
		return Stream.iterate(2L,e->e<n,e->Math2.siguientePrimo(e))
	 	.toList();
	}
	
	public static List<Long> primos2(Long n) {
		Long e = 2L;
		List<Long> r = new ArrayList<>();
		while(e<n) {
			r.add(e);
			e = Math2.siguientePrimo(e);
		}
		return r;
	}
	
	public static void test18() {
		List<Integer> ls = List.of(7,8,9,10,10,11,11,3,2);
		Integer r = mayor_r(ls);
		System.out.println(r);
		r = mayor_i(ls);
		System.out.println(r);
		r = mayor_f(ls);
		System.out.println(r);
	}
	
	public static void test19() {
		
	}


	public static void main(String[] args) throws IOException {
//		String2.toConsole("%s,%s",primos(100L),primos2(100L));
		test1();
	}

}
