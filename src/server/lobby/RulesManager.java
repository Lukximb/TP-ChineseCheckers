package server.lobby;

import server.board.Coordinates;
import jmx.Player;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class RulesManager extends NotificationBroadcasterSupport implements IRulesManager {
    @Override
    public boolean checkMove(Coordinates currentCoordinates, Coordinates newCoordinates, int pid) {
        sendNotification(new Notification(String.valueOf(pid), this, 001100110011, "####hello player created: " + pid));
        return false;
    }

    @Override
    public Player checkWinner() {
        return null;
    }

    @Override
    public Player checkLooser() {
        return null;
    }
}
