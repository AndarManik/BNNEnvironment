package Environment.Vizualizer;

import Environment.BNN;


import java.awt.*;
import java.util.ArrayList;

public class BNNBiasVizualizer extends BNNVizualizer {

    public ArrayList<BNN.BNNData> bnnHistory;
    BNN3DPointCloud bnn3DPointCloud;
    double index;
    double animationSpeed;
    double animationLength;// [0,1]
    double scale;

    public BNNBiasVizualizer(BNN bnn, double animationSpeed, double animationLength, double scale) {
        super("BiasVizualizer");
        bnnHistory = bnn.BNNHistory;
        bnn3DPointCloud = new BNN3DPointCloud(0);
        index = 0;
        this.animationSpeed = animationSpeed;
        this.animationLength = animationLength;
        this.scale = scale;
    }

    @Override
    public Graphics toRender(Graphics graphics) {
        return bnn3DPointCloud.render(graphics);
    }

    @Override
    public void update() {
        bnn3DPointCloud = new BNN3DPointCloud(scale);
        ArrayList<ArrayList<double[]>> biases = bnnHistory.get((int) index).biases;
        for (ArrayList<double[]> bias : biases)
            bnn3DPointCloud.add(bias.get(0));
        index += animationSpeed;
        index %= bnnHistory.size() * animationLength;
    }
}
