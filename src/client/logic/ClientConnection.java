package client.logic;


import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class ClientConnection {
    JMXServiceURL url = null;
    JMXConnector jmxc = null;
    String domain = null;
    public MBeanServerConnection mbsc = null;

    public ClientConnection() {
        try {
            url = new JMXServiceURL(
                    "service:jmx:rmi://localhost:44445/jndi/rmi://localhost:44444/jmxrmi");
                    //"service:jmx:rmi://25.0.246.243:44445/jndi/rmi://25.0.246.243:44444/jmxrmi");
//            url = new JMXServiceURL("service:jmx:rmi://25.71.242.160:44445/jndi/rmi://25.71.242.160:44444/jmxrmi");
            jmxc = JMXConnectorFactory.connect(url, null);
            mbsc = jmxc.getMBeanServerConnection();
            domain = mbsc.getDefaultDomain();
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Open connection...");
    }

    public String getDomain(){
        return domain;
    }

    public void createNewMBean(ObjectName mBeanName) {
        try {
            mbsc.createMBean("jmx.Hello", mBeanName, null, null);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println("Create new MBean: " + mBeanName.toString());
    }

    //========================== INVOKE METHOD TEMPLATE ================================

    public void invokeMethod(ObjectName mBeanName, String methodName, Object  opParams[], String  opSig[]) {
        try {
            mbsc.invoke(mBeanName, methodName, opParams, opSig);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println(methodName);
    }

    public void closeConnection() {
        try {
            jmxc.close();
        } catch (Exception  e) {
            e.printStackTrace();
        }
        System.out.println("Close connection...");
    }
}
