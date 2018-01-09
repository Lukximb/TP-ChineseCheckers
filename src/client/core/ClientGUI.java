package client.core;

import client.logic.ClientConnection;
import client.logic.ClientGUIController;
import client.logic.ClientListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.management.NotificationFilterSupport;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@SuppressWarnings("restriction")
public class ClientGUI extends Application {

    public String domain = null;
    public ObjectName factory = null;
    public ObjectName player = null;
    public ObjectName manager = null;
    public ObjectName lobbyObject = null;
    public int pid = 0;
    public int playerInLobby = 0;
    public int rowOfPawn = 0;
    public int corner = 0;
    public boolean isYourTurn = false;
    public String lobbyName = "";
    public String playerName = "";
    public ClientConnection connection;
    private ClientListener clientListener;
    private NotificationFilterSupport myFilter;
    private ClientGUIController controller;


    public ClientGUI() {
    }

    @Override
    public void init() {
    }

    @Override
    public void stop() {
//        connection.invokeExitFromLobbyMethod(player, "exitFromLobby");
        Object  opParams[] = {playerName, lobbyName};
        String  opSig[] = {String.class.getName(), String.class.getName()};
        connection.invokeMethod(manager, "removePlayerFromLobby", opParams, opSig);
        lobbyName = "";

        Object  opParams1[] = {pid};
        String  opSig1[] = {int.class.getName()};
        connection.invokeMethod(factory, "deletePlayer", opParams1, opSig1);
        try {
            connection.mbsc.unregisterMBean(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.closeConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        pid = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
        connection = new ClientConnection();
        domain = connection.getDomain();

        //Get factory and manager from registry
        factory = new ObjectName(domain+"F" +":type=server.core.Factory,name=Factory");
        manager = new ObjectName(domain+"M" +":type=manager.Manager,name=Manager");

        //Load GUI
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/client/ClientFXML.fxml"));

        controller = new ClientGUIController(this);
        loader.setController(controller);
        StackPane stackPane=loader.load();

        Scene scene = new Scene(stackPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Chinese Checkers");

        //Create notification listener and notification filter
        clientListener = new ClientListener(controller);
        myFilter = new NotificationFilterSupport();

        myFilter.disableAllTypes();

        myFilter.enableType(String.valueOf(pid));

        //Add notification listener
        connection.mbsc.addNotificationListener(factory, clientListener, myFilter, null);
        connection.mbsc.addNotificationListener(manager, clientListener, myFilter, null);

        primaryStage.show();
    }

    public void addNotificationListenerToLobby() {
        try {
            System.out.println("Create notification Listener for lobby: " + lobbyName);
            connection.mbsc.removeNotificationListener(factory, clientListener, myFilter, null);
            connection.mbsc.removeNotificationListener(manager, clientListener, myFilter, null);

            myFilter.enableType(String.valueOf(lobbyName));

            connection.mbsc.addNotificationListener(factory, clientListener, myFilter, null);
            connection.mbsc.addNotificationListener(manager, clientListener, myFilter, null);
            connection.mbsc.addNotificationListener(lobbyObject, clientListener, myFilter, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNotificationListenerToPlayer() {
        if (player != null) {
            try {
                connection.mbsc.addNotificationListener(player, clientListener, myFilter, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeNotificationListenerFromLobby() {
        if (lobbyObject != null) {
            try {
                connection.mbsc.removeNotificationListener(lobbyObject, clientListener, myFilter, null);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
