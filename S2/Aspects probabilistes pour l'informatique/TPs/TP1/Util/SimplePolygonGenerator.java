package TP1.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimplePolygonGenerator {
    public static PolygoneListe genetatePolygon(int nbSommets, double valMax, boolean polyTrou){
        double[] xValues =  new double[nbSommets];
        double[] yValues = new double[nbSommets];
        ArrayList<Double> radValues = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < nbSommets; i++) {
            double rad = (10 * r.nextDouble())%(2*Math.PI);
            radValues.add(rad);
        }
        if(!polyTrou)
            Collections.sort(radValues);
        for (int i = 0; i < nbSommets; i++) {
            double radval = radValues.get(i);
            double x = Math.cos(radval);
            double y = Math.sin(radval);
            double random =ThreadLocalRandom.current().nextDouble(0.1, 1);
            x *= random;
            y *= random;
            xValues[i] = valMax * (x+1)/2;
            yValues[i] = valMax * (y+1)/2;
        }
        return  new PolygoneListe(xValues, yValues, valMax);
    }
}
