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

    Lobby createLobby(String name);

    Field createField(int n, int m);

    Board createBoard(int n, int m);

    Chat createChat();

    Clock createClock();

    Coordinates createCoordinates(int n, int m);
}
