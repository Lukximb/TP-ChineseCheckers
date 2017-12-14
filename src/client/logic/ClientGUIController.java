package client.logic;

import client.core.ClientGUI;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import server.board.Coordinates;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.ArrayList;

public class ClientGUIController {

//	ClientGUI client;

	private ClientGUI client;
	private Node currentPosition;
	private Node destinationPosition;
	private ArrayList<Node> jumpPositions;
	private ObservableList<String> playersList;


	//LOGIN---------------------------------------
	@FXML
	private StackPane playerNickNamePanel;
	@FXML
	private Button loginButton;
	@FXML
	private TextField nickNameField;

	//MENU----------------------------------------
	@FXML
	private StackPane menu;
	@FXML
	private Button newGameButton;
	@FXML
	private Button exitButton;
	@FXML
	private Button joinGameButton;
	//CREATE LOBBY--------------------------------
	@FXML
	private StackPane createLobby;
	@FXML
	private TextField lobbyNameField;
	@FXML
	private Button player2Button;
	@FXML
	private Button player4Button;
	@FXML
	private Button player6Button;
	@FXML
	private Spinner boardSizeSpinner;
	@FXML
	private Button createLobbyButton;
	@FXML
	private Button cancelCreateLobbyButton;
	//JOIN LOBBY----------------------------------
	@FXML
	private StackPane joinLobby;
	@FXML
	private Button cancelJoinLobbyButton;
	@FXML
	private Button joinLobbyButton;	
	//LOBBY---------------------------------------
	@FXML
	private StackPane lobby;
	@FXML
	private TableView<String> playersInLobbyList;
	@FXML
	private TableColumn<String, String> playersInLobbyColumn;
	@FXML
	private TextField invitePlayerField;
	@FXML
	private Button addBotButton;
	@FXML
	private Button removePlayerButton;
	@FXML
	private Button addPlayerButton;
	@FXML
	private Button easyBotButton;
	@FXML
	private Button mediumBotButton;
	@FXML
	private Button hardBotButton;
	@FXML
	private Button readyButton;
	@FXML
	private Button exitLobbyButton;
	//GAME----------------------------------------
	@FXML
	private StackPane game;
	@FXML
    private GridPane board;
	@FXML
	private ProgressBar turnTimeBar;
	@FXML
	private Button moveButton;
	@FXML
	private Button passButton;
	@FXML
	private Button surrenderButton;
	@FXML
	private Button sendMsgButton;
	
	
	public ClientGUIController(ClientGUI client) {
		this.client = client;
	}
	
	@FXML
	void initialize() {

//        board.setStyle("-fx-background-image: url('/client/background.jpg');");

		game.setStyle("-fx-background-image: url('/client/wood.jpg');");
		jumpPositions = new ArrayList<>();

		playersList = FXCollections.observableArrayList();
		playersInLobbyColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
		playersInLobbyList.setItems(playersList);
	}

	//LOGIN
	public void loginButtonOnClick(ActionEvent exent) {
		this.playerNickNamePanel.setVisible(false);
		this.playerNickNamePanel.setDisable(true);

		createPlayer();

		this.menu.setVisible(true);
		this.menu.setDisable(false);
	}
	
	//MENU
	public void newGameButtonOnClick(ActionEvent exent) {
		this.menu.setVisible(false);
		this.menu.setDisable(true);

		this.createLobby.setVisible(true);
		this.createLobby.setDisable(false);
	}
	
	public void exitButtonOnClick(ActionEvent event) {
		Platform.exit();
	}
	
	public void joinGameButtonOnClick(ActionEvent event) {
		this.menu.setVisible(false);
		this.menu.setDisable(true);

		this.joinLobby.setVisible(true);
		this.joinLobby.setDisable(false);
		
	}
	//CREATE LOBBY
	public void createLobbyButtonOnClick(ActionEvent event) {		
		this.createLobby.setVisible(false);
		this.createLobby.setDisable(true);

		client.lobbyName = lobbyNameField.getText();
		client.rowForPlayerPawn = (int)boardSizeSpinner.getValue();
		System.out.println(client.lobbyName);
		System.out.println(client.rowForPlayerPawn);
		client.connection.invokeCreateLobbyMethod(client.factory, "createLobby", client.playerInLobby , client.rowForPlayerPawn, client.lobbyName, client.pid);

		playersList.clear();
		playersList.add(client.playerName);

		this.lobby.setVisible(true);
		this.lobby.setDisable(false);
	}
	
	public void cancelCreateLobbyButtonOnClick(ActionEvent event) {
		this.createLobby.setVisible(false);
		this.createLobby.setDisable(true);
		
		this.menu.setVisible(true);
		this.menu.setDisable(false);
	}
	
