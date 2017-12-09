package server.manager;

import server.player.Bot;
import server.player.Player;

import java.util.ArrayList;

public class PlayerManager implements IPlayerManager {
    public ArrayList<Player> playerFreeList = null;
    public ArrayList<Player> playerInGameList = null;
    public ArrayList<Bot> botList = null;
    private static volatile PlayerManager instance = null;

    public static PlayerManager getInstance() {
        if (instance == null) {
            synchronized (LobbyManager.class) {
                if (instance == null) {
                    instance = new PlayerManager();
                }
            }
        }
        return instance;
    }

    private PlayerManager() {
        playerFreeList = new ArrayList<>();
        playerInGameList = new ArrayList<>();
        botList = new ArrayList<>();
    }

    @Override
    public void getNewPlayer(int pid) {

    }

    @Override
    public void addPlayerToInGameList(Player player) {

    }

    @Override
    public void addPlayerToFreeList(Player player) {

    }

    @Override
    public void removePlayerFromInGameList(Player player) {

    }

    @Override
    public void removePlayerFromFreeList(Player player) {

    }

    @Override
    public void killBot(Bot bot) {

    }
}
