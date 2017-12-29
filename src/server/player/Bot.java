package server.player;

import client.logic.MoveType;
import javafx.scene.paint.Color;
import server.board.Coordinates;
import server.lobby.Lobby;

import java.util.ArrayList;

public class Bot implements PlayerTemplate{
    public int pid;
    public String name;
    public Lobby lobby;
    public Color color;
    public int corner;

    private Difficult difficultLevel;
    private MoveType moveType;
    private MoveType prevMoveType;
    private boolean correctMove;
    private ArrayList<Coordinates> currentCoordinates;
    private ArrayList<Coordinates> destinationCoordinates;
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
        currentCoordinates = new ArrayList<>();
        destinationCoordinates = new ArrayList<>();
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

        currentCoordinates = moveLogic.fillPawnList(rows, corner);
        destinationCoordinates = moveLogic.fillPawnList(rows, enemyCorner);
    }

    @Override
    public void joinToLobby(Lobby lobby, int corner) {
        name = lobby.name + corner;
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

    @Override
    public void setColor(Color color) {

    }

    @Override
    public String getPlayersNames() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Bot doesn't have pid, so return -1
     * @return -1
     */
    @Override
    public int getPid() {
        return -1;
    }

    @Override
    public void addCurrentCoordinates(int x, int y) {
        currentCoordinates.add(new Coordinates(x, y));
    }

    @Override
    public void addDestinationCoordinates(int x, int y) {
        destinationCoordinates.add(new Coordinates(x, y));
    }
}
