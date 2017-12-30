package server.lobby;

import server.player.Player;
import server.board.IBoard;
import server.player.Bot;
import server.player.PlayerTemplate;

public interface LobbyMBean {
    void startGame();

    void addPlayer(PlayerTemplate player);

    void endGame();

    void addBot(PlayerTemplate bot);

    void sendMoveNotification(String message);

    void removePlayer(PlayerTemplate player);

    void removePlayer(String playerName);

    void removeBot(Bot bot);

    void initPlayersOnBoard();

    void putPawnsOnBoard(PlayerTemplate player, int corner);

    void createDestinationCoordinates(PlayerTemplate player, int corner);

    IBoard getBoard();

    void getRoundTime();

    void nextRound();

    void printMessage(PlayerTemplate player, String message);

    void sendTurnNotification();

}
