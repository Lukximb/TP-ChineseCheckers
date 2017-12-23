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
import java.util.ArrayList;

public class BoardUpdate {
    private ClientGUIController controller;
    private ClientGUI client;
    private MoveType moveType;
    private MoveType prevMoveType;
    private boolean correctMove;
    private ArrayList<Coordinates> pawns;
    private int corner = 0;

    public BoardUpdate(ClientGUIController controller, ClientGUI client) {
        this.controller = controller;
        this.client = client;
        moveType = MoveType.EMPTY;
        prevMoveType = MoveType.EMPTY;
        correctMove = false;
        pawns = new ArrayList<>();
    }
    public void doMoveOnClick(ActionEvent event) {
        Image img = new Image("/client/pawnGreen.png");
        if (controller.currentPosition != null && controller.destinationPosition != null) {
            if (controller.jumpPositions.isEmpty()) {
                Coordinates cCoordinates =
                        new Coordinates(GridPane.getColumnIndex(controller.currentPosition),
                                GridPane.getRowIndex(controller.currentPosition));
                Coordinates dCoordinates =
                        new Coordinates(GridPane.getColumnIndex(controller.destinationPosition),
                                GridPane.getRowIndex(controller.destinationPosition));
                client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

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
                Coordinates cCoordinates =
                        new Coordinates(GridPane.getColumnIndex(controller.currentPosition),
                                GridPane.getRowIndex(controller.currentPosition));
                Coordinates dCoordinates =
                        new Coordinates(GridPane.getColumnIndex(controller.jumpPositions.get(0)),
                                GridPane.getRowIndex(controller.jumpPositions.get(0)));
                client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

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
                            new Coordinates(GridPane.getColumnIndex(controller.jumpPositions.get(i-1)),
                                    GridPane.getRowIndex(controller.jumpPositions.get(i-1)));
                    dCoordinates =
                            new Coordinates(GridPane.getColumnIndex(controller.jumpPositions.get(i)),
                                    GridPane.getRowIndex(controller.jumpPositions.get(i)));
                    client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

                    Circle circleJ2 = (Circle)controller.jumpPositions.get(i);
                    circleJ2.setStrokeWidth(1);
                    circleJ2.setFill(Color.TRANSPARENT);
                    circleJ2.setFill(new ImagePattern(img));
                    circleJ2.setStroke(Color.BLACK);
                }

                Circle circleJL = (Circle)controller.jumpPositions.get(controller.jumpPositions.size()-1);
                circleJL.setStrokeWidth(1);
                circleJL.setFill(Color.WHITE);
                circleJL.setStroke(Color.BLACK);

                cCoordinates =
                        new Coordinates(GridPane.getColumnIndex(controller.jumpPositions.get(controller.jumpPositions.size()-1)),
                                GridPane.getRowIndex(controller.jumpPositions.get(controller.jumpPositions.size()-1)));
                dCoordinates =
                        new Coordinates(GridPane.getColumnIndex(controller.destinationPosition),
                                GridPane.getRowIndex(controller.destinationPosition));
                client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

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
            if(GridPane.getRowIndex(node) == GridPane.getRowIndex((Node)event.getTarget()) &&
                    GridPane.getColumnIndex(node) == GridPane.getColumnIndex((Node)event.getTarget())) {
                if (controller.currentPosition == null
                        && node != controller.destinationPosition
                        && event.getButton() == MouseButton.PRIMARY) {

                    controller.setStartNode(node);

                } else if (node == controller.currentPosition
                        && event.getButton() == MouseButton.PRIMARY) {

                    controller.removeStartNode(node);

                } else if (controller.destinationPosition == null
                        && controller.currentPosition != null
                        && event.getButton() == MouseButton.SECONDARY
                        && !controller.jumpPositions.contains(node)) {

                    Coordinates cCoordinates =
                            new Coordinates(GridPane.getColumnIndex(controller.previousNode),
                                    GridPane.getRowIndex(controller.previousNode));
                    Coordinates dCoordinates =
                            new Coordinates(GridPane.getColumnIndex(node),
                                    GridPane.getRowIndex(node));
                    checkMoveType(cCoordinates,dCoordinates);

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
                            new Coordinates(GridPane.getColumnIndex(controller.previousNode),
                                    GridPane.getRowIndex(controller.previousNode));
                    Coordinates dCoordinates =
                            new Coordinates(GridPane.getColumnIndex(node),
                                    GridPane.getRowIndex(node));
                    checkMoveType(cCoordinates,dCoordinates);

                    if (correctMove && moveType == MoveType.JUMP) {
                        controller.waitingNode = node;
                        controller.clientListener.setMove("jump");
                        client.connection.invokeCheckMoveMethod(client.player, "checkMove", cCoordinates, dCoordinates, moveType);
                    } else if (correctMove && moveType == MoveType.SINGLE) {
                        controller.waitingNode = node;
                        controller.clientListener.setMove("end");
                        client.connection.invokeCheckMoveMethod(client.player, "checkMove", cCoordinates, dCoordinates, moveType);
                    }
                } else if(node != controller.currentPosition
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

    private void checkMoveType (Coordinates cCoordinates, Coordinates dCoordinates) {
        int cX = cCoordinates.getX();
        int cY = cCoordinates.getY();
        int dX = dCoordinates.getX();
        int dY = dCoordinates.getY();

        if (Math.abs(cX - dX) == 2 && Math.abs(cY - dY) == 0 && moveType == MoveType.EMPTY) {
            prevMoveType = moveType;
            moveType = MoveType.SINGLE;
            correctMove = true;
        } else if (Math.abs(cX - dX) == 1 && Math.abs(cY - dY) == 1 && moveType == MoveType.EMPTY) {
            prevMoveType = moveType;
            moveType = MoveType.SINGLE;
            correctMove = true;
        } else if (Math.abs(cX - dX) == 4 && Math.abs(cY - dY) == 0 && moveType != MoveType.SINGLE) {
            prevMoveType = moveType;
            moveType = MoveType.JUMP;
            correctMove = true;
        } else if (Math.abs(cX - dX) == 2 && Math.abs(cY - dY) == 2 && moveType != MoveType.SINGLE) {
            prevMoveType = moveType;
            moveType = MoveType.JUMP;
            correctMove = true;
        } else {
            correctMove = false;
        }
    }
}
