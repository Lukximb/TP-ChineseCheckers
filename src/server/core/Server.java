package server.core;

import java.io.IOException;

import jmx.Factory;
import server.connection.Connection;
import server.manager.Manager;

public class Server {
    private Connection connection = null;
    public Manager manager = null;
    public Factory factory = null;

    public Server() {
        getFactory();
        getConnection();
        getManager();

        connection.createConnectorServer();

        connection.createMBeanMainObject("jmx.Factory", "Factory", "F", factory);
        connection.registryFactoryMBeanObject(factory);

        System.out.println(">> server is running...");
    }

    private void getFactory() {
        factory = Factory.getInstance();
    }

    private void getConnection() {
        connection = factory.createConnection();
    }

    private void getManager() {
        manager = factory.createManager();
    }

    public static void main(String argv[]) {
        new Server();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}