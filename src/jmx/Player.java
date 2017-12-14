package jmx;

import javafx.scene.paint.Color;
import server.board.Coordinates;
import server.lobby.Lobby;
import server.player.Dificult;

import java.io.Serializable;

public class Player implements PlayerMBean, Serializable{
    public int pid = 0;
    public String name = "";
    public Lobby lobby = null;
    Color color = null;

    public Player(int pid, String name){
        this.pid = pid;
        this.name = name;
        System.out.println(">> Create player with pid= " + pid);
    }

    @Override
    public void move(Coordinates currentCoordinates, Coordinates destinationCoordinates) {
        System.out.println(">> Invoke move from player: " + pid);
    }

    @Override
    public Coordinates getCurrentPosition() {
        return null;
    }

    @Override
    public void joinToLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    @Override
    public void exitFromLobby() {

    }

    @Override
    public void createLobby(String lobbyName) {

    }

    @Override
    public void addBot(Dificult dificultLevel) {

    }

    @Override
    public void pass() {

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

    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
