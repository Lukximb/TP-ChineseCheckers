package server.player;

import client.logic.MoveType;
import javafx.scene.paint.Color;
import server.board.Coordinates;
import server.board.Field;
import server.lobby.Lobby;
import server.neuralNetwork.NNManager;

import java.util.ArrayList;

public class Bot implements PlayerTemplate{
    public int pid;
    public String name;
    public Lobby lobby;
    private int corner;

    private Difficult difficultLevel;
    private boolean myTurn;
    private ArrayList<Coordinates> pawns;
    private ArrayList<Coordinates> destinationPawns;
    private int enemyCorner;
    private int numberOfPlayers;
    private int rows;
    private Coordinates cCoordinates;
    private Coordinates dCoordinates;
    private CommonMoveLogic moveLogic;
    private NNManager nnManager;
    private int failMoveNumber;

    public Bot(Difficult difficultLevel) {
        this.difficultLevel = difficultLevel;
        pawns = new ArrayList<>();
        destinationPawns = new ArrayList<>();
        moveLogic = new CommonMoveLogic();
        nnManager = new NNManager();
    }

    public void move() {
        myTurn = true;
        failMoveNumber = 0;
        ArrayList<Coordinates> coordinatesList =  new ArrayList<>();
        double bestWayScore = 0;
        for (Coordinates c : pawns) {
            if (myTurn) {
                ArrayList<Coordinates> coordList;
                coordList = find(c);
                for (int k = 0; k < coordList.size(); k++) {
                    if (coordList.get(k) != null) {
                        if (k == 0 || k == 1 || k == 4 || k == 7 || k == 10 || k == 11){
                            System.out.println(name + " jump " + c.getX() + " " + c.getY() + " to " + coordList.get(k).getX() + " " + coordList.get(k).getY());
                        } else {
                            for(Coordinates eC : destinationPawns) {
                                double startDistance = Math.sqrt(Math.pow(eC.getX() - c.getX(), 2)
                                        + Math.pow(eC.getY() - c.getY(), 2));
                                double destinationDistance = Math.sqrt(Math.pow(eC.getX() - coordList.get(k).getX(), 2)
                                        + Math.pow(eC.getY() - coordList.get(k).getY(), 2));
                                if (startDistance - destinationDistance >= bestWayScore) {
                                    bestWayScore = startDistance - destinationDistance;
                                    coordinatesList.clear();
                                    coordinatesList.add(c);
                                    coordinatesList.add(coordList.get(k));
                                    System.out.println(name + " single " + c.getX() + " " + c.getY() + " to "
                                            + coordList.get(k).getX() + " " + coordList.get(k).getY());
                                }
                            }
                        }
                    }
                }
            } else {
                executeMove(coordinatesList);
                break;
            }
        }

        if (myTurn) {
            lobby.nextRound();
        }
    }

    private void executeMove(ArrayList<Coordinates> coordinatesList) {
        if (coordinatesList.get(0) != null && coordinatesList.get(1) != null) {
            System.out.println("move from " +name);
            if (lobby.mediator.move(this, coordinatesList.get(0), coordinatesList.get(1))) {
                changePawns(coordinatesList.get(0), coordinatesList.get(1));
                lobby.sendMoveNotification("E," + corner + ","
                        + coordinatesList.get(0).getX() + "," + coordinatesList.get(0).getY() + ","
                        + coordinatesList.get(1).getX() + "," + coordinatesList.get(1).getY());
            }
        }
    }

    private void changePawns(Coordinates current, Coordinates destination) {
        int i = 0;
        for (Coordinates c : pawns) {
            if (c.getX() == current.getX() && c.getY() == current.getY()) {
                pawns.remove(i);
                pawns.add(destination);
                break;
            }
            i++;
        }
    }

    private ArrayList<Coordinates> find(Coordinates c) {
        int boardN = lobby.board.getN();
        int boardM = (2 * lobby.board.getM()) - 1;
        ArrayList<Coordinates> coordinatesList =  new ArrayList<>();
        ArrayList<Double> rawoutput;
        ArrayList<Double> data;
        data = getData(c);
        rawoutput = nnManager.getOutput(data);
        int i = 0;
        for (Double d : rawoutput) {
            if (myTurn == false) {
                break;
            }
            if (d > 0.5) {
                Coordinates cD = getCoordinates(i, c);
                if (cD.getX() < 0 || cD.getY() < 0 || cD.getX() >= boardN || cD.getY() >= boardM) {
                    cD = null;
                }
                if (i == 0 || i == 1 || i == 4 || i == 7 || i == 10 || i == 11) {
                    if (cD != null && checkMove(c, cD, MoveType.JUMP)) {
                        coordinatesList.add(cD);
                    } else {
                        coordinatesList.add(null);
                        failMoveNumber++;
                    }
                } else {
                    if (cD != null && checkMove(c, cD, MoveType.SINGLE)) {
                        coordinatesList.add(cD);
                    } else {
                        coordinatesList.add(null);
                        failMoveNumber++;
                    }
                }
                checkFailNumber();
            }
            i++;
        }
        return coordinatesList;
    }

