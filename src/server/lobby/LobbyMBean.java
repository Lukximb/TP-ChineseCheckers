package server.lobby;

import server.player.Player;
import server.board.IBoard;
import server.player.Bot;

public interface LobbyMBean {
    void startGame();

    void addPlayer(Player player);

    void endGame();

    void addBot(Bot bot);

    void sendMoveNotification(String message);

    void removePlayer(Player player);

    void removePlayer(String playerName);

    void removeBot(Bot bot);

    void initPlayersOnBoard();

    void putPawnsOnBoard(Player player, int corner);

    IBoard getBoard();

    void getRoundTime();

    void nextRound();

    void printMessage(Player player, String message);

}
