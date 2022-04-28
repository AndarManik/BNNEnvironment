package Environment.Vizualizer;

import java.awt.*;

public class BNN3DPoint {
    public final double x,y,z;

    public BNN3DPoint(double... pointData){
        this.x = 100 * pointData[0];
        this.y = 100 * pointData[1];
        this.z = 100 * pointData[2];
    }

    public Point get2D(){
        int convertx = (int) (BNNVizualizer.WIDTH / 2 + x);
        int converty = (int) (BNNVizualizer.HEIGHT / 2 - y);

        return new Point(convertx, converty);
    }
}
