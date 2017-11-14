package server;

import java.rmi.registry.*;
import logic.*;

public class Server {
    public static void main( String[] args ) {
        try { 
            Registry registry = LocateRegistry.createRegistry(1099);
            PrintTest p = new PrintTest();
            registry.bind("print", p); 
        }
        catch( Exception e ){ 
            e.printStackTrace(); 
        }
    }
}