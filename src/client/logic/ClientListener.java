package client.logic;

import javax.management.Notification;
import javax.management.NotificationListener;

public class ClientListener implements NotificationListener {
    private ClientGUIController controller;
    private String move;
    private int corner;


    public ClientListener(ClientGUIController controller) {
        this.controller = controller;
        controller.setListener(this);
        corner = -1;
    }

    public void handleNotification(Notification notification, Object handback)
    {
        System.out.println("Receive notification: " + notification.getMessage());
//        System.out.println("Notifiaction: " + notification.getMessage().charAt(0) + " " + notification.getMessage().charAt(2));
        switch (notification.getMessage().charAt(0)) {
            case('P')://player
                receivePlayersNames(notification.getMessage().substring(2));
                break;
            case('W'):
                receiveLobbyNames(notification.getMessage().substring(2));
                break;
            case('R')://RulesManager
                handleRulesManagerResponse(notification.getMessage().substring(2));
                break;
            case('I')://InvitePopUp
                String[] newMessage = notification.getMessage().substring(2).split(",");
                controller.showInvitation(newMessage);
                break;
            case('A')://Accept Invitation
                String[] acceptation = notification.getMessage().substring(2).split(",");
                controller.client.connection.invokeSendPlayersInLobbyList(controller.client.manager,
                        "sendPlayersInLobbyList", controller.client.playerName);
                break;
            case('S')://Start Game
                String[] args = notification.getMessage().substring(2).split(",");
                hendleStartGame(args);
                break;
            case('C')://Corner
                int c = Integer.parseInt(notification.getMessage().substring(2));
                controller.boardUpdate.setCorner(c);
                this.corner = c;
                break;
            case ('E')://Executed other player move
                String[] arg = notification.getMessage().substring(2).split(",");
                int cor = Integer.parseInt(arg[0]);
                if (cor != corner) {
                    int cX = Integer.parseInt(arg[1]);
                    int cY = Integer.parseInt(arg[2]);
                    int dX = Integer.parseInt(arg[3]);
                    int dY = Integer.parseInt(arg[4]);
//                    System.out.println("moveEnemyPawn: " + cor + " " + cX + " " + cY + " " + dX + " " + dY);
                    controller.boardUpdate.moveEnemyPawn(cor, cX, cY, dX, dY);
                }
                break;
            case('T')://is player's turn
                String[] turn = notification.getMessage().substring(2).split(",");
                if(Integer.parseInt(turn[0]) == corner) {
                    controller.client.isYourTurn = true;
                    controller.showTurnLabel(controller.client.isYourTurn);
                }
                else {
                    controller.client.isYourTurn = false;
                    controller.showTurnLabel(controller.client.isYourTurn);
                }
                break;
            case('M')://Message
                String[] message = notification.getMessage().substring(2).split("#");
                String line = message[0] + ": " + message[1];
                controller.addMessage(line);
                break;
            default:
        }
    }

    private void receivePlayersNames(String message) {
        String[] newMessage = message.split(",");
        controller.updatePlayersList(newMessage);
    }

    private void receiveLobbyNames(String message) {
        String[] newMessage = message.split(",");
        controller.updateLobbyList(newMessage);
    }

    private void handleRulesManagerResponse(String response) {
        if (response.equals("CorrectMove")) {
            if (move.equals("jump")) {
                controller.setJumpNode();
            } else if (move.equals("end")) {
                controller.setDestinationNode();
            }
        }else if (response.equals("IncorrectMove")) {
            controller.boardUpdate.setMoveTypeAsEmpty();
        }
    }

    private void hendleStartGame(String[] args) {
        int rowNumber = Integer.parseInt(args[1]);
        int numberOfPlayers = Integer.parseInt(args[2]);
        if(args[1].equals("2")) {
            controller.lobby.setVisible(false);
            controller.lobby.setDisable(true);
            controller.game.setVisible(true);
            controller.game.setDisable(false);
            controller.board4.setVisible(false);
            controller.board4.setDisable(true);
            controller.board2.setVisible(true);
            controller.board2.setDisable(false);
            controller.board = controller.board2;
            controller.boardUpdate.createPawnList(rowNumber, numberOfPlayers);
        } else if(args[1].equals("3")) {
            controller.lobby.setVisible(false);
            controller.lobby.setDisable(true);
            controller.game.setVisible(true);
            controller.game.setDisable(false);
            controller.board4.setVisible(false);
            controller.board4.setDisable(true);
            controller.board3.setVisible(true);
            controller.board3.setDisable(false);
            controller.board = controller.board3;
            controller.boardUpdate.createPawnList(rowNumber, numberOfPlayers);
        } else if(args[1].equals("4")) {
            controller.lobby.setVisible(false);
            controller.lobby.setDisable(true);
            controller.game.setVisible(true);
            controller.game.setDisable(false);
            controller.board4.setVisible(true);
            controller.board4.setDisable(false);
            controller.board = controller.board4;
            controller.boardUpdate.createPawnList(rowNumber, numberOfPlayers);
        } else {
            controller.board = controller.board4;
            controller.boardUpdate.createPawnList(rowNumber, numberOfPlayers);
        }
    }

    public void setMove(String move) {
        this.move = move;
    }

    public void setCorner(int corner) {
        this.corner = corner;
    }
}
