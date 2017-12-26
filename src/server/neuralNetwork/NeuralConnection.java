package server.neuralNetwork;

public class NeuralConnection {
    Neuron in;
    Neuron out;
    double weight;
    double oldWeight;

    public NeuralConnection( Neuron in, Neuron out, double weight) {
        this.weight = weight;
        this.in = in;
        this.out = out;
    }
}
