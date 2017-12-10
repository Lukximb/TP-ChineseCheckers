package jmx;

import java.util.ArrayList;

public class Hello implements HelloMBean {
    private String message = null;
    private ArrayList<String> array = null;

    public Hello(){
        array = new ArrayList<>();
    }

    public void sayHello(String msg) {
        array.add(msg);
        System.out.println("");
        for(String m: array){
            System.out.print(m + " ");
        }
        //System.out.println(msg);
    }
}