package server.player;

import client.logic.MoveType;
import server.board.Coordinates;

import java.util.ArrayList;

public class Bot{
    private Difficult difficultLevel;
    private MoveType moveType;
    private MoveType prevMoveType;
    private boolean correctMove;
    private ArrayList<Coordinates> pawns;
    private ArrayList<Coordinates> destinationPawns;
    private int corner;
    private int enemyCorner;
    private int numberOfPlayers;
    private int rows;
    private Coordinates cCoordinates;
    private Coordinates dCoordinates;
    private CommonMoveLogic moveLogic;

    public Bot(Difficult difficultLevel) {
        this.difficultLevel = difficultLevel;
        moveType = MoveType.EMPTY;
        prevMoveType = MoveType.EMPTY;
        correctMove = false;
        pawns = new ArrayList<>();
        destinationPawns = new ArrayList<>();
        moveLogic = new CommonMoveLogic();
    }

    public void move(Coordinates coordinates) {

    }

    private boolean checkMove() {
        return false;
    }

    public Coordinates getCurrentPosition() {
        return null;
    }

    public void pass() {

    }

    public void surrender() {

    }

    public void setCorner(int corner) {
        this.corner = corner;
        enemyCorner = (corner + 3) % 6;
    }

    public void start(int rows, int numberOfPlayers) {
        this.rows = rows;
        this.numberOfPlayers  = numberOfPlayers;

        pawns = moveLogic.fillPawnList(rows, corner);
        destinationPawns = moveLogic.fillPawnList(rows, enemyCorner);
    }
}
