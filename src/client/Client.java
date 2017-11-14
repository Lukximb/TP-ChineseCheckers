package client;

import java.net.*;
import java.rmi.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import rmi.ITest;

public class Client{
    public static void main(String[] args) throws IOException { 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String: \n Enter 'close' to exit \n");
        while (true) {
        	String s = br.readLine();
        	rmi(s);
        	if (s.equals("close")) {
        		break;
        	}
        } 
    }

    private static void rmi(String s) throws RemoteException {
        try { 
                //int pid = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);   

                ITest p = (ITest)Naming.lookup( "print" ); 
                p.printStr(s);
            }
            catch( NotBoundException ex )
            { ex.printStackTrace(); }
            catch( RemoteException e )
            { e.printStackTrace(); }
            catch( MalformedURLException e )
            { e.printStackTrace(); }
    }
}