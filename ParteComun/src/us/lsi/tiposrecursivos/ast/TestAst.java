package us.lsi.tiposrecursivos.ast;

import java.io.IOException;

public class TestAst {

	public static void main(String[] args) throws IOException {
		Ast p = Ast.parse("ficheros/program.txt");
		
		if(p!=null) {
//			System.out.println(p);
			p.toDot("ficheros/program.gv");
//			System.out.println(p.block().sentences());
			IfThenElse s = (IfThenElse)p.block().sentences().get(0);
			Exp.toDot("ficheros/exp.gv",((Assign)s.trueBlock().sentences().get(0)).exp());
		}
		
	}
}
