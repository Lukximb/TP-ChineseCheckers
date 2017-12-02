package server;

import java.io.IOException;

import server.logic.Connection;

public class Server {
    private Connection connection = null;

    public Server() {
        connection = new Connection();
        connection.createRegistry();
        connection.createMBeanServer();
        connection.createDomain();
        connection.createMBeanObject("jmx.Hello", "h1", 1/*PID*/);
        connection.createConnectorServer();
        connection.registryMBeanObject("h1");

        System.out.println("server is running...");
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