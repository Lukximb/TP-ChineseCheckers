package client.logic;

import javax.management.Notification;
import javax.management.NotificationListener;

public class ClientListener implements NotificationListener {
    ClientGUIController controller;

    public ClientListener(ClientGUIController controller) {
        this.controller = controller;
    }

    public void handleNotification(Notification notification, Object handback)
    {
        System.out.println("\nReceived notification:  \n    message: " + notification.getMessage()
            + "\n    type: " + notification.getType()
            + "\n    timestamp: " + notification.getTimeStamp());

        switch (notification.getMessage().charAt(0)) {
            case('P'):
                recivePlayersNames(notification.getMessage().substring(2));
                break;
            case('W'):
                reciveLobbyNames(notification.getMessage().substring(2));
                break;
        }
    }

    public void recivePlayersNames(String message) {
        String[] newMessage = message.split(",");
        controller.updatePlayersList(newMessage);
    }

    public void reciveLobbyNames(String message) {
        String[] newMessage = message.split(",");
        controller.updateLobbyList(newMessage);
    }
}
