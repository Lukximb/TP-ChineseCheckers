//package server.lobby;
//
//public class Clock implements Runnable{
//    private long startTime;
//    private long currentTime;
//    private long currentRoundTime;
//    private LobbyMediator mediator;
//
//    @Override
//    public synchronized void run() {
//        System.out.println("Clock created\n");
//        while(true) {
//            try {
//                //usypiamy wątek na 100 milisekund
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if(checkTime() == false) {
//                endRound();
//                mediator.endOfTime();
//            }
//        }
//    }
//
//    public synchronized long getRoundTime() {
//        currentTime = System.currentTimeMillis();
//        currentRoundTime = (currentTime - startTime) / 1000;
//        return currentRoundTime;
//    }
//
//
//    public synchronized boolean checkTime() {
//        if(getRoundTime() >= 30) {
//            return false;
//        }
//        return true;
//    }
//
//
//    public synchronized void startRound() {
//        startTime = System.currentTimeMillis();
//    }
//
//    public synchronized void endRound() {
//        startTime = 0;
//        currentTime = 0;
//        currentRoundTime = 0;
//    }
//
//    public void setMediator(LobbyMediator mediator) {
//        this.mediator = mediator;
//    }
//}
