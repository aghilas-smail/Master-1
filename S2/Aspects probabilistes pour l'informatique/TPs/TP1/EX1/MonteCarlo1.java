package TP1.EX1;

import TP1.Util.Drawer;
import TP1.Util.Point2D;

import java.awt.*;

public class MonteCarlo1 {
    public static double main(double n, boolean draw) throws InterruptedException {
        Drawer drawer = null;
        if(draw)
            drawer = new Drawer(1);
        double s = 0;
        for (int i = 0; i < n; i++) {
            double x = Math.random();
            double y = Math.sqrt(1 - x*x);
            if(draw)
                drawer.draw(new Point2D(x, y), Color.cyan);
            if( i < 1000)
                Thread.sleep(1);
            s += y;
        }
        return 4*s/n;
    }
}