	//JOIN LOBBY
	public void joinLobbyButtonOnClick(ActionEvent event) {
		this.joinLobby.setVisible(false);
		this.joinLobby.setDisable(true);
		
		this.lobby.setVisible(true);
		this.lobby.setDisable(false);
	}
	
	public void cancelJoinLobbyButtonOnClick(ActionEvent event) {
		this.joinLobby.setVisible(false);
		this.joinLobby.setDisable(true);
		
		this.menu.setVisible(true);
		this.menu.setDisable(false);
	}
	
	//LOBBY
	public void readyButtonOnClick(ActionEvent event) {
		this.lobby.setVisible(false);
		this.lobby.setDisable(true);
		
		this.game.setVisible(true);
		this.game.setDisable(false);
	}
	
	public void exitLobbyButtonOnClick(ActionEvent event) {
		this.lobby.setVisible(false);
		this.lobby.setDisable(true);
		
		this.menu.setVisible(true);
		this.menu.setDisable(false);
	}

	public void addPlayerButtonOnClick(ActionEvent event) {
		String playerName = invitePlayerField.getText();

		client.connection.invokeAddPlayerToLobbyMethod(client.manager, "addPlayerToLobby", client.lobbyName, playerName);
		System.out.println("Add player: " + playerName + " to lobby: " + client.lobbyName);
		client.connection.invokeSendPlayersInLobbyList(client.manager, "sendPlayersInLobbyList", client.playerName);
	}

	public void removePlayerButtonOnClick(ActionEvent event) {
		String playerName = invitePlayerField.getText();

		client.connection.invokeRemovePlayerToLobbyMethod(client.manager, "removePlayerFromLobby", client.lobbyName, playerName);
		System.out.println("Remove player: " + playerName + " from lobby: " + client.lobbyName);
		client.connection.invokeSendPlayersInLobbyList(client.manager, "sendPlayersInLobbyList", client.playerName);
	}
	
	//GAME
	public void surrenderButtonOnClick(ActionEvent event) {
		this.game.setVisible(false);
		this.game.setDisable(true);
		
		this.menu.setVisible(true);
		this.menu.setDisable(false);
	}
	
    public void fieldsHandleTest(MouseEvent event) {
		System.out.println("mouse click detected! "+ event.getSource());
    }

    public void boardClick(MouseEvent event) {
		if (event.getButton() == MouseButton.SECONDARY) {
			System.out.println("Right button clicked");
		}
		System.out.println("Row: " + GridPane.getRowIndex((Node)event.getTarget())
				+ "\nColumn: " + GridPane.getColumnIndex((Node)event.getTarget()));
	}

	public void createPlayer() {
		if (client.player == null) {
			client.connection.invokeCreatePlayerMethod(client.factory, "createPlayer", client.pid, nickNameField.getText());
			try {
				client.player = new ObjectName(client.domain+ client.pid +":type=jmx.Player,name=Player" + client.pid);
				client.playerName = nickNameField.getText();
			} catch (MalformedObjectNameException e) {
				e.printStackTrace();
			}
		}
	}

	public void setPlayerNumberOn2ButtonOnClick(ActionEvent event) {
		client.playerInLobby = 2;
	}

	public void setPlayerNumberOn4ButtonOnClick(ActionEvent event) {
		client.playerInLobby = 4;
	}

	public void setPlayerNumberOn6ButtonOnClick(ActionEvent event) {
		client.playerInLobby = 6;
	}


