package server.neuralNetwork;

import java.lang.Math;
import java.util.ArrayList;

public class Neuron {
    public int id;
    public Layer layer;
    public double sum;
    public double value;
    private ArrayList<Double> data = new ArrayList<>();
    public ArrayList<NeuralConnection> connections = new ArrayList<>();

    public Neuron(int id) {
        sum = 0.0;
        this.id = id;
    }

    public void addLayer(Layer layer) {
        this.layer = layer;
    }

    public void addConnection(NeuralConnection connection) {
        connections.add(connection);
    }

    public void addData(double data){
        this.data.add(data);
    }

    public void sendValue() {
        for (NeuralConnection con: connections) {
            con.out.addData(value * con.weight);
        }
    }

    public void calculate() {
        sum();
        value = sigmoid(sum);
        data = new ArrayList<>();
    }

    private double sigmoid(double data) {
        return (1/(1 + Math.pow(Math.E, (-1*data))));
    }

    private void sum() {
        sum = 0.0;
        for (double d: data) {
            sum += d;
        }
    }

    public double getValue() {
        return value;
    }
}
