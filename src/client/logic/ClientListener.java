package client.logic;

import javax.management.Notification;
import javax.management.NotificationListener;

public class ClientListener implements NotificationListener {
    private ClientGUIController controller;

    public void handleNotification(Notification notification, Object handback)
    {
        System.out.println("\nReceived notification:  \n    message: " + notification.getMessage()
            + "\n    type: " + notification.getType()
            + "\n    timestamp: " + notification.getTimeStamp());
    }

    public void setGUIController(ClientGUIController controller){
        this.controller = controller;
    }
}
