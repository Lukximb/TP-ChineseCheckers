package server.core;

import server.board.Board;
import server.board.Coordinates;
import server.board.Field;
import server.connection.Connection;
import server.lobby.IRulesManager;
import server.lobby.LobbyMediator;
import server.manager.Manager;

public interface FactoryMBean {
    Connection createConnection();

    Manager createManager();

    void createLobbyManager();

    void createPlayerManager();

    IRulesManager createRulesManager();

    void createPlayer(int pid, String name);

    void createLobby(int playerNum, int rowNumber, String lobbyName, int adminPid);

    LobbyMediator createLobbyMediator();

    Field createField(int n, int m);

    Board createBoard(int n, int m);

//    Clock createClock();

    void deletePlayer(int pid);

    Coordinates createCoordinates(int n, int m);
}
