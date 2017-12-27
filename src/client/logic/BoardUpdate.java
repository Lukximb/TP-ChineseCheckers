package client.logic;

import client.core.ClientGUI;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import server.board.Coordinates;
import server.player.CommonMoveLogic;

import java.util.ArrayList;

public class BoardUpdate {
    private ClientGUIController controller;
    private ClientGUI client;
    private MoveType moveType;
    private MoveType prevMoveType;
    private boolean correctMove;
    private ArrayList<Coordinates> pawns;
    private ArrayList<Integer> enemyCorner;
    private int corner = 0;
    private int numberOfPlayers;
    private int rows;
    private Image img;
    private Image[] enemyImg;
    private ArrayList<ArrayList<Coordinates>> enemyCoordinates;
    private Coordinates cCoordinates;
    private Coordinates dCoordinates;
    private CommonMoveLogic moveLogic;

    public BoardUpdate(ClientGUIController controller, ClientGUI client) {
        this.controller = controller;
        this.client = client;
        moveType = MoveType.EMPTY;
        prevMoveType = MoveType.EMPTY;
        correctMove = false;
        pawns = new ArrayList<>();
        enemyCorner = new ArrayList<>();
        moveLogic = new CommonMoveLogic();
    }

    public void setCorner(int corner) {
        this.corner = corner;
    }

    public void createPawnList(int rows, int numberOfPlayers) {
        this.rows = rows;
        this.numberOfPlayers  = numberOfPlayers;

        pawns = moveLogic.fillPawnList(rows, corner);
        img = setPawnColor(corner);
        setEnemyCorner();
        drawPawns();
    }

    private Image setPawnColor(int cor) {
        if (cor == 0) {
            return new Image("/client/pawnPink.png");
        } else if (cor == 1) {
            return new Image("/client/pawnYellow.png");
        } else if (cor == 2) {
            return new Image("/client/pawnBlue.png");
        } else if (cor == 3) {
            return new Image("/client/pawnGreen.png");
        } else if (cor == 4) {
            return new Image("/client/pawnRed.png");
        } else if (cor == 5) {
            return new Image("/client/pawnLightBlue.png");
        } else {
            return null;
        }
    }

    private void drawPawns() {
        ObservableList<Node> childrens = controller.board.getChildren();
        for (Node node : childrens) {
            for (Coordinates cPawns : pawns) {
                if(GridPane.getRowIndex(node) == cPawns.getX() &&
                        GridPane.getColumnIndex(node) == cPawns.getY()) {
                    Circle circle = (Circle)node;
                    circle.setFill(Color.TRANSPARENT);
                    circle.setFill(new ImagePattern(img));
                }
            }
            for (int c: enemyCorner) {
                for (Coordinates cEnemPawn : enemyCoordinates.get(c)) {
                    if(GridPane.getRowIndex(node) == cEnemPawn.getX() &&
                            GridPane.getColumnIndex(node) == cEnemPawn.getY()) {
                        Circle circle = (Circle)node;
                        circle.setFill(Color.TRANSPARENT);
                        circle.setFill(new ImagePattern(enemyImg[c]));
                    }
                }
            }
        }
    }

    private void setEnemyCorner() {
        if(numberOfPlayers == 2) {
            enemyCorner.add(0);
            enemyCorner.add(3);
            int index = 0;
            for (int i: enemyCorner) {
                if (i == corner) {
                    enemyCorner.remove(index);
                    break;
                }
                index++;
            }
        } else if(numberOfPlayers == 3) {
            enemyCorner.add(0);
            enemyCorner.add(2);
            enemyCorner.add(4);
            int index = 0;
            for (int i: enemyCorner) {
                if (i == corner) {
                    enemyCorner.remove(index);
                    break;
                }
                index++;
            }
        } else if(numberOfPlayers == 4) {
            enemyCorner.add(0);
            enemyCorner.add(1);
            enemyCorner.add(3);
            enemyCorner.add(4);
            int index = 0;
            for (int i: enemyCorner) {
                if (i == corner) {
                    enemyCorner.remove(index);
                    break;
                }
                index++;
            }
        } else if(numberOfPlayers == 6) {
            for (int i = 0; i < 6; i++) {
                if ( i != corner) {
                    enemyCorner.add(i);
                }
            }
        }
        enemyImg = new Image[6];
        enemyCoordinates = new ArrayList<ArrayList<Coordinates>>(6);

        for ( int k = 0; k < 6; k++) {
            ArrayList<Coordinates> a = new ArrayList<>();
            enemyCoordinates.add(a);
        }

        for (Integer i : enemyCorner) {
            enemyImg[i] = setPawnColor(i);
            ArrayList<Coordinates> enemyPawn = new ArrayList<>();
            enemyPawn = moveLogic.fillPawnList(rows, i);
            enemyCoordinates.set(i, enemyPawn);
            System.out.println("enemy: " + i);
        }
    }

