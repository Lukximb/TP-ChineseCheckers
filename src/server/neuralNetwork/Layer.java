package server.neuralNetwork;

import java.util.ArrayList;

public class Layer {
    public ArrayList<Neuron> neurons;
    private Type type;
    private int neuronNumber;

    public Layer(Type type, int neuronNumber) {
        neurons = new ArrayList<>();
        this.type = type;
        this.neuronNumber = neuronNumber;
    }

    public void addNeuron(Neuron neuron) {
        neurons.add(neuron);
    }
}
