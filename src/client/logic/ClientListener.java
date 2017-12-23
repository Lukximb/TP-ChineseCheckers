package client.logic;

import javax.management.Notification;
import javax.management.NotificationListener;

public class ClientListener implements NotificationListener {
    private ClientGUIController controller;
    private String move;


    public ClientListener(ClientGUIController controller) {
        this.controller = controller;
        controller.setListener(this);
    }

    public void handleNotification(Notification notification, Object handback)
    {
        System.out.println("\nReceived notification:  \n    message: " + notification.getMessage()
                + "\n    type: " + notification.getType());

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
                if(args[1].equals("2")) {
                    controller.board4.setVisible(false);
                    controller.board4.setDisable(true);
                    controller.board2.setVisible(true);
                    controller.board2.setDisable(false);
                    controller.board = controller.board2;
                } else if(args[1].equals("3")) {
                    controller.board4.setVisible(false);
                    controller.board4.setDisable(true);
                    controller.board3.setVisible(true);
                    controller.board3.setDisable(false);
                    controller.board = controller.board3;
                } else if(args[1].equals("4")) {
                    controller.board4.setVisible(true);
                    controller.board4.setDisable(false);
                    controller.board = controller.board4;
                } else {
                    controller.board = controller.board4;
                }
                break;
            default:
        }
    }

    private void receivePlayersNames(String message) {
        System.out.println(message);
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
        }
    }

    public void setMove(String move) {
        this.move = move;
    }
}
