package us.lsi.colors;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import us.lsi.common.Files2;
import us.lsi.common.Map2;
import us.lsi.common.Pair;
import us.lsi.graphs.views.TreeToGraph;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;


public class GraphColors {
	
	public enum Color {
		 green, yellow, red, gray, cyan, orange, magenta, blue, black,  blank
	}
	
	public enum ArrowHead {
		none, normal, dot, inv, crow, tee, vee, diamond, box, curve, icurve
	}
	
	public enum Style {
		dotted, bold, filled, solid, invis, arrowhead
	}
	
	public enum Shape {
		box, polygon, ellipse, point, triangle, doublecircle
	}
	
	/**
	 * @param c color
	 * @return Un Map para ser a�adido en un exportToDot.
	 */
	public static Map<String,Attribute> color(Color c) {
		String cl = c == Color.blank? "" : c.toString();
		Map<String,Attribute> m = Map.of("color", DefaultAttribute.createAttribute(cl));
		return m;
	}
	
	public static Map<String,Attribute> color(Integer c) {
		return color(Color.values()[c]);
	}
	
	public static Map<String,Attribute> colorIf(List<Boolean> test, List<Color> colors) {	
		OptionalInt n = IntStream.range(0, test.size()).filter(i->test.get(i).equals(true)).findFirst();
		Color c = Color.black;
		if(n.isPresent()) c = colors.get(n.getAsInt());
		String cl = c.toString();
		Map<String,Attribute> m = Map.of("color", DefaultAttribute.createAttribute(cl));		
		return m;
	}
	
	public static Map<String,Attribute> colorIf(Color yesColor, Color noColor, Boolean test) {		
		Color c;
		if(test) c = yesColor;
		else c = noColor;
		String cl = c.toString();
		Map<String,Attribute> m = Map.of("color", DefaultAttribute.createAttribute(cl));		
		return m;
	}
	
	public static Map<String,Attribute> colorIf(Color yesColor, Boolean test) {		
		Map<String,Attribute> m = new HashMap<>();
		if(test) m = Map.of("color", DefaultAttribute.createAttribute(yesColor.toString()));
		return m;
	}
	
	public static Map<String, Attribute> label(String label) {
		if(label.equals("")) return new HashMap<>();
		return Map.of("label", DefaultAttribute.createAttribute(label));
	}
	
	public static Map<String, Attribute> style(Style style) {
		return Map.of("style", DefaultAttribute.createAttribute(style.name()));
	}
	
	public static Map<String, Attribute> shape(Shape shape) {
		return Map.of("shape", DefaultAttribute.createAttribute(shape.name()));
	}
	
	public static Map<String, Attribute> styleIf(Style style, Boolean test) {
		if(!test) style = Style.solid;
		return Map.of("style", DefaultAttribute.createAttribute(style.name()));
	}
	
	public static Map<String,Attribute> style(Integer value) {		
		return Map.of("style", DefaultAttribute.createAttribute(Style.values()[value].toString()));
	}
	
	public static Map<String, Attribute> shapeIf(Shape shape, Boolean test) {
		if(!test) shape = Shape.ellipse;
		return Map.of("shape", DefaultAttribute.createAttribute(shape.name()));
	}
	
	public static Map<String,Attribute> shape(Integer value) {		
		return Map.of("shape", DefaultAttribute.createAttribute(Shape.values()[value].toString()));
	}
	
	public static Map<String,Attribute> arrowHead(ArrowHead head) {		
		return Map.of("arrowhead", DefaultAttribute.createAttribute(head.name()));
	}
	
	@SafeVarargs
	public static Map<String, Attribute> all(Map<String, Attribute>... properties){
		final Map<String, Attribute> r = new HashMap<>();
		for(Map<String, Attribute> f:properties)
			r.putAll(f);
		return r;
	}
	
	public static <E> void toDot(BinaryTree<E> tree, String file) {	
		SimpleDirectedGraph<Pair<E,Integer>, DefaultEdge> graph = TreeToGraph.toGraph(tree);
		GraphColors.toDot(graph, file,
				v->v.first()==null?"_":v.first().toString(),
				e->"");
	}
	
	public static <E> void toDot(Tree<E> tree, String file) {	
		SimpleDirectedGraph<Pair<E,Integer>, DefaultEdge> graph = TreeToGraph.toGraph(tree);
		GraphColors.toDot(graph, file,
				v->v.first()==null?"_":v.first().toString(),
				e->"");
	}

	public static <V,E> void toDot(Graph<V,E> graph, String file) {		
		DOTExporter<V,E> de = new DOTExporter<V,E>();
		de.setVertexAttributeProvider(v->GraphColors.label(v.toString()));
		Writer f1 = Files2.getWriter(file);
		de.exportGraph(graph, f1);
	}
	
	public static <V,E> void toDot(Graph<V,E> graph, String file, Function<V,String> vertexLabel) {	
		DOTExporter<V,E> de = new DOTExporter<V,E>();	
		de.setVertexAttributeProvider(v->GraphColors.label(vertexLabel.apply(v)));
		Writer f1 = Files2.getWriter(file);
		de.exportGraph(graph, f1);
	}
	
	public static <V,E> void toDot(Graph<V,E> graph, String file, 
			Function<V,String> vertexLabel,
			Function<E,String> edgeLabel) {		
		DOTExporter<V,E> de = new DOTExporter<V,E>();
		de.setVertexAttributeProvider(v->GraphColors.label(vertexLabel.apply(v)));
		de.setEdgeAttributeProvider(e->GraphColors.label(edgeLabel.apply(e)));		
		Writer f1 = Files2.getWriter(file);
		de.exportGraph(graph, f1);
	}
		
	
	public static <V,E> void toDot(Graph<V,E> graph, String file, 
			Function<V,String> vertexLabel,
			Function<E,String> edgeLabel,
			Function<V,Map<String,Attribute>> vertexAttribute,
			Function<E,Map<String,Attribute>> edgeAttribute) {
		
		DOTExporter<V,E> de = new DOTExporter<V,E>();
		
		Function<V,Map<String,Attribute>> m1 = 
			v->Map2.merge(GraphColors.label(vertexLabel.apply(v)),vertexAttribute.apply(v));
		Function<E,Map<String,Attribute>> m2 = 
			e->Map2.merge(GraphColors.label(edgeLabel.apply(e)),edgeAttribute.apply(e));
		
		de.setVertexAttributeProvider(m1);
		de.setEdgeAttributeProvider(m2);
		
		
		Writer f1 = Files2.getWriter(file);
		de.exportGraph(graph, f1);
	}
	
	
}
