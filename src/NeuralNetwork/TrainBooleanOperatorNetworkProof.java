package NeuralNetwork;

import java.util.ArrayList;

public class TrainBooleanOperatorNetworkProof {

    static final int TRIES = 100;
    static final double CUTOFF = 0.3;
    static final boolean printErrors = true;
    static final int EPOCMAG = 6;
    static final double RATE = 0.01;
    static BiasManager bm;
    static ArrayList<double[]> inputSpace = binaryProductSpace(2);
    static final ArrayList<double[]> outputSpace = binaryProductSpace(4);

    public static void main(String[] args) {
        System.out.println(works(TRIES));
    }

    /**
     * Trains a new initialization of a Bias Manager a number of times
     *
     * @param tries Cut off point
     * @return True if the total error is less than 0.5
     * False if it does not return true after 'TRIES' times
     */
    private static boolean works(int tries) {
        for (int i = 0; i < tries; i++)
            if (train(EPOCMAG, RATE) < CUTOFF)
                return true;
        return false;
    }

    private static double train(double epocMag, double rate) {
        bm = new BiasManager(16, 2, 3, 1);
        for (int epoc = 0; epoc < Math.pow(10, epocMag); epoc++) {
            singlePass(epoc % 16);
            if (epoc % 16 == 15) {
                bm.update(rate);
                bm.updateBiases(rate);
            }
        }
        return proof();
    }

    private static void singlePass(int task) {
        bm.setBias(task);
        double[] currentOp = outputSpace.get(task);
        for (int i = 0; i < inputSpace.size(); i++)
            bm.back(inputSpace.get(i), new double[]{currentOp[i]});
        bm.storBiasGrad();
    }

    //Proof=============================================================
    //Proof=============================================================
    //Proof=============================================================

    /**
     * Sums of the errors of the network at its current state
     *
     * @return Sum of errors
     */
    private static double proof() {
        double sum = 0;
        for (double d : getScoreList())
            sum += d;
        if (printErrors)
            System.out.println(sum);
        return sum;
    }

    /**
     * Computes the error of the Bias Manager for every tasks
     *
     * @return Array storing the errors for every task
     */
    private static double[] getScoreList() {
        double[] scoreList = new double[16];
        for (int task = 0; task < 16; task++)
            scoreList[task] = getTaskError(task);
        return scoreList;
    }

    /**
     * Computes the error for a single task
     *
     * @param task Current task for the Bias Manager
     * @return Error for the current task
     */
    private static double getTaskError(int task) {
        bm.setBias(task);
        double[] currentOp = outputSpace.get(task);
        double error = 0;
        for (int i = 0; i < currentOp.length; i++)
            error += Math.abs((bm.calc(inputSpace.get(i))[0] - currentOp[i]));
        return error;
    }


    //Data Generation====================================================
    //Data Generation====================================================
    //Data Generation====================================================


    /**
     * Computes an ArrayList which stores every binary number of a certain digit count
     *
     * @param dimension Number of digits
     * @return ArrayList of Double[] which contain the individual binary digits
     */
    public static ArrayList<double[]> binaryProductSpace(double dimension) {
        ArrayList<double[]> productSpace = new ArrayList<>();
        for (int i = 0; i < Math.pow(2, dimension); i++)
            productSpace.add(getBinaryRepresentation(dimension, i));
        return productSpace;
    }

    /**
     * Convert number to binary and stores the digits in an array
     *
     * @param dimension Number of digits
     * @param number    Current number to be converted
     * @return Double[] which stores the individual digits
     */
    private static double[] getBinaryRepresentation(double dimension, int number) {
        double[] output = new double[(int) dimension];
        int remainder = number;
        for (int j = 0; j < dimension; j++, remainder /= 2)
            output[j] = (remainder % 2 - 0.5) * 2;
        return output;
    }
}