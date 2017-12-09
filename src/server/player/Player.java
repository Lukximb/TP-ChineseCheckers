package server.player;

import server.board.Coordinates;
import server.lobby.Lobby;

public class Player implements IPlayer {
    public int pid;
    public Lobby lobby = null;

    public Player(){

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
        return 0;
    }

    @Override
    public void sendMessage(String message) {

    }
}
