package us.lsi.p2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.IntegerSet;
import us.lsi.common.Pair;
import us.lsi.common.Ranges.IntRange;

public class Ejercicio2 {
	
	public static IntegerSet bbRango(List<Integer> ls, IntRange rngAB) {
		return bbRango(ls, rngAB, IntRange.of(0, ls.size()));
	}
	
	public static IntegerSet bbRango(List<Integer> ls, IntRange rngAB, IntRange rngIJ) {
		if(rngIJ.a()>=rngIJ.b()) {
			return IntegerSet.empty();
		} else {
			int p = (rngIJ.a()+rngIJ.b())/2;
			Integer v = ls.get(p);
			if(rngAB.contains(v)) {
				return rangoIT(ls, rngAB, rngIJ, p);
			} else if(v<rngAB.a()) {
				return bbRango(ls, rngAB, IntRange.of(rngIJ.a(), p));
			} else {
				return bbRango(ls, rngAB, IntRange.of(p+1, rngIJ.b()));
			}
		}
	}

	private static IntegerSet rangoIT(List<Integer> ls, IntRange rngAB, IntRange rngIJ, int p) {
		IntegerSet res = IntegerSet.of(ls.get(p));
		
		int i=p-1, j=p+1;
		boolean ok = true;
		
		while((i>=rngIJ.a() || j<rngIJ.b()) && ok) {
			ok = false;
			if(i>=rngIJ.a() && rngAB.contains(ls.get(i))) {
				res.add(ls.get(i--));
				ok = true;
			}
			if(j<rngIJ.b() && rngAB.contains(ls.get(j))) {
				res.add(ls.get(j++));
				ok = true;
			}
		}
		return res;
	}

	// ==================================== TESTS ====================================
	public static void main(String[] args) {
		Files2.streamFromFile("ficheros/Ejercicio2DatosEntrada.txt")
		.map(s->Pair.of(creaLista(s.split("#")[0]), creaRango(s.split("#")[1])))
		.forEach(p->System.out.println(bbRango(p.first(), p.second())));
	}

	private static List<Integer> creaLista(String s) {
		return Arrays.stream(s.split(","))
		.filter(e->e!=null && e.length()>0)
		.map(e->Integer.parseInt(e.trim())).collect(Collectors.toList());
	}
	
	private static IntRange creaRango(String s) {
		String[] tk = s.split(",");
		return IntRange.of(Integer.parseInt(tk[0].trim()), Integer.parseInt(tk[1].trim()));
	}
	
}
