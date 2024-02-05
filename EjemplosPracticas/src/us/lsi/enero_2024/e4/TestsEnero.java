package us.lsi.enero_2024.e4;


import java.util.List;

import us.lsi.colors.GraphColors;
import us.lsi.common.Files2;
import us.lsi.common.MutableType;
import us.lsi.tiposrecursivos.Tree;

public class TestsEnero {
	
	public static void main(String[] args) {
		testsEjemploExamen();
	}
	
	
	
	public static void testsEjemploExamen() {
		
		MutableType<Integer> n = MutableType.of(0);
		
		String file = "ficheros/enero_2024/familia.txt";

		List<Tree<Datos>> inputs = Files2.streamFromFile(file)
				.map(linea -> Tree.parse(linea,s -> Datos.parse(s)))
				.toList();
		inputs.stream().forEach( x->{
			System.out.println(x);
			System.out.println(Datos.porcentaje(Familia.proporcionPadre(x),x.optionalLabel().get()));
			System.out.println(Familia.enraizada(x));
		    GraphColors.toDot(x,"ficheros_generados/familia"+n.value()+".txt");
		    n.setValue(n.value()+1);
		});
	}
}


