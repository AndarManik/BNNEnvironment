package Environment;

import java.util.ArrayList;

public final class BNNDATASET {
    public static ArrayList<double[]> inputSpace = binaryProductSpace(2);
    public static final ArrayList<double[]> outputSpace = binaryProductSpace(4);

    static ArrayList<double[]> binaryProductSpace(double dimension) {
        ArrayList<double[]> productSpace = new ArrayList<>();
        for (int i = 0; i < Math.pow(2, dimension); i++)
            productSpace.add(getBinaryRepresentation(dimension, i));
        return productSpace;
    }

    static double[] getBinaryRepresentation(double dimension, int number) {
        double[] output = new double[(int) dimension];
        int remainder = number;
        for (int j = 0; j < dimension; j++, remainder /= 2)
            output[j] = (remainder % 2 - 0.5) * 2;
        return output;
    }
}
