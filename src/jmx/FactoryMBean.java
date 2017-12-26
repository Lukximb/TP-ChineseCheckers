package jmx;

import server.board.Board;
import server.board.Coordinates;
import server.board.Field;
import server.connection.Connection;
import server.lobby.Chat;
import server.lobby.Clock;
import server.lobby.IRulesManager;
import server.lobby.LobbyMediator;
import server.manager.Manager;
import server.player.Bot;

public interface FactoryMBean {
    Connection createConnection();

    Manager createManager();

    void createLobbyManager();

    void createPlayerManager();

    IRulesManager createRulesManager();

    void createPlayer(int pid, String name);

    Bot createBot();

    void createLobby(int playerNum, int rowNumber, String lobbyName, int adminPid);

    LobbyMediator createLobbyMediator();

    Field createField(int n, int m);

    Board createBoard(int n, int m);

    Chat createChat();

    Clock createClock();

    void deletePlayer(int pid);

    Coordinates createCoordinates(int n, int m);
}
