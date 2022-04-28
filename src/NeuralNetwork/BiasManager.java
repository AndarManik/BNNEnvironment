package NeuralNetwork;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
    BiasManager stores biases to be used in multitask settings.
    It's able to extract biases from the network
    set the bias in the network
    and update stored biases using the bias of network
 */
public class BiasManager extends NeuralNetwork {
    public ArrayList<ArrayList<double[]>> biases;
    public ArrayList<ArrayList<double[]>> biasGrads;
    int currentBiasIndex;//-1 if bias is not in list

    /**
     * Constructs a Neural Network and a set of biases
     *
     * @param dim       Dimension of the network
     * @param biasCount Number of biases
     */
    public BiasManager(int biasCount, int... dim) {
        super(dim);
        biases = new ArrayList<>();
        biasGrads = new ArrayList<>();
        ArrayList<double[]> bias = getBias();

        for (int i = 0; i < biasCount; i++) {
            ArrayList<double[]> curBias = new ArrayList<>();
            ArrayList<double[]> curGrad = new ArrayList<>();
            for (double[] b : bias) {
                curBias.add(b.clone());
                curGrad.add(new double[b.length]);
            }
            biases.add(curBias);
            biasGrads.add(curGrad);
        }

        currentBiasIndex = 0;
    }

    //returns the current bias
    public ArrayList<double[]> getBias() {
        ArrayList<double[]> bias = new ArrayList<>();
        for (Layer l : network)
            bias.add(l.bias);
        return bias;
    }

    public void setBias(ArrayList<double[]> bias) {
        currentBiasIndex = -1;
        for (int i = 0; i < network.size(); i++)
            network.get(i).bias = bias.get(i);
    }

    public void setBias(int biasIndex) {
        currentBiasIndex = biasIndex;
        ArrayList<double[]> bias = biases.get(biasIndex);
        for (int i = 0; i < network.size(); i++)
            network.get(i).bias = bias.get(i);
    }

    public void storBiasGrad() {
        if (currentBiasIndex == -1) return;

        ArrayList<double[]> curBiasGrad = biasGrads.get(currentBiasIndex);
        for (int i = 0; i < curBiasGrad.size(); i++)
            for (int j = 0; j < curBiasGrad.get(i).length; j++) {
                curBiasGrad.get(i)[j] += network.get(i).biasGrad[j];
                network.get(i).biasGrad[j] = 0;
            }
    }

    public void updateBiases(double rate) {
        for (int i = 0; i < biases.size(); i++)
            for (int j = 0; j < biases.get(i).size(); j++)
                for (int k = 0; k < biases.get(i).get(j).length; k++) {
                    biases.get(i).get(j)[k] -= biasGrads.get(i).get(j)[k] * rate;
                    biasGrads.get(i).get(j)[k] = 0;
                }
    }

    public ArrayList<double[]> getBiasPOINTER(int index) {
        return biases.get(index);
    }

    public void randomizeBias(int index) {
        ArrayList<double[]> curr = biases.get(index);
        for (double[] d : curr)
            for (int i = 0; i < d.length; i++)
                d[i] = Math.random() - 0.5;
    }

    public void save(FileWriter fileWriter) {
        try {
            for (Layer layer : network)
                for (double[] weight : layer.weight)
                    for (int i = 0; i < weight.length; i++)
                        fileWriter.write(weight[i] + "\n");

            for (ArrayList<double[]> bias : biases)
                for (double[] layerBias : bias)
                    for (int i = 0; i < layerBias.length; i++)
                        fileWriter.write(layerBias[i] + "\n");

            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("BiasManager save error" + e);
        }
    }

    public void load(Scanner scanner) {
        for (Layer layer : network)
            for (double[] weight : layer.weight)
                for (int i = 0; i < weight.length; i++)
                    weight[i] = Double.parseDouble(scanner.nextLine());

        for (ArrayList<double[]> bias : biases)
            for (double[] layerBias : bias)
                for (int i = 0; i < layerBias.length; i++)
                    layerBias[i] = Double.parseDouble(scanner.nextLine());
    }
}
