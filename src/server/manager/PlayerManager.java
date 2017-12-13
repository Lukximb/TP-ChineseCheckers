package server.manager;

import javafx.scene.paint.Color;
import server.player.Bot;
import jmx.Player;

import java.util.ArrayList;

public class PlayerManager implements IPlayerManager {
    public ArrayList<Player> playerFreeList;
    public ArrayList<Player> playerInGameList;
    public ArrayList<Bot> botList;
    public String name;
    public final Color[] colorPalette = {Color.DEEPPINK, Color.YELLOW, Color.MEDIUMBLUE, Color.LIMEGREEN, Color.FIREBRICK, Color.CYAN};
    private static volatile PlayerManager instance;


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
        name = "PM";
        playerFreeList = new ArrayList<>();
        playerInGameList = new ArrayList<>();
        botList = new ArrayList<>();
    }

    @Override
    public void getNewPlayer(Player player) {
        playerFreeList.add(player);
        System.out.println(">> PM added new player to list");
        for(Player p: playerFreeList){
            System.out.print(p.pid + " ");
        }
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

    @Override
    public int checkPlayerStatus(int pid) {
        for(Player p: playerFreeList) {
            if(p.pid == pid) {
                return 1;
            }
        }
        for(Player p: playerInGameList) {
            if(p.pid == pid) {
                return 2;
            }
        }
        return 0;
    }
}
