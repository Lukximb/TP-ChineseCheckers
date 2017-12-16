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

        switch (notification.getMessage().charAt(0)) {
            case('P')://player
                System.out.println("\nReceived notification:  \n    message: " + notification.getMessage()
                        + "\n    type: " + notification.getType());
                receivePlayersNames(notification.getMessage().substring(2));
                break;
            case('W')://waiting
                System.out.println("\nReceived notification:  \n    message: " + notification.getMessage()
                        + "\n    type: " + notification.getType());
                receiveLobbyNames(notification.getMessage().substring(2));
                break;
            case('R')://RulesManager
                handleRulesManagerResponse(notification.getMessage().substring(2));
                break;
            default:
                System.out.println("\nReceived notification:  \n    message: " + notification.getMessage()
                        + "\n    type: " + notification.getType());
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
