// -*- coding: utf-8 -*-

import java.lang.Math; 
import java.util.Random;

public class PiSurQuatre {
    public static void main(String[] args) {
        long nbTirages = 5_000_000;     // Précision du calcul, fixée à 5 000 000
        long tiragesDansLeDisque = 0 ;
      
        if (args.length>0) {
            try { nbTirages = 1_000_000 * Integer.parseInt(args[0]); } 
            catch(NumberFormatException nfe) { 
                System.err.println 
                    ("Usage : java PiSurQuatre <nb de tirages (en millions)>"); 
                System.err.println(nfe.getMessage()); 
                System.exit(1); 
            }
        }

        System.out.println("Nombre de tirages: " + nbTirages/1_000_000 + " million(s).") ;
		final long début = System.nanoTime();

        Random aléa = new Random();
        double x, y;
        for (long i = 0; i < nbTirages; i++) {
            x = aléa.nextDouble() ;
            y = aléa.nextDouble() ;
            if (x * x + y * y <= 1) tiragesDansLeDisque++ ;
        }
        double résultat = (double) tiragesDansLeDisque / nbTirages ;
        System.out.format("Estimation de Pi/4: %.9f %n", résultat) ;
        double erreur = 100 * Math.abs(résultat-Math.PI/4)/(Math.PI/4) ;
        System.out.format("Pourcentage d'erreur: %.9f %% %n", erreur);
        
		final long fin = System.nanoTime();
		final long durée = (fin - début) / 1_000_000 ;
		System.out.format("Durée du calcul: %.3f s.%n", (double) durée/1000);
    }
}

/*
  Avec un million de tirages, l'erreur doit être en général < 0.1 %
  Avec 100 millions de tirages, l'erreur doit être < 0.01 %

  $ make
  javac *.java
  $ java PiSurQuatre
  Nombre de tirages: 1 million(s).
  Estimation de Pi/4: 0,784199000 
  Pourcentage d'erreur: 0,152682226 % 
  Durée du calcul: 0,068 s.
  $ java PiSurQuatre 100
  Nombre de tirages: 100 million(s).
  Estimation de Pi/4: 0,785350190 
  Pourcentage d'erreur: 0,006108163 % 
  Durée du calcul: 3,950 s.
  $ java PiSurQuatre 500
  Nombre de tirages: 500 million(s).
  Estimation de Pi/4: 0,785362942 
  Pourcentage d'erreur: 0,004484528 % 
  Durée du calcul: 19,667 s.
*/
