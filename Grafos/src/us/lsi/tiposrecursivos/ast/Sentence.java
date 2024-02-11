package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.Map;

public sealed interface Sentence permits Assign, IfThenElse, While, Block{
	String name();
	void toDot(PrintStream file, Map<Object,Integer> map);
}
