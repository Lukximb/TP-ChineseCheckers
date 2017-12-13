package server.manager;

import jmx.Player;
import server.player.*;

public interface IPlayerManager {
    void getNewPlayer(Player player);

    void addPlayerToInGameList(Player player);

    void addPlayerToFreeList(Player player);

    void removePlayerFromInGameList(Player player);

    void removePlayerFromFreeList(Player player);

    void killBot(Bot bot);

    int checkPlayerStatus(int pid);
}
