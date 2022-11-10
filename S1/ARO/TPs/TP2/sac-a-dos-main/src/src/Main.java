import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    static ArrayList<Object> objects;
    static int c;
    static double optimum;
    static ArrayList<Object> list = new ArrayList<>();
    static ArrayList<Object> listMax = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i <= 4; i++) {
            String s = "Sac/sac" + i;
            System.out.println("la solution optimale pour le sac " + i + " est : " + optimalSolution(s));
            System.out.println(listMax);
        }

    }



    public static double optimalSolution(String file){

        objects = new ArrayList<Object>();
        list = new ArrayList<>();
        listMax = new ArrayList<>();
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            if(myReader.hasNextLine()) {
                c = Integer.parseInt(myReader.nextLine());
            }
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] s = data.split(" ");
                int w = Integer.parseInt(s[0]);
                int v = Integer.parseInt(s[1]);
                Object object = new Object(v, w);
                objects.add(object);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Collections.sort(objects);
        branchAndBound(0,0,0);
        return optimum;
    }


    public static void branchAndBound(int weight, double value, int level){
        if(level == objects.size()) return;
        if(value > optimum) {
            optimum = value;
            listMax = (ArrayList<Object>) list.clone();
        }

        double maxValue = value;
        int maxWeight = weight;
        int i = level;
        while(maxWeight<c && i<objects.size()) {
            Object object = objects.get(i);
            if((maxWeight + object.getWeight()) < c) {
                maxWeight += object.getWeight();
                maxValue += object.getValue();
            }
            else {
                maxValue = maxValue +  ((double)(c-maxWeight)/(double) object.getWeight())* object.getValue();
                maxWeight = c;
            }
            i++;
        }

        Object object = objects.get(level);
        if((maxValue > optimum) && (c>=weight + object.getWeight())) {
            list.add(object);
            branchAndBound(weight + object.getWeight(), value + object.getValue(), level+1);
            list.remove(object);
            branchAndBound(weight, value, level+1);
        }
    }
}
