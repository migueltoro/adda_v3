package us.lsi.tiposrecursivos.ast;

import java.io.IOException;

import us.lsi.colors.GraphColors;

public class TestAst {

	public static void main(String[] args) throws IOException {
		Ast p = Ast.parse("ficheros/program3.txt");
	
		
		if(p!=null) {
			System.out.println(p);
			GraphColors.toDot(p,"ficheros/program.gv");
			System.out.println(p.block().sentences());
//			Exp s = p.block().sentences().get(0).;			
			GraphColors.toDot(p, "ficheros/program.gv");
//			GraphColors.toDot(s, "ficheros/exp.gv");
		}
		
	}
}
