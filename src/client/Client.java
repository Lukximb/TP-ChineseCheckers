package client;

import java.net.*;
import java.rmi.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import rmi.ITest;

public class Client{
	ITest p;
	
	public Client() {
		ClientGUI gui = new ClientGUI(this);
		try { 
			//int pid = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);   
			p = (ITest)Naming.lookup("print"); 
		}
		catch( NotBoundException ex )
		{ ex.printStackTrace(); }
		catch( RemoteException e )
		{ e.printStackTrace(); }
		catch( MalformedURLException e )
		{ e.printStackTrace(); }
	}
	
	public static void main(String[] args) throws IOException {
		new Client();
	}
}