package rmi;

import java.rmi.*;

public interface ITest extends java.rmi.Remote {
    void printStr(String str) throws RemoteException;
}