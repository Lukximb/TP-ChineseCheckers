package server.lobby;

import server.board.Board;
import server.player.Bot;
import server.player.PlayerTemplate;

public interface LobbyMBean {
    /**
     * Starts game.
     */
    void startGame();

    /**
     * Adds player to lobby.
     * @param player - added player
     */
    void addPlayer(PlayerTemplate player);

    void endGame();

    /**
     * Adds bot to lobby.
     * @param bot - added bot
     */
    void addBot(PlayerTemplate bot);

    /**
     * Sends information about executed move.
     * @param message
     */
    void sendMoveNotification(String message);

    /**
     * Removes player from lobby.
     * @param player - player as PlayerTemplate
     */
    void removePlayer(PlayerTemplate player);

    /**
     * Removes player from lobby.
     * @param playerName - player's name as String
     */
    void removePlayer(String playerName);

    /**
     * Removes bot from lobby.
     * @param bot
     */
    void removeBot(Bot bot);

    /**
     * Prepares to start game
     */
    void initPlayersOnBoard();

    /**
     * Puts player's pawns on board.
     * @param player
     * @param corner
     */
    void putPawnsOnBoard(PlayerTemplate player, int corner);

    /**
     * creates destination coordinates for player.
     * @param player - player's object
     * @param corner - player's corner
     */
    void createDestinationCoordinates(PlayerTemplate player, int corner);

    /**
     * Returns lobby's board
     * @return board
     */
    Board getBoard();

   // void getRoundTime();

    /**
     * Starts next players turn.
     */
    void nextRound();

    /**
     * Prints new chat message - not working yet
     * @param player - player who sends message
     * @param message - message content
     */
    void printMessage(PlayerTemplate player, String message);

    /**
     * Sends information about starting new turn.
     */
    void sendTurnNotification();

}
