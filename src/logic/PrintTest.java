package logic;

import java.rmi.*;
import java.rmi.server.*;
import rmi.*;

public class PrintTest extends UnicastRemoteObject implements ITest {
	String reply;
	private static final long serialVersionUID = 5327494499005883907L;

	public PrintTest() throws RemoteException {}

    public void printStr(String str) {
        System.out.println(str);
        reply = str + " - OK.";
    }
    
    public String getReply() {
    	return reply;
    }
}