package server.neuralNetwork;

import java.util.ArrayList;
import java.util.Random;
import java.lang.StringBuilder;
import java.io.*;

public class NNManager {
    private Layer in;
    private Layer out;
    private ArrayList<Layer> hidden;
    private ArrayList<Neuron> neurons;
    private ArrayList<NeuralConnection> connections;
    private int neuroNum;
    private int neuronNumberIN;
    private int neuronNumberOUT;
    private int[] neuronNumberHID;
    private int hidenLayerNumber;
    private ArrayList<Double> target;
    private ArrayList<Double> weight;
    private boolean startNew = false;

    public NNManager(int neuronNumberIN, int hidenLayerNumber, int[] neuronNumberHID, int neuronNumberOUT) {
        hidden = new ArrayList<>();
        target = new ArrayList<>();
        neurons = new ArrayList<>();
        connections = new ArrayList<>();
        weight = new ArrayList<>();
        startNew = true;
        this.neuronNumberIN = neuronNumberIN;
        this.neuronNumberOUT = neuronNumberOUT;
        this.neuronNumberHID = neuronNumberHID;
        this.hidenLayerNumber = hidenLayerNumber;
        neuroNum = neuronNumberIN + neuronNumberOUT;
        in = new Layer(Type.IN, neuronNumberIN);
        for( int i = 0; i < hidenLayerNumber; i++) {
            hidden.add(new Layer(Type.HIDDEN, neuronNumberHID[i]));
            neuroNum += neuronNumberHID[i];
        }
        out = new Layer(Type.OUT, neuronNumberOUT);
        createNetwork();
        for (double t: target) {
            t = 0;
        }
    }

    public NNManager() {
        hidden = new ArrayList<>();
        target = new ArrayList<>();
        neurons = new ArrayList<>();
        connections = new ArrayList<>();
        weight = new ArrayList<>();
        readFromFile();
        in = new Layer(Type.IN, neuronNumberIN);
        for( int i = 0; i < hidenLayerNumber; i++) {
            hidden.add(new Layer(Type.HIDDEN, neuronNumberHID[i]));
            neuroNum += neuronNumberHID[i];
        }
        out = new Layer(Type.OUT, neuronNumberOUT);
        createNetwork();
        for (double t: target) {
            t = 0;
        }
    }

    private void createNetwork() {
        int id = 0;
        for (int i = 0; i < neuronNumberIN; i++, id++) {
            Neuron neuron = new Neuron(id);
            neuron.addLayer(in);
            in.addNeuron(neuron);
            neurons.add(neuron);
        }
        for (int i = 0; i < hidenLayerNumber; i++) {
            for (int j = 0; j < neuronNumberHID[i]; j++, id++) {
                Neuron neuron = new Neuron(id);
                neuron.addLayer(hidden.get(i));
                hidden.get(i).addNeuron(neuron);
                neurons.add(neuron);
            }
        }
        for (int i = 0; i < neuronNumberOUT; i++, id++) {
            Neuron neuron = new Neuron(id);
            neuron.addLayer(out);
            out.addNeuron(neuron);
            neurons.add(neuron);
        }

        int i = 0;
        if (startNew) {
            Random rand = new Random(123456);
            for (Neuron neuronI: in.neurons) {
                for (Neuron neuronH: hidden.get(0).neurons) {
                    NeuralConnection connection = new NeuralConnection(neuronI, neuronH, rand.nextDouble());
                    neuronI.addConnection(connection);
                    connections.add(connection);
                    //System.out.println("Connector created: IN <-> HIDDEN");
                }
            }

            for (int m = 0; m < hidenLayerNumber; m++) {
                if (m + 1 < hidenLayerNumber) {
                    for (Neuron neuronHA: hidden.get(m).neurons) {
                        for (Neuron neuronHB: hidden.get(m+1).neurons) {
                            NeuralConnection connection = new NeuralConnection(neuronHA, neuronHB, rand.nextDouble());
                            neuronHA.addConnection(connection);
                            connections.add(connection);
                            //System.out.println("IConnector created: HIDDEN <-> OUT");
                        }
                    }
                } else {
                    for (Neuron neuronH: hidden.get(m).neurons) {
                        for (Neuron neuronO: out.neurons) {
                            NeuralConnection connection = new NeuralConnection(neuronH, neuronO, rand.nextDouble());
                            neuronH.addConnection(connection);
                            connections.add(connection);
                            //System.out.println("EConnector created: HIDDEN <-> OUT");
                        }
                    }
                }
            }
        } else {
            for (Neuron neuronI: in.neurons) {
                for (Neuron neuronH: hidden.get(0).neurons) {
                    NeuralConnection connection = new NeuralConnection(neuronI, neuronH, weight.get(i));
                    neuronI.addConnection(connection);
                    connections.add(connection);
                    i++;
                    //System.out.println("Connector created: IN <-> HIDDEN");
                }
            }

            for (int m = 0; m < hidenLayerNumber; m++) {
                if (m + 1 < hidenLayerNumber) {
                    for (Neuron neuronHA: hidden.get(m).neurons) {
                        for (Neuron neuronHB: hidden.get(m+1).neurons) {
                            NeuralConnection connection = new NeuralConnection(neuronHA, neuronHB, weight.get(i));
                            neuronHA.addConnection(connection);
                            connections.add(connection);
                            i++;
                            //System.out.println("Connector created: HIDDEN <-> OUT");
                        }
                    }
                } else {
                    for (Neuron neuronH: hidden.get(m).neurons) {
                        for (Neuron neuronO: out.neurons) {
                            NeuralConnection connection = new NeuralConnection(neuronH, neuronO, weight.get(i));
                            neuronH.addConnection(connection);
                            connections.add(connection);
                            i++;
                            //System.out.println("Connector created: HIDDEN <-> OUT");
                        }
                    }
                }
            }
        }
    }