    public void moveEnemyPawn(int corner, int cX, int cY, int dX, int dY) {
        for (Coordinates c : enemyCoordinates.get(corner)) {
            if (c.getX() == cX && c.getY() == cY) {
                enemyCoordinates.get(corner).remove(c);
                enemyCoordinates.get(corner).add(new Coordinates(dX, dY));
                break;
            }
        }

        ObservableList<Node> childrens = controller.board.getChildren();

        for (Node node : childrens) {
            if(GridPane.getRowIndex(node) == cX &&
                    GridPane.getColumnIndex(node) == cY) {
                Circle circle = (Circle)node;
                circle.setFill(Color.WHITE);
            }
            if(GridPane.getRowIndex(node) == dX &&
                    GridPane.getColumnIndex(node) == dY) {
                Circle circle = (Circle)node;
                circle.setFill(Color.TRANSPARENT);
                circle.setFill(new ImagePattern(enemyImg[corner]));
            }
        }

    }

    public void doMoveOnClick(ActionEvent event) {
        if (controller.currentPosition != null && controller.destinationPosition != null) {
            if (controller.jumpPositions.isEmpty()) {
                cCoordinates =
                        new Coordinates(GridPane.getRowIndex(controller.currentPosition),
                                GridPane.getColumnIndex(controller.currentPosition));
                dCoordinates =
                        new Coordinates(GridPane.getRowIndex(controller.destinationPosition),
                                GridPane.getColumnIndex(controller.destinationPosition));

                client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

                int index = 0;
                for (Coordinates c : pawns) {
                    if (c.getX() == GridPane.getRowIndex(controller.currentPosition) &&
                            c.getY() == GridPane.getColumnIndex(controller.currentPosition)) {
                        pawns.remove(index);
                        break;
                    }
                    index++;
                }
                pawns.add(dCoordinates);

                Circle circleC = (Circle)controller.currentPosition;
                circleC.setStrokeWidth(1);
                circleC.setStroke(Color.BLACK);
                circleC.setFill(Color.WHITE);
                controller.currentPosition = null;

                Circle circleD = (Circle)controller.destinationPosition;
                circleD.setStrokeWidth(1);
                circleD.setFill(Color.TRANSPARENT);
                circleD.setFill(new ImagePattern(img));
                circleD.setStroke(Color.BLACK);
                controller.destinationPosition = null;
                controller.destinationSelected = false;
            } else {
                //Current --> 1st jump
                cCoordinates =
                        new Coordinates(GridPane.getRowIndex(controller.currentPosition),
                                GridPane.getColumnIndex(controller.currentPosition));
                dCoordinates =
                        new Coordinates(GridPane.getRowIndex(controller.jumpPositions.get(0)),
                                GridPane.getColumnIndex(controller.jumpPositions.get(0)));
                client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

                int index = 0;
                for (Coordinates c : pawns) {
                    if (c.getX() == GridPane.getRowIndex(controller.currentPosition) &&
                            c.getY() == GridPane.getColumnIndex(controller.currentPosition)) {
                        pawns.remove(index);
                        break;
                    }
                    index++;
                }
                pawns.add(dCoordinates);

                Circle circleC = (Circle)controller.currentPosition;
                circleC.setStrokeWidth(1);
                circleC.setStroke(Color.BLACK);
                circleC.setFill(Color.WHITE);
                controller.currentPosition = null;

                Circle circleJ = (Circle)controller.jumpPositions.get(0);
                circleJ.setStrokeWidth(1);
                circleJ.setFill(Color.TRANSPARENT);
                circleJ.setFill(new ImagePattern(img));
                circleJ.setStroke(Color.BLACK);

                for (int i = 1; i < controller.jumpPositions.size(); i++) {
                    Circle circleJ1 = (Circle)controller.jumpPositions.get(i-1);
                    circleJ1.setStrokeWidth(1);
                    circleJ1.setFill(Color.WHITE);
                    circleJ1.setStroke(Color.BLACK);

                    cCoordinates =
                            new Coordinates(GridPane.getRowIndex(controller.jumpPositions.get(i-1)),
                                    GridPane.getColumnIndex(controller.jumpPositions.get(i-1)));
                    dCoordinates =
                            new Coordinates(GridPane.getRowIndex(controller.jumpPositions.get(i)),
                                    GridPane.getColumnIndex(controller.jumpPositions.get(i)));
                    client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

                    Circle circleJ2 = (Circle)controller.jumpPositions.get(i);
                    circleJ2.setStrokeWidth(1);
                    circleJ2.setFill(Color.TRANSPARENT);
                    circleJ2.setFill(new ImagePattern(img));
                    circleJ2.setStroke(Color.BLACK);

                    index = 0;
                    for (Coordinates c : pawns) {
                        if (c.getX() == GridPane.getRowIndex(controller.jumpPositions.get(i-1)) &&
                                c.getY() == GridPane.getColumnIndex(controller.jumpPositions.get(i-1))) {
                            pawns.remove(index);
                            break;
                        }
                        index++;
                    }
                    pawns.add(dCoordinates);
                }

                Circle circleJL = (Circle)controller.jumpPositions.get(controller.jumpPositions.size()-1);
                circleJL.setStrokeWidth(1);
                circleJL.setFill(Color.WHITE);
                circleJL.setStroke(Color.BLACK);

                cCoordinates =
                        new Coordinates(GridPane.getRowIndex(controller.jumpPositions.get(controller.jumpPositions.size()-1)),
                                GridPane.getColumnIndex(controller.jumpPositions.get(controller.jumpPositions.size()-1)));
                dCoordinates =
                        new Coordinates(GridPane.getRowIndex(controller.destinationPosition),
                                GridPane.getColumnIndex(controller.destinationPosition));
                client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

                index = 0;
                for (Coordinates c : pawns) {
                    if (c.getX() == GridPane.getRowIndex(controller.jumpPositions.get(controller.jumpPositions.size()-1)) &&
                            c.getY() == GridPane.getColumnIndex(controller.jumpPositions.get(controller.jumpPositions.size()-1))) {
                        pawns.remove(index);
                        break;
                    }
                    index++;
                }
                pawns.add(dCoordinates);

                controller.jumpPositions.clear();

                Circle circleD = (Circle)controller.destinationPosition;
                circleD.setStrokeWidth(1);
                circleD.setFill(Color.TRANSPARENT);
                circleD.setFill(new ImagePattern(img));
                circleD.setStroke(Color.BLACK);
                controller.destinationPosition = null;
                controller.destinationSelected = false;
            }
            moveType = MoveType.EMPTY;
            prevMoveType = MoveType.EMPTY;
            correctMove = false;
        }
    }

