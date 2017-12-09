package server.lobby;

public class Clock {
    private long startTime;
    private long currentTime;
    private long currentRoundTime;

    public long getRoundTime() {
        currentTime = System.currentTimeMillis();
        currentRoundTime = (currentTime - startTime) / 1000;
        return currentRoundTime;
    }

    public void startRound() {
        startTime = System.currentTimeMillis();
    }

    public void endRound() {
        startTime = 0;
        currentTime = 0;
        currentRoundTime = 0;
    }
}
