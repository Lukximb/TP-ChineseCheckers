package server.manager;

import server.lobby.Lobby;

public interface ILobbyManager {
    void createLobby();

    void removeLobby(Lobby lobby);

    void setLobbyAsWaiting(Lobby lobby);

    void setLobbyAsRunning(Lobby lobby);

    void runLobby(Lobby lobby);
}
