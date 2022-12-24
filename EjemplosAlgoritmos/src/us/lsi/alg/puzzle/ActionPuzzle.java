package us.lsi.alg.puzzle;

import java.util.List;

import us.lsi.common.IntPair;
import us.lsi.graphs.virtual.Action;

public record ActionPuzzle(String name,IntPair direction) implements Action<VertexPuzzle>{
	
	public static ActionPuzzle of(String name) {
		Integer index = switch(name) {
		case "Up" -> 0; 
		case "Down" -> 1;
		case "Left" -> 2; 
		case "Right" -> 3;
		default -> throw new IllegalArgumentException("Unexpected value: " + name); 
		};		
		return actions.get(index);
	}
	
	public static ActionPuzzle of(String name, IntPair direction) {
		return new ActionPuzzle(name, direction);
	}
	
	public static final List<ActionPuzzle> actions = 
			List.of(ActionPuzzle.of("Up",IntPair.of(-1,0)), ActionPuzzle.of("Down",IntPair.of(1,0)),
					ActionPuzzle.of("Left",IntPair.of(0,-1)),ActionPuzzle.of("Right",IntPair.of(0,1)));
	
	@Override
	public VertexPuzzle neighbor(VertexPuzzle v) {
		return v.neighbor(this);
	}

	@Override
	public boolean isApplicable(VertexPuzzle v) {
		return v.validPosition(v.blackPosition().add(this.direction));
	}

}
