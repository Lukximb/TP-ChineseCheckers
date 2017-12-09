package server.core;

import server.connection.Connection;
import server.lobby.*;
import server.manager.*;
import server.player.*;
import server.board.*;

public interface IFactory {
    Connection createConnection();

    Manager createManager();

    LobbyManager createLobbyManager();

    PlayerManager createPlayerManager();

    RulesManager createRulesManager();

    Player createPlayer();

    Bot createBot();

    Lobby createLobby();

    Field createField();

    Board createBoard();

    Chat createChat();

    Clock createClock();
}