    public void getOutput(int i, int k, ArrayList<Double> data, ArrayList<Double> target) {
        int dataNumber = 0;
        this.target = target;
        for (int c = 0; c < neuronNumberIN; c++, dataNumber++) {
            in.neurons.get(c).addData(data.get(dataNumber));
            in.neurons.get(c).calculate();
            in.neurons.get(c).sendValue();
        }
        for (int c = 0; c < hidenLayerNumber; c++) {
            for (int j = 0; j < neuronNumberHID[c]; j++) {
                hidden.get(c).neurons.get(j).calculate();
                hidden.get(c).neurons.get(j).sendValue();
            }
        }
        int n = 0;
        for (Neuron neuronO: out.neurons) {
            neuronO.calculate();
            if (i == 9998 && k == 998) {
                System.out.println(neuronO.getValue());
                if (n==2) { System.out.println(""); }
                n++;
            }
        }
    }

    public ArrayList<Double> getOutput(ArrayList<Double> data) {
        ArrayList<Double> output = new ArrayList<>();
        int dataNumber = 0;
        this.target = target;
        for (int i = 0; i < neuronNumberIN; i++, dataNumber++) {
            in.neurons.get(i).addData(data.get(dataNumber));
            in.neurons.get(i).calculate();
            in.neurons.get(i).sendValue();
        }
        for (int i = 0; i < hidenLayerNumber; i++) {
            for (int j = 0; j < neuronNumberHID[i]; j++, dataNumber++) {
                in.neurons.get(j).addData(data.get(dataNumber));
                in.neurons.get(j).calculate();
                in.neurons.get(j).sendValue();
            }
        }
        for (Neuron neuronO: out.neurons) {
            neuronO.calculate();
            output.add(neuronO.getValue());
        }
        return output;
    }

    private double learn(ArrayList<Double> input) {
        ArrayList<Double> marginOutputError = new ArrayList<>();
        for (int i = 0; i < neuronNumberOUT; i++) {
            marginOutputError.add(target.get(i) - out.neurons.get(i).value);
        }

        double errsum = 0;
        for (double err : marginOutputError) {
            errsum += err;
        }
        errsum /= marginOutputError.size();

        ArrayList<Double> deltaSum = new ArrayList<>();
        for (int i = 0; i < neuronNumberOUT; i++) {
            deltaSum.add(sigmoidDerivative(out.neurons.get(i).sum) * marginOutputError.get(i));
        }

        ArrayList<Double> oldDeltahiddenSum;
        ArrayList<Double> deltahiddenSum = new ArrayList<>();
        for (int i = hidenLayerNumber - 1; i >= 0; i--) {
            oldDeltahiddenSum = deltahiddenSum;
            deltahiddenSum = new ArrayList<>();
            if (i + 1 == hidenLayerNumber) {
                for (Neuron neuronH: hidden.get(i).neurons) {
                    double error = 0;
                    for (int k = 0; k < neuronNumberOUT; k++) {
                        neuronH.connections.get(k).oldWeight = neuronH.connections.get(k).weight;
                        neuronH.connections.get(k).weight += 0.001 * deltaSum.get(k) * neuronH.value;
                        error += deltaSum.get(k) * neuronH.connections.get(k).oldWeight * sigmoidDerivative(neuronH.sum);
                    }
                    deltahiddenSum.add(error);
                }
            } else {
                for (Neuron neuronH: hidden.get(i).neurons) {
                    double error = 0;
                    for (int k = 0; k < neuronNumberHID[i+1]; k++) {
                        neuronH.connections.get(k).oldWeight = neuronH.connections.get(k).weight;
                        neuronH.connections.get(k).weight += 0.001 * oldDeltahiddenSum.get(k) * neuronH.value;
                        error += oldDeltahiddenSum.get(k) * neuronH.connections.get(k).oldWeight * sigmoidDerivative(neuronH.sum);
                    }
                    deltahiddenSum.add(error);
                }
            }
        }
        for (int i = 0; i < neuronNumberIN; i++) {
            for (int k = 0; k < neuronNumberHID[0]; k++) {
                in.neurons.get(i).connections.get(k).oldWeight = in.neurons.get(i).connections.get(k).weight;
                in.neurons.get(i).connections.get(k).weight += 0.001 * deltahiddenSum.get(k) * (input.get(i) + 0.00000001);
            }
        }
        return errsum;
    }

