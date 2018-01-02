package server.lobby;

//import javafx.scene.paint.Color;

import server.board.Board;
import server.board.Coordinates;
import server.board.Field;
import server.manager.LobbyManager;
import server.manager.PlayerManager;
import server.player.Bot;
import server.player.Player;
import server.player.PlayerTemplate;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class Lobby extends NotificationBroadcasterSupport implements LobbyMBean{
    //public final Color[] colorPalette = {Color.DEEPPINK, Color.YELLOW, Color.MEDIUMBLUE, Color.LIMEGREEN, Color.FIREBRICK, Color.CYAN};
    public LobbyMediator mediator;
    public String name;
    public volatile PlayerTemplate[] players;
    public Board board;
    public PlayerTemplate round;
    public int numberOfPlayers;
    public LobbyManager lobbyManager;

    private PlayerTemplate admin;
    private volatile int roundCorner;
    private int rowNumber;
    private int playerCorner;

    public Lobby(int playerNum, int rowNumber, String lobbyName, PlayerTemplate admin, LobbyMediator mediator) {
        this.mediator = mediator;
        mediator.setLobby(this);
        numberOfPlayers = playerNum;
        this.name = lobbyName;
        this.admin = admin;
        this.rowNumber = rowNumber;
        roundCorner = 0;
        playerCorner = 0;
        players = new PlayerTemplate[numberOfPlayers];
        addPlayer(admin);
        lobbyManager = LobbyManager.getInstance();
    }

    @Override
    public synchronized void startGame() {
        initPlayersOnBoard();
        mediator.setBoard(board);
        round = players[0];
        roundCorner = 0;
        sendNotification(new Notification(String.valueOf(name), this, 110011110,
                "S,StartGame," + rowNumber + "," + numberOfPlayers));
        mediator.startRound();
        for (PlayerTemplate p : players) {
            if (p.isBot()) {
               p.start();
            }
        }
    }

    @Override
    public void endGame() {

    }

    @Override
    public synchronized void addPlayer(PlayerTemplate player) {
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

    @Override
    public void addBot(PlayerTemplate bot) {
        addPlayer(bot);
        sendNotification(new Notification(String.valueOf(name), this,
                001100101010, "B"));

    }

    public boolean isEmpty() {
        boolean empty = true;
            for(PlayerTemplate p : players) {
                if(p != null) {
                    empty = false;
                    break;
                }
            }
        return empty;
    }

    @Override
    public void sendMoveNotification(String message) {
        System.out.println("Notify for: " + name);
        sendNotification(new Notification(String.valueOf(name), this, 110011110, message));
    }

    @Override
    public void removePlayer(PlayerTemplate player) {
        int i = 0;
        int next = 0;
        while(i<numberOfPlayers) {
            if(players[i].equals(player)) {
                players[i] = null;
                next = i+1;
                roundCorner--;
                break;
            }
            i++;
        }
        while(next < numberOfPlayers) {
            if(players[next] == null) {
                break;
            }
            players[next-1] = players[next];
            players[next] = null;
            next++;
        }
    }

    @Override
    public void removePlayer(String playerName) {
        for(PlayerTemplate p : players) {
            if(p.getName().equals(playerName)) {
                p.exitFromLobby();
                String message = "X," + p.getName() + "," + this.name;
                sendNotification(new Notification(String.valueOf(p.getPid()), this, 110000110, message));
                break;
            }
        }
    }

    @Override
    public void removeBot(Bot bot) {

    }

    @Override
    public void initPlayersOnBoard() {

        for(int i=0; i<numberOfPlayers; i++) {
            if(numberOfPlayers == 6) {
                //players[i].setColor(colorPalette[i]);
                putPawnsOnBoard(players[i], i);
                createDestinationCoordinates(players[i], i);
            } else if(numberOfPlayers == 4) {
                if(i == 0) {
                    putPawnsOnBoard(players[i], 0);
                    createDestinationCoordinates(players[i], 0);
                } else if(i == 1) {
                    putPawnsOnBoard(players[i], 1);
                    createDestinationCoordinates(players[i], 1);
                } else if(i == 2) {
                    putPawnsOnBoard(players[i], 3);
                    createDestinationCoordinates(players[i], 3);
                } else if(i == 3) {
                    putPawnsOnBoard(players[i], 4);
                    createDestinationCoordinates(players[i], 4);
                }
//                if(i%2 == 0) {
//                    //players[i].setColor(colorPalette[i*2]);
//                    putPawnsOnBoard(players[i], i*2);
//                    createDestinationCoordinates(players[i], i*2);
//                }
//                else {
//                    //players[i].setColor(colorPalette[i*2-1]);
//                    putPawnsOnBoard(players[i], i*2-1);
//                    createDestinationCoordinates(players[i], i*2-1);
//                }
            } else if(numberOfPlayers == 3) {
                //players[i].setColor(colorPalette[i*2]);
                putPawnsOnBoard(players[i], i*2);
                createDestinationCoordinates(players[i], i*2);
            } else if(numberOfPlayers == 2) {
                //players[i].setColor(colorPalette[i*3]);
                putPawnsOnBoard(players[i], i*3);
                createDestinationCoordinates(players[i], i*3);
            }
        }
    }

    @Override
    public void putPawnsOnBoard(PlayerTemplate player, int corner) {
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
            return;
        }
        if(corner == 0 || corner == 2 || corner == 4) {
            for(int i=0; i<rows; i++) {
                for(int j=0; j<rows-i; j++) {
                    if(i%2 == 0) {
                        Field f = board.getField(new Coordinates(n+i, m+2*j+i));
                        f.setPlayerOn(player);
                        player.addCurrentCoordinates(n+i, m+2*j+i);
                    }
                    else {
                        Field f = board.getField(new Coordinates(n+i, m+2*j+i));
                        f.setPlayerOn(player);
                        player.addCurrentCoordinates(n+i, m+2*j+i);
                    }
                }
            }
        }
        else {
            for(int i=0; i<rows; i++) {
                for(int j=0; j<rows-i; j++) {
                    if(i%2 == 0) {
                        board.getField(new Coordinates(n-i, m+2*j+i)).setPlayerOn(player);
                        player.addCurrentCoordinates(n-i, m+2*j+i);
                    }
                    else {
                        board.getField(new Coordinates(n-i, m+2*j+i)).setPlayerOn(player);
                        player.addCurrentCoordinates(n-i, m+2*j+i);
                    }
                }
            }
        }
    }

    @Override
    public void createDestinationCoordinates(PlayerTemplate player, int corner) {
        int bN = board.getN();
        int bM = board.getM();
        int rows = bN - bM;
        int n, m;
        if(corner == 3) {
            n = 3 * rows + 1;
            m = 2 * rows + 1;
        } else if(corner == 4) {
            n = 3 * rows;
            m = 0;
        } else if(corner == 5) {
            n = rows;
            m = 0;
        } else if(corner == 0) {
            n = rows - 1;
            m = 2 * rows + 1;
        } else if(corner == 1) {
            n = rows;
            m = 4 * rows + 2;
        } else if(corner == 2) {
            n = 3 * rows;
            m = 4 * rows + 2;
        } else {
            return;
        }
        if(corner == 0 || corner == 2 || corner == 4) {
            for(int i=0; i<rows; i++) {
                for(int j=0; j<rows-i; j++) {
                    player.addDestinationCoordinates(n-i, m+2*j+i);
                }
            }
        }
        else {
            for(int i=0; i<rows; i++) {
                for(int j=0; j<rows-i; j++) {
                    player.addDestinationCoordinates(n+i, m+2*j+i);
                }
            }
        }
    }

    @Override
    public Board getBoard() {
        return board;
    }

//    @Override
//    public void getRoundTime() {
//        mediator.getRoundTime();
//    }

    @Override
    public synchronized void nextRound() {
        mediator.checkWinner();
        do {
            roundCorner++;
            if (roundCorner == numberOfPlayers) {
                roundCorner = 0;
            }
            round = players[roundCorner];
        } while(round == null);
        mediator.startRound();
        System.out.println(">> Lobby " + name + " started next round for corner " + roundCorner);
        if (players[roundCorner].isBot()) {
            players[roundCorner].yourTurn();
        }
    }

    public int getRoundCornerValue() {
        if(numberOfPlayers == 6) {
            return roundCorner;
        } else if(numberOfPlayers == 4) {
            if(roundCorner == 0) {
                return 0;
            } else if(roundCorner == 1) {
                return 1;
            } else if(roundCorner == 2) {
                return 3;
            } else if(roundCorner == 3) {
                return 4;
            }
        } else if(numberOfPlayers == 3) {
            return roundCorner*2;
        } else if(numberOfPlayers == 2) {
            return roundCorner*3;
        }
        return -1;
    }

    @Override
    public synchronized void sendTurnNotification() {
        String message = "T,";
        message = message.concat(Integer.toString(getRoundCornerValue()));
        sendNotification(new Notification(String.valueOf(name), this, 1100110000, message));
    }

    public synchronized void sendWinnerNotification(PlayerTemplate winner, PlayerTemplate looser) {
        String message = "+,";
        if(looser == null) {
            message = message.concat(winner.getName() + "," + " ");
        } else {
            message = message.concat(winner.getName() + "," + looser.getName());
        }
        sendNotification(new Notification(String.valueOf(winner.getPid()), this, 1100111110, message));
    }

    public synchronized void sendLooserNotification(PlayerTemplate looser, PlayerTemplate winner) {
        String message = "-,";
        if(winner == null) {
            message = message.concat(looser.getName() + "," + " ");
        } else {
            message = message.concat(looser.getName() + "," + winner.getName());
        }
        sendNotification(new Notification(String.valueOf(looser.getPid()), this, 11001100, message));
    }

    public synchronized void sendWinnerPopUpNotification(PlayerTemplate winner, PlayerTemplate looser) {
        String message = "*,";
        if(looser == null) {
            message = message.concat(winner.getName() + "," + " ");
        } else {
            message = message.concat(winner.getName() + "," + looser.getName());
        }
        for(PlayerTemplate p : players) {
            if(p != null) {
                if (!p.isBot()) {
                    if(!p.equals(looser) && !p.equals(winner)) {
                        sendNotification(new Notification(String.valueOf(p.getPid()), this, 11001100, message));
                    }
                }
            }
        }
    }

    @Override
    public void printMessage(PlayerTemplate player, String message) {
        //chat.printMessage(player, message);
    }

    public void surrender(PlayerTemplate player) {
        PlayerManager playerManager = PlayerManager.getInstance();

        if(numberOfPlayers != 3) {
            int winnerCorner = (player.getCorner() + 3) % 6;

            for (int i = 0; i < numberOfPlayers; i++) {
                if (players[i] != null) {
                    if (players[i].getCorner() == winnerCorner) {
                        sendWinnerNotification(players[i], player);
                        sendLooserNotification(player, players[i]);
                        sendWinnerPopUpNotification(players[i], player);

                        if (players[i] instanceof Player) {
                            playerManager.movePlayerToFreeList((Player) players[i]);
                        }

                        players[i].setLobby(null);
                        players[i] = null;
                        break;
                    }
                }
            }
        } else {
            sendLooserNotification(player, null);
        }

        for(int i=0; i<numberOfPlayers; i++) {
            if(players[i].equals(player)) {
                if(round.equals(players[i])) {
                    nextRound();
                }

                if(players[i] instanceof Player) {
                    playerManager.movePlayerToFreeList((Player)players[i]);
                }

                players[i].setLobby(null);
                players[i] = null;
                break;
            }
        }

        if(isEmpty()) {
            lobbyManager.removeLobby(this);
        }

    }

}
