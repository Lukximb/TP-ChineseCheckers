package client.logic;

import client.core.ClientGUI;
import javafx.event.ActionEvent;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class PlayerLogic {
    private ClientGUIController controller;
    private ClientGUI client;

    private String invitedPlayerName;

    public PlayerLogic(ClientGUIController controller, ClientGUI client) {
        this.controller = controller;
        this.client = client;
    }

    public void addPlayerButtonOnClick(ActionEvent event) {
        invitedPlayerName = controller.invitePlayerField.getText();
        Object  opParams[] = {client.lobbyName, controller.client.playerName, invitedPlayerName};
        String  opSig[] = {String.class.getName(), String.class.getName(), String.class.getName()};
        client.connection.invokeMethod(client.manager,
                "InvitePlayer", opParams, opSig);
    }

    public void removePlayerButtonOnClick(ActionEvent event) {
        String playerName = controller.invitePlayerField.getText();

        Object  opParams[] = {client.lobbyName, playerName};
        String  opSig[] = {String.class.getName(),String.class.getName()};
        client.connection.invokeMethod(client.manager,
                "removePlayerFromLobby", opParams, opSig);
        System.out.println("Remove player: " + playerName + " from lobby: " + client.lobbyName);
        Object  opParams1[] = {client.playerName};
        String  opSig1[] = {String.class.getName()};
        client.connection.invokeMethod(client.manager, "sendPlayersInLobbyList", opParams1, opSig1);
    }

    public void createPlayer() {
        if (client.player == null) {
            Object  opParams[] = {client.pid, controller.nickNameField.getText()};
            String  opSig[] = {int.class.getName(), String.class.getName()};
            client.connection.invokeMethod(client.factory,
                    "createPlayer", opParams, opSig);
            try {
                client.player = new ObjectName(client.domain+ client.pid +":type=server.player.Player,name=Player" + client.pid);
                client.addNotificationListenerToPlayer();
                client.playerName = controller.nickNameField.getText();
            } catch (MalformedObjectNameException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPlayerToLobby() {
        Object  opParams[] = {client.lobbyName, client.playerName};
        String  opSig[] = {String.class.getName(), String.class.getName()};
        client.connection.invokeMethod(client.manager,
                "addPlayerToLobby", opParams, opSig);
        System.out.println("Add player: " + invitedPlayerName + " to lobby: " + client.lobbyName);
    }
}
