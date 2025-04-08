package us.lsi.alg.caballo;

public enum CaballoAction {
    
	UP_LEFT(-2, -1), UP_RIGHT(-2, 1), RIGHT_UP(-1, 2), RIGHT_DOWN(1, 2),
    DOWN_RIGHT(2, 1), DOWN_LEFT(2, -1), LEFT_DOWN(1, -2), LEFT_UP(-1, -2);

    public final int dRow;
    public final int dCol;

    CaballoAction(int dRow, int dCol) {
        this.dRow = dRow;
        this.dCol = dCol;
    }

    public CaballoVertex move(CaballoVertex vertex) {
        return CaballoVertex.of(vertex.row() + dRow, vertex.col() + dCol);
    }
}
