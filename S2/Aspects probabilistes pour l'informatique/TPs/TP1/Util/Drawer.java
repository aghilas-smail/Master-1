package TP1.Util;

import javax.swing.*;
import java.awt.*;

public class Drawer {
    private JFrame frame;
    private MyCanvas myCanvas;
    int width, height;
    public Drawer(double valMax) {
        frame = new JFrame("Dessiner Polygone");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        width = 800;
        height = 800;
        frame.setSize(width +  (width/10), height + (height/10));
        myCanvas = new MyCanvas(width, height, valMax);
        frame.setContentPane(myCanvas.getView());
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

    }

    public void draw(PolygoneListe polygoneListe) {
        myCanvas.drawPolygon(polygoneListe);
    }

    public void draw(Point2D point2D, Color color){
       myCanvas.drawPoint(point2D, color);
    }
}
