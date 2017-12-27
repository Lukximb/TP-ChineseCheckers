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
        client.connection.invokeInvitePlayerToLobbyMethod(client.manager,
                "InvitePlayer", client.lobbyName, controller.client.playerName, invitedPlayerName);
    }

    public void removePlayerButtonOnClick(ActionEvent event) {
        String playerName = controller.invitePlayerField.getText();

        client.connection.invokeRemovePlayerToLobbyMethod(client.manager,
                "removePlayerFromLobby", client.lobbyName, playerName);
        System.out.println("Remove player: " + playerName + " from lobby: " + client.lobbyName);
        client.connection.invokeSendPlayersInLobbyList(client.manager, "sendPlayersInLobbyList", client.playerName);
    }

    public void createPlayer() {
        if (client.player == null) {
            client.connection.invokeCreatePlayerMethod(client.factory,
                    "createPlayer", client.pid, controller.nickNameField.getText());
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
        client.connection.invokeAddPlayerToLobbyMethod(client.manager,
                "addPlayerToLobby", client.lobbyName, client.playerName);
        System.out.println("Add player: " + invitedPlayerName + " to lobby: " + client.lobbyName);
    }
}