    private double sigmoidDerivative(double data) {
        return sigmoid(data) * (1 - sigmoid(data));
    }

    private double sigmoid(double data) {
        return (1/(1 + Math.pow(Math.E, (-1*data))));
    }

    private void writeToFile() {
        StringBuilder text = new StringBuilder();
        text.append(neuroNum+"\n");
        text.append(neuronNumberIN+"\n");
        text.append(neuronNumberOUT+"\n");
        text.append(hidenLayerNumber+"\n");
        for (int i = 0; i < hidenLayerNumber; i++) {
            text.append(neuronNumberHID[i]+"\n");
        }
        text.append("in:\n");
        for (NeuralConnection con: connections) {
            if(con.in.layer == in) {
                text.append(" " + con.weight + " \n");
            }
        }
        text.append("hid:\n");
        for (NeuralConnection con: connections) {
            for (int i = 0; i < hidenLayerNumber; i++) {
                if(con.in.layer == hidden.get(i)) {
                    text.append(" " + con.weight + " \n");
                }
            }
        }
        BufferedWriter output = null;
        try {
            File file = new File("weihgNeurons.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write(text.toString());
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
                try {
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void readFromFile() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("weihgNeurons.txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            int k = 0;
            int n = 0;

            while (line != null) {
                if (k == 0) {
                    neuroNum = Integer.parseInt(line);
                } else if (k == 1) {
                    neuronNumberIN = Integer.parseInt(line);
                } else if (k == 2) {
                    neuronNumberOUT = Integer.parseInt(line);
                } else if (k == 3) {
                    hidenLayerNumber = Integer.parseInt(line);
                    neuronNumberHID = new int[hidenLayerNumber];
                } else if (k <= hidenLayerNumber + 3) {
                    neuronNumberHID[n] = Integer.parseInt(line);
                    n++;
                } else if (line.equals("in:") || line.equals("hid:")) {
                    k++;
                    line = br.readLine();
                    continue;
                } else {
                    double data = Double.parseDouble(line);
                    weight.add(data);
                }
                k++;
                line = br.readLine();
            }
            br.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        long startTime = System.currentTimeMillis();
        int nHid[] = {14, 17, 13};
        //NNManager manager = new NNManager(12, 3, nHid, 12);//(IN, hLayer, [] neHID, OUT)
        NNManager manager = new NNManager();
        double prevErr;
        double err = 0;

        for (int i = 0; i < 1000000; i++) {
            for (int j = 0; j < 200; j++) {

                BufferedReader br;
                File file = new File("G:\\Repozytoria\\TP-ChineseCheckers\\src\\dataExample.txt");
                ArrayList<Double> data;
                ArrayList<Double> target;
                ArrayList<Double> error = new ArrayList<>();
                int rowNumber = 0;

                try {
                    br = new BufferedReader(new FileReader(file));
                    String line = br.readLine();
                    while (line != null) {
                        data = new ArrayList<>();
                        target = new ArrayList<>();
                        error = new ArrayList<>();
                        String[] dataString = line.split(" ");
                        for( String s: dataString) {
                            double n = Double.parseDouble(s);
                            data.add(n);
                            if (n == 1) {
                                target.add(0.0);
                            } else {
                                target.add(1.0);
                            }
                        }
                        manager.getOutput(j, i, data, target);

                        error.add(manager.learn(data));
                        rowNumber++;

                        line = br.readLine();
                    }
                    if (j == 199) {
                        prevErr = err;
                        err = 0;
                        for (double d : error) {
                            err += d;
                        }

                        if (i == 0) {
                            prevErr = err;
                        }

                        System.out.print(i + " Err: ");
                        System.out.printf(" %.19f", err);
                        System.out.print("      DecErr: ");
                        System.out.printf(" %.25f", Math.abs(prevErr - err));
                        System.out.println("");

                    }
                    br.close();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
            manager.writeToFile();
        }
        manager.writeToFile();
        long endTime   = System.currentTimeMillis();
        Long totalTime = endTime - startTime;
        System.out.println(totalTime);
    }
}
