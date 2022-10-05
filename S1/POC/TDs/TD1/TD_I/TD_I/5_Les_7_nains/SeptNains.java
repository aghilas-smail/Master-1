// -*- coding: utf-8 -*-

import java.util.ArrayList;

public class SeptNains {
    public static void main(String[] args) {
        int nbNains = 7;
        String nom [] = {"Simplet", "Dormeur",  "Atchoum", "Joyeux", "Grincheux", "Prof", "Timide"};
        Nain nain [] = new Nain [nbNains];
        for(int i = 0; i < nbNains; i++) nain[i] = new Nain(nom[i]);
        for(int i = 0; i < nbNains; i++) nain[i].start();
    }
}    

class BlancheNeige {
    private volatile boolean libre = true;        // Initialement, Blanche-Neige est libre.

    public void requerir() {
        System.out.println("\t" + Thread.currentThread().getName()
                           + " veut un accès exclusif à la ressource");
    }

    public void acceder() { }

    public void relacher() { }
}

class Nain extends Thread {
    private static final BlancheNeige bn = new BlancheNeige();

    public Nain(String nom) {
        this.setName(nom);
    }
    
    public void run() {
        while(true) {
            bn.requerir();
            bn.acceder();
            try {
                sleep(1000);
            } catch (InterruptedException e) {e.printStackTrace();}
            bn.relacher();
        }
    }	
}

