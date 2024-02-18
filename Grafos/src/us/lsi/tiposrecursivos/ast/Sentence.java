package us.lsi.tiposrecursivos.ast;

import java.util.Map;

import org.jgrapht.nio.Attribute;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;

public sealed interface Sentence extends Vertex permits Assign, IfThenElse, While, Block{
	String name();
	
	default Map<String,Attribute> color() {
		return GraphColors.color(Color.blue);
	}

}
