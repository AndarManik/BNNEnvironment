package Environment.Vizualizer;

import java.awt.*;

public class BNN3DPoint {
    public final double x, y, z;

    public BNN3DPoint(double... pointData) {
        this.x = pointData[0];
        this.y = pointData[1];
        this.z = pointData[2];
    }

    public Point get2D(double scale) {
        double scalex = scale * x;
        double scaley = scale * y;
        double scalez = scale * z;

        int convertx = (int) (BNNVizualizer.WIDTH / 2 + scalex);
        int converty = (int) (BNNVizualizer.HEIGHT / 2 - scaley);

        return new Point(convertx, converty);
    }
}