    public void chooseCircleOnClick(MouseEvent event) {
        ObservableList<Node> childrens = controller.board.getChildren();
        for (Node node : childrens) {
            if (GridPane.getRowIndex(node) == GridPane.getRowIndex((Node) event.getTarget()) &&
                    GridPane.getColumnIndex(node) == GridPane.getColumnIndex((Node) event.getTarget())) {
                if (controller.currentPosition == null
                        && node != controller.destinationPosition
                        && event.getButton() == MouseButton.PRIMARY) {

                    if (moveLogic.checkStartPosition(pawns, GridPane.getRowIndex(node), GridPane.getColumnIndex(node))) {
                        controller.setStartNode(node);
                    }
                } else if (node == controller.currentPosition
                        && event.getButton() == MouseButton.PRIMARY) {

                    controller.removeStartNode(node);

                } else if (controller.destinationPosition == null
                        && controller.currentPosition != null
                        && event.getButton() == MouseButton.SECONDARY
                        && !controller.jumpPositions.contains(node)) {

                    Coordinates cCoordinates =
                            new Coordinates(GridPane.getRowIndex(controller.previousNode),
                                    GridPane.getColumnIndex(controller.previousNode));
                    Coordinates dCoordinates =
                            new Coordinates(GridPane.getRowIndex(node),
                                    GridPane.getColumnIndex(node));
                    correctMove = moveLogic.checkMoveType(moveType, prevMoveType, cCoordinates, dCoordinates);
                    moveType = moveLogic.getMoveType();
                    prevMoveType = moveLogic.getPrevMoveType();

                    if (correctMove) {
                        controller.waitingNode = node;
                        controller.clientListener.setMove("end");
                        client.connection.invokeCheckMoveMethod(client.player, "checkMove", cCoordinates, dCoordinates, moveType);
                    }
                } else if (node == controller.destinationPosition
                        && event.getButton() == MouseButton.SECONDARY) {

                    controller.removeDestinationNode(node);
                    if (moveType == MoveType.SINGLE && correctMove) {
                        moveType = MoveType.EMPTY;
                    } else if (moveType == MoveType.SINGLE && prevMoveType == MoveType.EMPTY) {
                        moveType = MoveType.EMPTY;
                        prevMoveType = MoveType.EMPTY;
                        correctMove = true;
                    } else if (prevMoveType == MoveType.JUMP) {
                        moveType = MoveType.JUMP;
                    } else {
                        moveType = MoveType.EMPTY;
                    }


                } else if (!controller.destinationSelected
                        && node != controller.currentPosition
                        && node != controller.destinationPosition
                        && !controller.jumpPositions.contains(node)
                        && event.getButton() == MouseButton.PRIMARY) {

                    Coordinates cCoordinates =
                            new Coordinates(GridPane.getRowIndex(controller.previousNode),
                                    GridPane.getColumnIndex(controller.previousNode));
                    Coordinates dCoordinates =
                            new Coordinates(GridPane.getRowIndex(node),
                                    GridPane.getColumnIndex(node));
                    correctMove = moveLogic.checkMoveType(moveType, prevMoveType, cCoordinates, dCoordinates);
                    moveType = moveLogic.getMoveType();
                    prevMoveType = moveLogic.getPrevMoveType();

                    if (correctMove && moveType == MoveType.JUMP) {
                        controller.waitingNode = node;
                        controller.clientListener.setMove("jump");
                        client.connection.invokeCheckMoveMethod(client.player, "checkMove", cCoordinates, dCoordinates, moveType);
                    } else if (correctMove && moveType == MoveType.SINGLE) {
                        controller.waitingNode = node;
                        controller.clientListener.setMove("end");
                        client.connection.invokeCheckMoveMethod(client.player, "checkMove", cCoordinates, dCoordinates, moveType);
                    }
                } else if (node != controller.currentPosition
                        && node != controller.destinationPosition
                        && controller.jumpPositions.contains(node)
                        && event.getButton() == MouseButton.PRIMARY) {

                    controller.removeJumpNode(node);
                    if (moveType == MoveType.SINGLE && correctMove) {
                        moveType = MoveType.EMPTY;
                    } else if (moveType == MoveType.SINGLE && prevMoveType == MoveType.EMPTY) {
                        moveType = MoveType.EMPTY;
                        prevMoveType = MoveType.EMPTY;
                        correctMove = true;
                    } else if (prevMoveType == MoveType.JUMP) {
                        moveType = MoveType.JUMP;
                    } else {
                        moveType = MoveType.EMPTY;
                    }
                }
                break;
            }
        }
    }

    public void setMoveTypeAsEmpty() {
        this.moveType = MoveType.EMPTY;
    }
}
