package server.player;

import server.board.Coordinates;
import server.lobby.Lobby;

import java.util.ArrayList;

public class Bot implements PlayerTemplate{
    public int pid;
    public String name;
    public Lobby lobby;
    public int corner;
    public volatile Difficult difficultLevel;
    private volatile Runnable borRunnable;
    private volatile Thread botThread;
    public boolean myTurn;
    public volatile ArrayList<Coordinates> currentCoordinates;
    public volatile ArrayList<Coordinates> destinationCoordinates;



    public Bot(Difficult difficultLevel) {
        this.difficultLevel = difficultLevel;
        currentCoordinates = new ArrayList<>();
        destinationCoordinates = new ArrayList<>();
        myTurn = false;
        //nnManager = new NNManager();
        //Runnable runners = new Runnable;
        //Thread threads = new Thread;
    }


    @Override
    public void joinToLobby(Lobby lobby, int corner) {
        name = "Bot " + lobby.name + "-" + String.valueOf(corner) + " " + difficultLevel.toString();
        this.lobby = lobby;
        this.corner = corner;
    }

    @Override
    public void exitFromLobby() {
        //DO NOTHING
    }

    @Override
    public void pass() {
        //DO NOTHING
    }

    @Override
    public void sendMessage(String message) {
        //DO NOTHING
    }

    @Override
    public void yourTurn() {
        myTurn = true;
    }

    @Override
    public String getPlayersNames() {
        String playersList = "";
        for(PlayerTemplate p: lobby.players) {
            if (p == null) {
                continue;
            }
            playersList = playersList.concat("," + p.getName());
        }
        return playersList;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Bot doesn't have pid, so return -1
     * @return -1
     */
    @Override
    public int getPid() {
        return -1;
    }

    @Override
    public boolean isBot() {
        return true;
    }

    @Override
    public void addCurrentCoordinates(int x, int y){
        currentCoordinates.add(new Coordinates(x, y));
    }

    @Override
    public void addDestinationCoordinates(int x, int y){
        destinationCoordinates.add(new Coordinates(x, y));
    }

    @Override
    public void start() {
        borRunnable = new BotThread(this);
        botThread = new Thread(borRunnable);
        botThread.start();
    }

    @Override
    public boolean compareCoordinates() {
        int correct = 0;
        for(Coordinates c : currentCoordinates) {
            int cX = c.getX();
            int cY = c.getY();
            for(Coordinates d : destinationCoordinates) {
                if(d.getX() == cX && d.getY() == cY) {
                    correct++;
                    break;
                }
            }
        }
        if(correct == destinationCoordinates.size()) {
            return true;
        }
        return false;
    }

    @Override
    public int getCorner() {
        return corner;
    }

    @Override
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public void updateDestinationCoordinates(Coordinates currentCoordinates, Coordinates destinationCoordinates) {
        int curX = currentCoordinates.getX();
        int curY = currentCoordinates.getY();
        for(Coordinates c : this.currentCoordinates) {
            if(c.getX() == curX && c.getY() == curY) {
                this.currentCoordinates.remove(c);
                break;
            }
        }
        this.currentCoordinates.add(destinationCoordinates);
    }
}
