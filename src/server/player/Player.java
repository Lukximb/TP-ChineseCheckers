package server.player;

import client.logic.MoveType;
import javafx.scene.paint.Color;
import server.board.Coordinates;
import server.lobby.Lobby;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;
import java.util.ArrayList;

public class Player extends NotificationBroadcasterSupport implements PlayerMBean, Serializable{
    public int pid;
    public String name;
    public Lobby lobby;
    public Color color;
    public int corner;
    public ArrayList<Coordinates> destinationCoordinates;
    public ArrayList<Coordinates> currentCoordinates;
    private int enemyCorner;

    public Player(int pid, String name){
        this.pid = pid;
        this.name = name;
        destinationCoordinates = new ArrayList<>();
        currentCoordinates = new ArrayList<>();
        System.out.println(">> Create player with pid= " + pid);
    }

    @Override
    public void checkMove(Coordinates currentCoordinates, Coordinates destinationCoordinates, MoveType moveType) {
        if (lobby.mediator.checkMove(currentCoordinates, destinationCoordinates, moveType)) {
            sendNotification(new Notification(String.valueOf(pid), this, 110011110, "R CorrectMove"));
        } else {
            sendNotification(new Notification(String.valueOf(pid), this, 110011110, "R IncorrectMove"));
        }
    }

    @Override
    public void move(Coordinates currentCoordinates, Coordinates destinationCoordinates) {
        if (lobby.mediator.move(this, currentCoordinates, destinationCoordinates)) {
            lobby.sendMoveNotification("E," + corner + "," + currentCoordinates.getX() + ","
                    + currentCoordinates.getY() + "," + destinationCoordinates.getX() + "," + destinationCoordinates.getY());
            this.currentCoordinates.remove(currentCoordinates);
            this.currentCoordinates.add(destinationCoordinates);
        }
    }

    @Override
    public void nextRound() {
        lobby.nextRound();
    }


    @Override
    public Coordinates getCurrentPosition() {
        return null;
    }

    @Override
    public void joinToLobby(Lobby lobby, int corner) {
        this.lobby = lobby;
        this.corner = corner;
        sendNotification(new Notification(String.valueOf(pid), this, 110011110, "C " + corner));
    }

    @Override
    public void exitFromLobby() {
        lobby.removePlayer(this);
        if(lobby.isEmpty()) {
            //TODO
            //removing lobby if is empty
            //lobbyManager.removeLobby(lobby);
        }
        lobby = null;
    }

    @Override
    public void addBot(Difficult difficultLevel) {
        Bot bot = new Bot(difficultLevel);
        lobby.addBot(bot);
    }

    @Override
    public void pass() {
        lobby.nextRound();
    }

    @Override
    public void surrender() {

    }

    @Override
    public int getPid() {
        return pid;
    }

    @Override
    public void sendMessage(String message) {
        sendNotification(new Notification(String.valueOf(lobby.name), lobby, 100011001, "M#" + name + "#" + message));
    }

    @Override
    public void yourTurn() {
        //DO NOTHING
    }

    @Override
    public String getPlayersNames() {
        String playersList = "";
        for(PlayerTemplate p: lobby.players) {
            if (p == null) {
                continue;
            }
            playersList = playersList.concat("," + p.getName());
        }
        return playersList;
    }

    @Override
    public void addCurrentCoordinates(int x, int y) {
        currentCoordinates.add(new Coordinates(x, y));
    }

    @Override
    public void addDestinationCoordinates(int x, int y) {
        destinationCoordinates.add(new Coordinates(x, y));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isBot() {
        return false;
    }

    @Override
    public void start() {

    }

    //@Override
    //public void sortDestinationCoordinates() {}
}
