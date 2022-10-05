// -*- coding: utf-8 -*-

import java.lang.Math; 
import java.util.Random;
import java.lang.Thread;

public class PiSurQuatre extends Thread {	
    static long nbTirages = 5_000_000 ;          // Précision du calcul, fixée à 5 000 000	
    static long tiragesDansLeDisque = 0 ;        // Nb de tirages dans le disque
    static int nbThreads = 10 ;                  // Nb de threads utilisés
    
    public static void main (String args[]) {
        System.out.println("Nombre de tirages: " + nbTirages/1_000_000 + " million(s).") ;
        PiSurQuatre[] T = new PiSurQuatre[nbThreads];
        for(int i=0; i<nbThreads; i++){
            T[i] = new PiSurQuatre();
            T[i].start();
        }
        for(int i=0; i<nbThreads; i++){
            try{ T[i].join(); } catch(InterruptedException e){e.printStackTrace();}
        }
        double résultat = (double) tiragesDansLeDisque / nbTirages ;
        System.out.format("Estimation de Pi/4: %.9f %n", résultat) ;
    }

    public void run(){
        Random aléa = new Random();
        double x, y;
        for(long i = 0; i < nbTirages/nbThreads; i++){
            x = aléa.nextDouble() ;
            y = aléa.nextDouble() ;
            if (x * x + y * y <= 1) tiragesDansLeDisque++ ;
        }
    }
}


/* 
  $ make
  javac -encoding UTF-8 *.java
  $ java PiSurQuatre
  Nombre de tirages: 5 million(s).
  Estimation de Pi/4: 0,365413200 
  $
*/
