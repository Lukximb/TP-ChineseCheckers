package client.logic;


import server.board.Coordinates;
import server.player.Difficult;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class ClientConnection {
    JMXServiceURL url = null;
    JMXConnector jmxc = null;
    String domain = null;
    public MBeanServerConnection mbsc = null;

    public ClientConnection() {
        try {
//            url = new JMXServiceURL("service:jmx:rmi://25.0.246.243:44445/jndi/rmi://25.0.246.243:44444/jmxrmi");
            url = new JMXServiceURL("service:jmx:rmi://25.71.242.160:44445/jndi/rmi://25.71.242.160:44444/jmxrmi");
            jmxc = JMXConnectorFactory.connect(url, null);
            mbsc = jmxc.getMBeanServerConnection();
            domain = mbsc.getDefaultDomain();
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Open connection...");
    }

    public String getDomain(){
        return domain;
    }

    public void createNewMBean(ObjectName mBeanName) {
        try {
            mbsc.createMBean("jmx.Hello", mBeanName, null, null);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println("Create new MBean: " + mBeanName.toString());
    }

    //========================== INVOKE METHOD TEMPLATE ================================

    public void invokeMethod(ObjectName mBeanName, String methodName, String arg) {
        Object  opParams[] = {arg};
        String  opSig[] = {String.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void invokeMethod(ObjectName mBeanName, String methodName, int num) {
        Object  opParams[] = {num};
        String  opSig[] = {int.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    //========================== END OF INVOKE METHOD TEMPLATE ================================

    //================================== INVOKE METHOD ========================================

    public void invokeCreateLobbyMethod(ObjectName mBeanName, String methodName, int playerNum, int rowNumber, String lobbyName, int adminPid) {
        Object opParams [] = {playerNum, rowNumber, lobbyName, adminPid};
        String  opSig[] = {int.class.getName(), int.class.getName(), String.class.getName(), int.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void invokeCreatePlayerMethod(ObjectName mBeanName, String methodName, int pid, String name) {
        Object  opParams[] = {pid, name};
        String  opSig[] = {int.class.getName(), String.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void invokeMovePlayerMethod(ObjectName mBeanName, String methodName, Coordinates currentCoordinates, Coordinates destinationCoordinates) {
        Object  opParams[] = {currentCoordinates, destinationCoordinates};
        String  opSig[] = {Coordinates.class.getName(), Coordinates.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        //System.out.println(methodName);
    }

    public void invokeInvitePlayerToLobbyMethod(ObjectName mBeanName, String methodName, String lobbyName,
                                                String playerName, String invitedPlayerName) {
        Object  opParams[] = {lobbyName, playerName, invitedPlayerName};
        String  opSig[] = {String.class.getName(), String.class.getName(), String.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void invokeAddPlayerToLobbyMethod(ObjectName mBeanName, String methodName, String lobbyName, String playerName) {
        Object  opParams[] = {lobbyName, playerName};
        String  opSig[] = {String.class.getName(), String.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void invokeRemovePlayerToLobbyMethod(ObjectName mBeanName, String methodName, String lobbyName, String playerName) {
        Object  opParams[] = {lobbyName, playerName};
        String  opSig[] = {String.class.getName(), String.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void invokeSendPlayersInLobbyList(ObjectName mBeanName, String methodName, String playerName) {
        Object  opParams[] = {playerName};
        String  opSig[] = {String.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void invokeSendWaitingLobbyList(ObjectName mBeanName, String methodName, String playerName) {
        Object  opParams[] = {playerName};
        String  opSig[] = {String.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void invokeCheckMoveMethod(ObjectName mBeanName, String methodName, Coordinates currentCoordinates,
                                      Coordinates destinationCoordinates, MoveType moveType) {
        Object  opParams[] = {currentCoordinates, destinationCoordinates, moveType};
        String  opSig[] = {Coordinates.class.getName(), Coordinates.class.getName(), MoveType.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        //System.out.println(methodName);
    }

    public void invokeStartGameMethod(ObjectName mBeanName, String methodName, String lobbyName) {
        Object  opParams[] = {lobbyName};
        String  opSig[] = {String.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void invokeAddBotMethod(ObjectName mBeanName, String methodName, Difficult botDifficult) {
        Object  opParams[] = {botDifficult};
        String  opSig[] = {Difficult.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void invokePassMethod(ObjectName mBeanName, String methodName) {
        Object  opParams[] = null;
        String  opSig[] = null;
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void invokeSendMessageMethod(ObjectName mBeanName, String methodName, String message) {
        Object  opParams[] = {message};
        String  opSig[] = {String.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void invokeNextRoundMethod(ObjectName mBeanName, String methodName) {
        Object  opParams[] = null;
        String  opSig[] = null;
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void invokeExitFromLobbyMethod(ObjectName mBeanName, String methodName) {
        Object  opParams[] = null;
        String  opSig[] = null;
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void closeConnection() {
        try {
            jmxc.close();
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println("Close connection...");
    }
}
