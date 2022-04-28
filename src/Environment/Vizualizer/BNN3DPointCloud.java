package Environment.Vizualizer;

import java.awt.*;
import java.util.ArrayList;

public class BNN3DPointCloud {
    public final ArrayList<BNN3DPoint> points;

    public BNN3DPointCloud(){
        points = new ArrayList<>();
    }

    public void add(double... point) {
        points.add(new BNN3DPoint(point));
    }

    /**
     * Project points to 2d and print to graphics
     * @param graphics
     */
    public Graphics render (Graphics graphics){
        for (BNN3DPoint point3D : points) {
            Point point = point3D.get2D();
            graphics.drawLine(point.x - 1, point.y - 1, point.x, point.y);
        }
        return graphics;
    }
}
