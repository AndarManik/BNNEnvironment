package Environment.Vizualizer;

import Environment.BNN;


import java.awt.*;
import java.util.ArrayList;

public class BNNBiasVizualizer extends BNNVizualizer{

    public ArrayList<BNN.BNNData> bnnHistory;
    BNN3DPointCloud bnn3DPointCloud;
    double index;
    double animationSpeed;
    double animationLength;// [0,1]

    public BNNBiasVizualizer(BNN bnn, double animationSpeed, double animationLength) {
        super("BiasVizualizer");
        bnnHistory = bnn.BNNHistory;
        bnn3DPointCloud = new BNN3DPointCloud();
        index = 0;

        this.animationSpeed = animationSpeed;
        this.animationLength = animationLength;
    }

    @Override
    public Graphics toRender(Graphics graphics) {
        return bnn3DPointCloud.render(graphics);
    }

    @Override
    public void update() {
        bnn3DPointCloud = new BNN3DPointCloud();

        ArrayList<ArrayList<double[]>> biases = bnnHistory.get((int)index).biases;
        for (ArrayList<double[]> bias : biases)
            bnn3DPointCloud.add(bias.get(0));
        index += animationSpeed;
        index %= bnnHistory.size() * animationLength;
    }
}
