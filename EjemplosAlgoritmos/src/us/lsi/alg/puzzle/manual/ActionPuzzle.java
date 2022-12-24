package us.lsi.alg.puzzle.manual;

import java.util.List;
import us.lsi.common.IntPair;

public record ActionPuzzle(String name,IntPair direction) {
	
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
	
	public ProblemPuzzle neighbor(ProblemPuzzle v) {
		return v.neighbor(this);
	}

	public boolean isApplicable(ProblemPuzzle v) {
		return v.validPosition(v.blackPosition().add(this.direction));
	}

	public Double weight(ProblemPuzzle v) {
		return 1.;
	}

}
