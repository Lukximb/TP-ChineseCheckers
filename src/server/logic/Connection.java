package server.logic;

import jmx.Hello;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.rmi.registry.LocateRegistry;

public class Connection {
    private MBeanServer mbs = null;
    private String domain = null;

    public void createRegistry(){
        try {
            LocateRegistry.createRegistry(44444);
            System.out.println("Create RMI registry on port 44444");
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void createMBeanServer() {
        mbs = MBeanServerFactory.createMBeanServer();
        System.out.println("Create MBean Server");
    }

    public void createDomain() {
        domain = mbs.getDefaultDomain();
        System.out.println("Set domain: " + domain);
    }

    public void createMBeanObject(String mBeanClassNameArg, String name, int id) {
        String mbeanClassName = mBeanClassNameArg;
        System.out.println("Set mbeanClassName: " + mbeanClassName);
        String mbeanObjectNameStr =
                domain+ id + ":type=" + mbeanClassName + ",name=" + name;
        System.out.println("Set mbeanObjectNameStr: " + mbeanObjectNameStr);
        try {
            ObjectName mbeanObjectName =
                    ObjectName.getInstance(mbeanObjectNameStr);
            mbs.createMBean(mbeanClassName, mbeanObjectName);
            System.out.println("Create mBean: " + mbeanObjectName.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void createConnectorServer() {
        try {
            JMXServiceURL url = new JMXServiceURL(
                    "service:jmx:rmi://25.71.213.200:44445/jndi/rmi://25.71.213.200:44444/jmxrmi");
            JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
            cs.start();
            System.out.println("Create ConnectorServer");
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void registryMBeanObject(String name) {
        Hello helloBean = new Hello();
        ObjectName helloName = null;

        try {
            helloName = new ObjectName("server.Server:name=" + name);
            mbs.registerMBean(helloBean, helloName);
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Registry MBeanObject: " + name);
    }
}
