package server.player;

import server.board.Coordinates;

public interface IPlayer {
    void move(Coordinates coordinates);

    Coordinates getCurrentPosition();

    void joinToLobby(String lobbyName);

    void exitFromLobby();

    void createLobby(String lobbyName);

    void addBot(Dificult dificultLevel);

    void pass();

    void surrender();

    int getPid();

    void sendMessage(String message);
}
