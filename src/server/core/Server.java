package server.core;

import java.io.IOException;

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

        connection.createMBeanObject("jmx.Hello", "h1", 1/*PID*/);
        connection.createConnectorServer();
        connection.registryMBeanObject("h1");

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