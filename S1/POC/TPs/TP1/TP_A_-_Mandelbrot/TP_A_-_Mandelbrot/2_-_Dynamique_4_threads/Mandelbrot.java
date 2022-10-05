// -*- coding: utf-8 -*-

import java.awt.Color;

public class Mandelbrot extends Thread{
    final static int taille = 500;   // nombre de pixels par ligne (et par colonne)
    final static Picture image = new Picture(taille, taille);
    final static int max = 100_000; 

    private static Object unVerrou = new Object();
    final static int nbThreads = 4;
    private static volatile int prochaineLigne = 0;
    // C'est l'indice de la prochaine ligne à calculer

    public static void main(String[] args) throws Exception{
		final long début = System.nanoTime();

        // On créé les 4 threads et on les démarre
    	Mandelbrot[] mesThreads = new Mandelbrot[nbThreads];
    	for (int i = 0; i < nbThreads; i++){
    		mesThreads[i] = new Mandelbrot();
    		mesThreads[i].start();
    	}
		// On attend qu'ils aient tous terminé
    	for (int i = 0; i < nbThreads; i++){
			mesThreads[i].join();
		}

		final long fin = System.nanoTime();
		final long durée = (fin - début) / 1_000_000 ;
		System.out.println("Durée Totale = " + (long) durée / 1000 + " s.");

        image.show();
    }
    
    public int ligneSuivante(){
        int ligne;
        synchronized(unVerrou){ // Incrémentation atomique
            ligne = prochaineLigne;
            prochaineLigne++;
        }
        return ligne;
    }

    public void run()  {
		final long début = System.nanoTime();

		int ligne = ligneSuivante() ;
		while(ligne < taille){
			for (int i = 0; i<taille; i++){
				colorierPixel(i,ligne);
                synchronized(image){image.show();}
			}
            ligne = ligneSuivante() ;
		}
        
		final long fin = System.nanoTime();
		final long durée = (fin - début) / 1_000_000 ;
		System.out.println("Durée de "+this.getName()+" = " + (long) durée / 1000 + " s.");
    }    

    // La fonction colorierPixel(i,j) colorie le pixel (i,j) de l'image
    public void colorierPixel(int i, int j) {
        final Color gris = new Color(90, 90, 90) ;
        final Color blanc =  new Color(255, 255, 255);
        final double xc = -.5 ;
        final double yc = 0 ; // Le point (xc,yc) est le centre de l'image
        final double region = 2 ;
        /*
          La région du plan considérée est un carré de côté égal à 2.
          Elle s'étend donc du point (xc - 1, yc - 1) au point (xc + 1, yc + 1)
          c'est-à-dire du point (-1.5, -1) en bas à gauche au point (0.5, 1) en haut
          à droite
        */
		double a = xc - region/2 + region*i/taille;
		double b = yc - region/2 + region*j/taille;
		// Le pixel (i,j) correspond au point (a,b)
		if (mandelbrot(a, b, max)) image.set(i, j, gris);
		else image.set(i, j, blanc);
    }

    // La fonction mandelbrot(a, b, max) détermine si le point (a,b) est noir
    public static boolean mandelbrot(double a, double b, int max) {
        double x = 0;
		double y = 0;
		for (int t = 0; t < max; t++) {
            if (x*x + y*y > 4.0) return false; // Le point (a,b) est blanc
            double nx = x*x - y*y + a;
	    	double ny = 2*x*y + b;
	    	x = nx;
	    	y = ny;
        }
        return true; // Le point (a,b) est noir
    }
}


/* 
   java version "1.8.0_60"
   Java(TM) SE Runtime Environment (build 1.8.0_60-b27)
   Java HotSpot(TM) 64-Bit Server VM (build 25.60-b23, mixed mode)
   $ java -jar Mandelbrot.jar
   Durée de Thread-0 = 9 s.
   Durée de Thread-2 = 9 s.
   Durée de Thread-3 = 9 s.
   Durée de Thread-1 = 9 s.
   Durée Totale = 9 s.
   $
*/
