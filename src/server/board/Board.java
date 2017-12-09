package server.board;

public class Board implements IBoard {
    private int n = 0;
    private int m = 0;
    public Field[][] fieldList = null;

    public Board(int n, int m) {
        this.n = n;
        this.m = m;
        fieldList = new Field[n][(2*m)-1];
    }

    @Override
    public void executeMove(Coordinates currentCoordinates, Coordinates newCoordinates) {

    }

    @Override
    public Field getField(Coordinates coordinates) {
        return null;
    }
}