	public void doMoveOnClick(ActionEvent event) {
		Image img = new Image("/client/pawnBlack.png");
		if (currentPosition != null && destinationPosition != null) {
			if (jumpPositions.isEmpty()) {
				Coordinates cCoordinates = new Coordinates(board.getColumnIndex(currentPosition), board.getRowIndex(currentPosition));
				Coordinates dCoordinates = new Coordinates(board.getColumnIndex(destinationPosition), board.getRowIndex(destinationPosition));
				client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

				Circle circleC = (Circle)currentPosition;
				circleC.setStrokeWidth(1);
				circleC.setStroke(Color.BLACK);
				circleC.setFill(Color.WHITE);
				currentPosition = null;

				Circle circleD = (Circle)destinationPosition;
				circleD.setStrokeWidth(1);
				circleD.setFill(Color.TRANSPARENT);
				circleD.setFill(new ImagePattern(img));
				circleD.setStroke(Color.BLACK);
				destinationPosition = null;
			} else {
				//Current --> 1st jump
				Coordinates cCoordinates = new Coordinates(board.getColumnIndex(currentPosition), board.getRowIndex(currentPosition));
				Coordinates dCoordinates = new Coordinates(board.getColumnIndex(jumpPositions.get(0)), board.getRowIndex(jumpPositions.get(0)));
				client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

				Circle circleC = (Circle)currentPosition;
				circleC.setStrokeWidth(1);
				circleC.setStroke(Color.BLACK);
				circleC.setFill(Color.WHITE);
				currentPosition = null;

				Circle circleJ = (Circle)jumpPositions.get(0);
				circleJ.setStrokeWidth(1);
				circleJ.setFill(Color.TRANSPARENT);
				circleJ.setFill(new ImagePattern(img));
				circleJ.setStroke(Color.BLACK);

				for (int i = 1; i < jumpPositions.size(); i++) {
					Circle circleJ1 = (Circle)jumpPositions.get(i-1);
					circleJ1.setStrokeWidth(1);
					circleJ1.setFill(Color.TRANSPARENT);
					circleJ1.setFill(new ImagePattern(img));
					circleJ1.setStroke(Color.BLACK);

					cCoordinates = new Coordinates(board.getColumnIndex(jumpPositions.get(i-1)), board.getRowIndex(jumpPositions.get(i-1)));
					dCoordinates = new Coordinates(board.getColumnIndex(jumpPositions.get(i)), board.getRowIndex(jumpPositions.get(i)));
					client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

					Circle circleJ2 = (Circle)jumpPositions.get(i);
					circleJ2.setStrokeWidth(1);
					circleJ2.setFill(Color.TRANSPARENT);
					circleJ2.setFill(new ImagePattern(img));
					circleJ2.setStroke(Color.BLACK);
				}

				Circle circleJL = (Circle)jumpPositions.get(jumpPositions.size()-1);
				circleJL.setStrokeWidth(1);
				circleJL.setFill(Color.TRANSPARENT);
				circleJL.setFill(new ImagePattern(img));
				circleJL.setStroke(Color.BLACK);

				cCoordinates = new Coordinates(board.getColumnIndex(jumpPositions.get(jumpPositions.size()-1)), board.getRowIndex(jumpPositions.get(jumpPositions.size()-1)));
				dCoordinates = new Coordinates(board.getColumnIndex(destinationPosition), board.getRowIndex(destinationPosition));
				client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

				jumpPositions.clear();

				Circle circleD = (Circle)destinationPosition;
				circleD.setStrokeWidth(1);
				circleD.setFill(Color.TRANSPARENT);
				circleD.setFill(new ImagePattern(img));
				circleD.setStroke(Color.BLACK);
				destinationPosition = null;
			}
		}
	}

	//TEST
	public void submitButtonOnClick(ActionEvent event) {
	}

	public void chooseCircleOnClick(MouseEvent event) {
		ObservableList<Node> childrens = board.getChildren();
		for (Node node : childrens) {
			if(board.getRowIndex(node) == GridPane.getRowIndex((Node)event.getTarget()) &&
					board.getColumnIndex(node) == GridPane.getColumnIndex((Node)event.getTarget())) {
				if (currentPosition == null && node != destinationPosition) {
					Circle circle = (Circle)node;
					circle.setStrokeWidth(4);
					circle.setStroke(Color.GREEN);
					currentPosition = node;
				} else if (node == currentPosition) {
					Circle circle = (Circle)node;
					circle.setStrokeWidth(1);
					circle.setStroke(Color.BLACK);
					currentPosition = null;
				} else if (destinationPosition == null && event.getButton() == MouseButton.SECONDARY) {
					Circle circle = (Circle)node;
					circle.setStrokeWidth(4);
					circle.setStroke(Color.RED);
					destinationPosition = node;
				} else if (node == destinationPosition && event.getButton() == MouseButton.SECONDARY) {
					Circle circle = (Circle)node;
					circle.setStrokeWidth(1);
					circle.setStroke(Color.BLACK);
					destinationPosition = null;
				} else if (node != currentPosition && node != destinationPosition && !jumpPositions.contains(node)) {
					Circle circle = (Circle)node;
					circle.setStrokeWidth(4);
					circle.setStroke(Color.BLUE);
					jumpPositions.add(node);
				} else if(node != currentPosition && node != destinationPosition && jumpPositions.contains(node)) {
					Circle circle = (Circle)node;
					circle.setStrokeWidth(1);
					circle.setStroke(Color.BLACK);
					jumpPositions.remove(node);
				}
				break;
			}
		}
	}

	public void updatePlayersList(String[] list) {
		playersList.clear();
		for(String s: list) {
			playersList.add(s);
		}
		playersInLobbyList.setItems(playersList);
	}

	public void updateLobbyList(String[] list) {

	}

}
