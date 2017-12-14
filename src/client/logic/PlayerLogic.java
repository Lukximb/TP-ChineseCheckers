package client.logic;

import client.core.ClientGUI;
import javafx.event.ActionEvent;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class PlayerLogic {
    private ClientGUIController controller;
    private ClientGUI client;

    public PlayerLogic(ClientGUIController controller, ClientGUI client) {
        this.controller = controller;
        this.client = client;
    }

    public void addPlayerButtonOnClick(ActionEvent event) {
        String playerName = controller.invitePlayerField.getText();

        client.connection.invokeAddPlayerToLobbyMethod(client.manager, "addPlayerToLobby", client.lobbyName, playerName);
        System.out.println("Add player: " + playerName + " to lobby: " + client.lobbyName);
        client.connection.invokeSendPlayersInLobbyList(client.manager, "sendPlayersInLobbyList", client.playerName);
    }

    public void removePlayerButtonOnClick(ActionEvent event) {
        String playerName = controller.invitePlayerField.getText();

        client.connection.invokeRemovePlayerToLobbyMethod(client.manager, "removePlayerFromLobby", client.lobbyName, playerName);
        System.out.println("Remove player: " + playerName + " from lobby: " + client.lobbyName);
        client.connection.invokeSendPlayersInLobbyList(client.manager, "sendPlayersInLobbyList", client.playerName);
    }

    public void createPlayer() {
        if (client.player == null) {
            client.connection.invokeCreatePlayerMethod(client.factory, "createPlayer", client.pid, controller.nickNameField.getText());
            try {
                client.player = new ObjectName(client.domain+ client.pid +":type=jmx.Player,name=Player" + client.pid);
                client.addNotificationListenerToPlayer();
                client.playerName = controller.nickNameField.getText();
            } catch (MalformedObjectNameException e) {
                e.printStackTrace();
            }
        }
    }
}
