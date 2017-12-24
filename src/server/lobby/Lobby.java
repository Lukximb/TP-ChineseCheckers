package server.lobby;

import javafx.scene.paint.Color;
import server.board.Coordinates;
import server.board.Field;
import server.board.IBoard;
import server.player.Bot;
import jmx.Player;

public class Lobby implements Runnable{
    public final Color[] colorPalette = {Color.DEEPPINK, Color.YELLOW, Color.MEDIUMBLUE, Color.LIMEGREEN, Color.FIREBRICK, Color.CYAN};
    public String name;
    public Player admin;
    public Player[] players;
    public IBoard board;
    public int numberOfPlayers;
    public Player round;
    public int roundCorner;
    public LobbyMediator mediator;
    public Chat chat;
    public int rowNumber;
    public int playerCorner;

    public Lobby(int playerNum, int rowNumber, String lobbyName, Player admin, LobbyMediator mediator) {
        this.mediator = mediator;
        mediator.setLobby(this);
        numberOfPlayers = playerNum;
        this.name = lobbyName;
        this.admin = admin;
        this.rowNumber = rowNumber;
        roundCorner = 0;
        playerCorner = 0;
        players = new Player[numberOfPlayers];
        addPlayer(admin);
    }

    @Override
    public void run() {
        while(true) {
            /*System.out.println("Watek "+id);
            try {
                //usypiamy wątek na 100 milisekund
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    public void startGame() {
        initPlayersOnBoard();
        mediator.setBoard(board);
        round = players[0];
        roundCorner = 0;
    }

    public void endGame() {

    }

    public void addPlayer(Player player) {
        if(roundCorner < numberOfPlayers) {
            player.joinToLobby(this, playerCorner);
            players[roundCorner] = player;
            roundCorner++;
            if (numberOfPlayers == 2) {
                playerCorner += 3;
            } else if (numberOfPlayers == 3) {
                playerCorner += 2;
            } else if (numberOfPlayers == 4) {
                if (roundCorner == 1 || roundCorner == 3) {
                    playerCorner += 1;
                } else if (roundCorner == 2) {
                    playerCorner += 2;
                }
            } else if (numberOfPlayers == 6) {
                playerCorner += 1;
            }
        }
    }

    public void addBot(Bot bot) {

    }

    public void removePlayer(Player player) {
        int i = 0;
        int next = 0;
        while(i<numberOfPlayers) {
            if(players[i].equals(player)) {
                players[i] = null;
                next = i+1;
                roundCorner--;
            }
        }
        while(next < numberOfPlayers || players[next] != null) {
            players[next-1] = players[next];
            players[next] = null;
            next++;
        }
    }

    public void removePlayer(String playerName) {
        int i = 0;
        int next = 0;
        while(i<numberOfPlayers) {
            if(players[i].name.equals(playerName)) {
                players[i] = null;
                next = i+1;
                roundCorner--;
            }
        }
        while(next < numberOfPlayers || players[next] != null) {
            players[next-1] = players[next];
            players[next] = null;
            next++;
        }
    }

    public void removeBot(Bot bot) {

    }

    public void initPlayersOnBoard() {

        for(int i=0; i<numberOfPlayers; i++) {
            if(numberOfPlayers == 6) {
                players[i].setColor(colorPalette[i]);
                System.out.println("i= " + i);
                putPawnsOnBoard(players[i], i);
            } else if(numberOfPlayers == 4) {
                if(i%2 == 0) {
                    players[i].setColor(colorPalette[i*2]);
                    System.out.println("i= " + i);
                    putPawnsOnBoard(players[i], i*2);
                }
                else {
                    players[i].setColor(colorPalette[i*2-1]);
                    putPawnsOnBoard(players[i], i*2-1);
                }
            } else if(numberOfPlayers == 2) {
                players[i].setColor(colorPalette[i*3]);
                System.out.println("i= " + i);
                putPawnsOnBoard(players[i], i*3);
            }
        }
    }

    public void putPawnsOnBoard(Player player, int corner) {
        int bN = board.getN();
        int bM = board.getM();
        int rows = bN - bM;
        int n, m;
        if(corner == 0) {
            n = 3 * rows + 1;
            m = 2 * rows + 1;
        } else if(corner == 1) {
            n = 3 * rows;
            m = 0;
        } else if(corner == 2) {
            n = rows;
            m = 0;
        } else if(corner == 3) {
            n = rows - 1;
            m = 2 * rows + 1;
        } else if(corner == 4) {
            n = rows;
            m = 4 * rows + 2;
        } else if(corner == 5) {
            n = 3 * rows;
            m = 4 * rows + 2;
        } else {
            System.out.println("ELSE");
            return;
        }
        if(corner == 0 || corner == 2 || corner == 4) {
            for(int i=0; i<rows; i++) {
                for(int j=0; j<rows-i; j++) {
                    System.out.println("SetPlayerOnField");
                    if(i%2 == 0) {
                        Field f = board.getField(new Coordinates(n+i, m+2*j+i));
                        f.setPlayerOn(player);
                    }
                    else {
                        Field f = board.getField(new Coordinates(n+i, m+2*j+i));
                        f.setPlayerOn(player);
                    }
                }
            }
        }
        else {
            for(int i=0; i<rows; i++) {
                for(int j=0; j<rows-i; j++) {
                    System.out.println("SetPlayerOnField");
                    if(i%2 == 0) {
                        board.getField(new Coordinates(n-i, m+2*j+i)).setPlayerOn(player);
                    }
                    else {
                        board.getField(new Coordinates(n-i, m+2*j+i)).setPlayerOn(player);
                    }
                }
            }
        }
    }

    public IBoard getBoard() {
        return board;
    }

    public void getRoundTime() {
        mediator.getRoundTime();
    }

    public void nextRound() {
        roundCorner++;
        if(roundCorner == numberOfPlayers) {
            roundCorner = 0;
        }
        round = players[roundCorner];
        mediator.startRound();

    }

    public void printMessage(Player player, String message) {
        chat.printMessage(player, message);
    }
}
