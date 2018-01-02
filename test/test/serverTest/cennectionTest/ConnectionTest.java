package test.serverTest.cennectionTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.connection.Connection;
import server.manager.Manager;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConnectionTest {
    Connection con;
    Registry reg;

    @BeforeEach
    void setUp() {
        con = Connection.getInstance();
    }


    @Test
    void getInstance() {
        assertNotNull(con);
    }

    @Test
    void createRegistry() {
        con.createRegistry();
        try {
            reg = LocateRegistry.getRegistry(44444);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        assertNotNull(reg);
    }

    @Test
    void createMBeanServer() {
        con.createMBeanServer();
        Field privateMBS = null;
        try {
            privateMBS = Connection.class.
                    getDeclaredField("mbs");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        privateMBS.setAccessible(true);

        try {
            assertNotNull((MBeanServer) privateMBS.get(con));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createDomain() {
        con.createDomain();
        Field privateDomain = null;
        try {
            privateDomain = Connection.class.
                    getDeclaredField("domain");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        privateDomain.setAccessible(true);

        try {
            assertNotNull((String) privateDomain.get(con));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createMBeanObjectTest() {
        ObjectName manager = null;
        con.createConnectorServer();
        Manager m = Manager.getInstance();
        con.createMBeanMainObject("manager.Manager", "Manager", "M", m);

        Field privateDomain = null;
        try {
            privateDomain = Connection.class.
                    getDeclaredField("domain");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        privateDomain.setAccessible(true);

        try {
            manager = new ObjectName((String)privateDomain.get(con)+"F" +":type=server.core.Factory,name=Factory");
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(manager);
    }
}