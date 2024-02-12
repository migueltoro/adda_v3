package us.lsi.tiposrecursivos.ast;

public sealed interface Sentence extends Vertex permits Assign, IfThenElse, While, Block{
	String name();
}
