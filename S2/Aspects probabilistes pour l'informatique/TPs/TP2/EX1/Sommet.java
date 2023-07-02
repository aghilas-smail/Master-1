package TP2.EX1;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Sommet {
    private int index;
    private ArrayList<Sommet> voisins;

    public Sommet(int index) {
        this.index = index;
        this.voisins = new ArrayList<>();
    }

    public void addVoisin(Sommet voisin){
        this.voisins.add(voisin);
    }

    public int getIndex(){ return index; }

    public Sommet getRandomVoisin(){
        if(voisins.size() > 0) {
            int index = ThreadLocalRandom.current().nextInt(0, voisins.size());
            return voisins.get(index);
        }else{
            return null;
        }
    }

    public ArrayList<Sommet> getVoisins() {
        return voisins;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sommet n: " + index + "\t\tVoisins: ");
        for(Sommet sommet : voisins){
            sb.append(sommet.getIndex() + ", ");
        }
        String returnValue = sb.toString();
        returnValue = returnValue.substring(0, returnValue.length()-1);
        return returnValue;
    }
}
