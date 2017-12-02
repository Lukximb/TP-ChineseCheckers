package jmx;

public class Hello implements HelloMBean {
    private String message = null;

    public Hello(){}

    public void sayHello(String msg) {
        System.out.println(msg);
    }
}