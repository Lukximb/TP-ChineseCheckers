package jmx;

import server.connection.Connection;
import server.lobby.*;
import server.manager.*;
import server.player.*;
import server.board.*;

public interface FactoryMBean {
    Connection createConnection();

    Manager createManager();

    void createLobbyManager();

    void createPlayerManager();

    RulesManager createRulesManager();

    void createPlayer(int pid);

    Bot createBot();

    Lobby createLobby(int playerNum, int rowNumber, String lobbyName, int adminPid);

    Field createField(int n, int m);

    Board createBoard(int n, int m);

    Chat createChat();

    Clock createClock();

    void deletePlayer(int pid);

    Coordinates createCoordinates(int n, int m);
}