    private ArrayList<Coordinates> findJump(Coordinates c) {
        ArrayList<Coordinates> coordinatesList = new ArrayList<>();
        ArrayList<Double> rawoutput;
        ArrayList<Double> data;
        data = getData(c);
        rawoutput = nnManager.getOutput(data);
        int i = 0;
        for (Double d : rawoutput) {
            if (d > 0.5) {
                Coordinates cD = getCoordinates(i, c);
                if (i == 0 || i == 1 || i == 4 || i == 7 || i == 10 || i == 11) {
                    if (checkMove(c, cD, MoveType.JUMP)) {
                        coordinatesList.add(cD);
                    } else {
                        failMoveNumber++;
                    }
                }
                checkFailNumber();
            }
            i++;
        }
        return coordinatesList;
    }

    private ArrayList<Double> getData(Coordinates c) {
        int n = lobby.board.getN();
        int m = lobby.board.getM();
        int x = c.getX();
        int y = c.getY();
        ArrayList<Double> data = new ArrayList<>();

        if (x - 2 < 0 || y - 2 < 0)
            data.add(1.0);
        else {
            checkField(data, x-2, y-2);
        }

        if (x - 2 < 0 || y + 2 >= (2*m)-1)
            data.add(1.0);
        else {
            checkField(data, x-2, y+2);
        }

        if (x - 1 < 0 || y - 1 < 0)
            data.add(1.0);
        else {
            checkField(data, x-1, y-1);
        }

        if (x - 1 < 0 || y + 1 >= (2*m)-1)
            data.add(1.0);
        else {
            checkField(data, x-1, y+1);
        }

        if (y - 4 < 0)
            data.add(1.0);
        else {
            checkField(data, x, y-4);
        }

        if (y - 2 < 0)
            data.add(1.0);
        else {
            checkField(data, x, y-2);
        }

        if (y + 2 >= (2*m)-1)
            data.add(1.0);
        else {
            checkField(data, x, y+2);
        }

        if (y + 4 >= (2*m)-1)
            data.add(1.0);
        else {
            checkField(data, x, y+4);
        }

        if (x + 1 >= n || y - 1 < 0)
            data.add(1.0);
        else {
            checkField(data, x+1, y-1);
        }

        if (x + 1 >= n || y + 1 >= (2*m)-1)
            data.add(1.0);
        else {
            checkField(data, x+1, y+1);
        }

        if (x + 2 >= n || y - 2 < 0)
            data.add(1.0);
        else {
            checkField(data, x+2, y-2);
        }

        if (x + 2 >= n || y + 2 >= (2*m)-1)
            data.add(1.0);
        else {
            checkField(data, x+2, y+2);
        }

        return data;
    }

    private Coordinates getCoordinates(int i, Coordinates c) {
        int x = c.getX();
        int y = c.getY();
        if (i == 0) {
            return new Coordinates(x - 2, y - 2);
        } else if (i == 1) {
            return new Coordinates(x - 2,y + 2);
        } else if (i == 2) {
            return new Coordinates(x - 1,y - 1);
        } else if (i == 3) {
            return new Coordinates(x - 1,y + 1);
        } else if (i == 4) {
            return new Coordinates(x, y - 4);
        } else if (i == 5) {
            return new Coordinates(x, y - 2);
        } else if (i == 6) {
            return new Coordinates(x, y + 2);
        } else if (i == 7) {
            return new Coordinates(x, y + 4);
        } else if (i == 8) {
            return new Coordinates(x + 1, y - 1);
        } else if (i == 9) {
            return new Coordinates(x + 1, y + 1);
        } else if (i == 10) {
            return new Coordinates(x + 2, y - 2);
        } else {
            return new Coordinates(x + 2, y + 2);
        }
    }

    private ArrayList<Double> checkField(ArrayList<Double> data, int x, int y) {
        Coordinates newC = new Coordinates(x, y);
        Field field = lobby.mediator.getField(newC);
        if (field == null) {
            data.add(1.0);
        } else if (lobby.mediator.getField(newC).getPlayerOn() == null)
            data.add(0.0);
        else
            data.add(1.0);
        return data;
    }

    private void checkFailNumber() {
        if (difficultLevel == Difficult.EASY && failMoveNumber == 5) {
            lobby.nextRound();
            myTurn = false;
            System.out.println("bot " + name + " has max fail number, pass");
        } else if (difficultLevel == Difficult.MEDIUM && failMoveNumber == 10) {
            lobby.nextRound();
            myTurn = false;
            System.out.println("bot " + name + " has max fail number, pass");
        } else if (difficultLevel == Difficult.HARD && failMoveNumber == 45) {
            lobby.nextRound();
            myTurn = false;
            System.out.println("bot " + name + " has max fail number, pass");
        }
    }

    public boolean checkMove(Coordinates currentCoordinates, Coordinates destinationCoordinates, MoveType moveType) {
        return lobby.mediator.checkMove(currentCoordinates, destinationCoordinates, moveType );
    }

    @Override
    public void start(int rows, int numberOfPlayers) {
        this.rows = rows;
        this.numberOfPlayers  = numberOfPlayers;

        pawns = moveLogic.fillPawnList(rows, corner);
        destinationPawns = moveLogic.fillPawnList(rows, enemyCorner);
    }

    @Override
    public void joinToLobby(Lobby lobby, int corner) {
        name = "Bot " + lobby.name + "-" + String.valueOf(corner) + " " + difficultLevel.toString();
        this.lobby = lobby;
        this.corner = corner;
        enemyCorner = (corner + 3) % 6;
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
        move();
    }

    @Override
    public void setColor(Color color) {
        //DO NOTHING
    }

    @Override
    public String getPlayersNames() {
        return name;
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
}
