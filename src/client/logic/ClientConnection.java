package client.logic;


import server.board.Coordinates;

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
            url = new JMXServiceURL("service:jmx:rmi://25.0.246.243:44445/jndi/rmi://25.0.246.243:44444/jmxrmi");
//            url = new JMXServiceURL("service:jmx:rmi://25.71.242.160:44445/jndi/rmi://25.71.242.160:44444/jmxrmi");
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

    public void invokeMethod(ObjectName mBeanName, String methodName, String arg) {
        Object  opParams[] = {arg};
        String  opSig[] = {String.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println("Method invoked: " + methodName + " on: " + mBeanName.toString());
    }

    public void invokeMethod(ObjectName mBeanName, String methodName, int num) {
        Object  opParams[] = {num};
        String  opSig[] = {int.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println("Method invoked: " + methodName + " on: " + mBeanName.toString());
    }

    public void invokeCreateLobbyMethod(ObjectName mBeanName, String methodName, int playerNum, int rowNumber, String lobbyName, int adminPid) {
        Object opParams [] = {playerNum, rowNumber, lobbyName, adminPid};
        String  opSig[] = {int.class.getName(), int.class.getName(), String.class.getName(), int.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println("Method invoked: " + methodName + " on: " + mBeanName.toString());
    }

    public void invokeCreatePlayerMethod(ObjectName mBeanName, String methodName, int pid, String name) {
        Object  opParams[] = {pid, name};
        String  opSig[] = {int.class.getName(), String.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println("Method invoked: " + methodName + " on: " + mBeanName.toString());
    }

    public void invokeMovePlayerMethod(ObjectName mBeanName, String methodName, Coordinates currentCoordinates, Coordinates destinationCoordinates) {
        Object  opParams[] = {currentCoordinates, destinationCoordinates};
        String  opSig[] = {Coordinates.class.getName(), Coordinates.class.getName()};
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println("Method invoked: " + methodName + " on: " + mBeanName.toString());
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
