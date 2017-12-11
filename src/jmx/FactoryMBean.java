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

    Lobby createLobby();

    Field createField();

    Board createBoard();

    Chat createChat();

    Clock createClock();
}
