package server.board;

public interface IBoard {
    int n = 0;
    int m = 0;
    Field[][] fieldList = null;

    void executeMove(Coordinates currentCoordinates, Coordinates newCoordinates);

    Field getField(Coordinates coordinates);
}
