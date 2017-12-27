package server.player;

import client.logic.MoveType;
import server.board.Coordinates;
import server.lobby.Lobby;

import java.util.ArrayList;

public class Bot implements PlayerTemplate{
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

    private void move() {

    }


    public boolean checkMove() {
        return true;
    }

    public void start(int rows, int numberOfPlayers) {
        this.rows = rows;
        this.numberOfPlayers  = numberOfPlayers;

        pawns = moveLogic.fillPawnList(rows, corner);
        destinationPawns = moveLogic.fillPawnList(rows, enemyCorner);
    }

    @Override
    public void joinToLobby(Lobby lobby, int corner) {
        this.corner = corner;
        enemyCorner = (corner + 3) % 6;
    }

    @Override
    public void exitFromLobby() {

    }

    @Override
    public void pass() {

    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void yourTurn() {
        move();
    }
}
