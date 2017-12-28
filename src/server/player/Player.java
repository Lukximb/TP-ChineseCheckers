package server.player;

import client.logic.MoveType;
import javafx.scene.paint.Color;
import server.board.Coordinates;
import server.lobby.Lobby;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;

public class Player extends NotificationBroadcasterSupport implements PlayerMBean, Serializable{
    public int pid;
    public String name;
    public Lobby lobby;
    public Color color;
    public int corner;

    public Player(int pid, String name){
        this.pid = pid;
        this.name = name;
        System.out.println(">> Create player with pid= " + pid);
    }

    @Override
    public void checkMove(Coordinates currentCoordinates, Coordinates destinationCoordinates, MoveType moveType) {
        if (lobby.mediator.checkMove(currentCoordinates, destinationCoordinates, pid, moveType) == true) {
            sendNotification(new Notification(String.valueOf(pid), this, 110011110, "R CorrectMove"));
        } else {
            sendNotification(new Notification(String.valueOf(pid), this, 110011110, "R IncorrectMove"));
        }
    }

    @Override
    public void move(Coordinates currentCoordinates, Coordinates destinationCoordinates) {
        if (lobby.mediator.move(this, currentCoordinates, destinationCoordinates)) {
            lobby.sendMoveNotification("E," + corner + ","
                    + currentCoordinates.getX() + "," + currentCoordinates.getY() + "," + destinationCoordinates.getX() + "," + destinationCoordinates.getY());

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
    public void createLobby(String lobbyName) {

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

    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public String getPlayersNames() {
        String playersList = "";
        for(Player p: lobby.players) {
            if (p == null) {
                continue;
            }
            playersList = playersList.concat(","+p.name);
        }
        return playersList;
    }

}
