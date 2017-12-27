package server.core;

import server.connection.Connection;
import server.manager.Manager;

import java.io.IOException;

public class Server {
    public Connection connection = null;
    public Manager manager = null;
    public Factory factory = null;

    public Server() {
        getFactory();
        getConnection();
        getManager();

        connection.createConnectorServer();

        connection.createMBeanMainObject("server.core.Factory", "Factory", "F", factory);
        connection.createMBeanMainObject("manager.Manager", "Manager", "M", manager);

        System.out.println(">> server is running...");
    }

    private void getFactory() {
        factory = Factory.getInstance(this);
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