package server.player;

import javafx.scene.paint.Color;
import server.board.Coordinates;
import server.lobby.Lobby;
import java.io.Serializable;

public class Player implements IPlayer, Serializable{
    public int pid;
    public Lobby lobby = null;
    Color color = null;

    public Player(int pid){
        this.pid = pid;
        System.out.println(">> Create player with pid= " + pid);
    }

    @Override
    public void move(Coordinates coordinates) {

    }

    @Override
    public Coordinates getCurrentPosition() {
        return null;
    }

    @Override
    public void joinToLobby(String lobbyName) {

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
