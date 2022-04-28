package Environment;

import Environment.Vizualizer.BNNBiasVizualizer;
import NeuralNetwork.BiasManager;

import java.util.ArrayList;
import java.util.Scanner;

public class BNN extends BiasManager {
    public ArrayList<BNNData> BNNHistory;

    public BNN() {
        super(16, 2, 3, 1);
        BNNHistory = new ArrayList<>();
    }

    public BNN(Scanner scanner) {
        this();
        load(scanner);
    }

    public BNN learn(double epocMag, double rate) {
        for (int epoc = 0; epoc < Math.pow(10, epocMag); epoc++) {
            singlePass(epoc % 16);
            if (epoc % 16 == 15)
                update(rate);
        }
        return this;
    }

    private void singlePass(int task) {
        setBias(task);
        double[] currentOp = BNNDATASET.outputSpace.get(task);
        for (int i = 0; i < BNNDATASET.inputSpace.size(); i++)
            back(BNNDATASET.inputSpace.get(i), new double[]{currentOp[i]});
        storBiasGrad();
    }

    public double computeError() {
        double error = 0;
        for (int task = 0; task < 16; task++) {
            setBias(task);
            double[] expected = BNNDATASET.outputSpace.get(task);
            for (int eval = 0; eval < 4; eval++)
                error += Math.abs(expected[eval] - calc(BNNDATASET.inputSpace.get(eval))[0]);
        }
        return error;
    }

    public void printBiases() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter animation speed");
        double animationSpeed = Double.parseDouble(sc.nextLine());
        System.out.println("Enter animation length");
        double animationLength = Double.parseDouble(sc.nextLine());

        new BNNBiasVizualizer(this, animationSpeed, animationLength);
    }

    public void printError() {
        System.out.println("Enter print count");
        Scanner sc = new Scanner(System.in);
        int count = Integer.parseInt(sc.nextLine());
        int modulus = BNNHistory.size() / count;
        int counter = 0;
        for (BNNData bnnData : BNNHistory)
            if (counter++ % modulus == 0)
                System.out.println(bnnData.error);
    }

    @Override
    public void update(double rate) {
        ArrayList<ArrayList<double[]>> curBiasesClone = clone(biases);
        double curError = computeError();
        BNNData bnnData = new BNNData(curBiasesClone, curError);
        BNNHistory.add(bnnData);
        super.update(rate);
        updateBiases(rate);
    }

    private ArrayList<ArrayList<double[]>> clone(ArrayList<ArrayList<double[]>> toClone) {
        ArrayList<ArrayList<double[]>> clone = new ArrayList<>();
        for (ArrayList<double[]> doubles : toClone) {
            ArrayList<double[]> doublesClone = new ArrayList<>();
            for (double[] aDouble : doubles)
                doublesClone.add(aDouble.clone());
            clone.add(doublesClone);
        }
        return clone;
    }

    @Override
    public String toString() {
        return "" + computeError();
    }

    public class BNNData {
        public ArrayList<ArrayList<double[]>> biases;
        public double error;

        public BNNData(ArrayList<ArrayList<double[]>> biases, double error) {
            this.biases = biases;
            this.error = error;
        }
    }
}
