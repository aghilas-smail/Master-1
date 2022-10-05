// -*- coding: utf-8 -*-

import java.awt.Color;

public class Mandelbrot {
    final static int taille = 500;   // nombre de pixels par ligne (et par colonne)
    final static Picture image = new Picture(taille, taille);
    // Il y a donc taille*taille pixels blancs ou noirs à déterminer
    final static int max = 100_000; 
    // C'est le nombre maximum d'itérations pour déterminer la couleur d'un pixel

    final static int nbThreads = 4;
    private final int maZone;
    
    public Mandelbrot(int zone){
        maZone = zone;
    }

    public static void main(String[] args) throws Exception{        
		final long début = System.nanoTime();
        
        // On créé et démarre les 4 threads
    	Thread[] mesThreads = new Thread[nbThreads];
    	for (int k = 0; k < nbThreads; k++){
            final int maZone = k;
    		mesThreads[k] = new Thread(new Runnable() {
                    public void run() {
                        final long début = System.nanoTime();
                        for (int ligne = maZone*taille/4 ; ligne < (maZone+1)*taille/4; ligne++) {
                            for (int i = 0; i<taille; i++){
                                colorierPixel(i,ligne);
                            }
                        }       
                        final long fin = System.nanoTime();
                        final long durée = (fin - début) / 1_000_000 ;
                        System.out.println("Durée de "+Thread.currentThread().getName()+" = " + (long) durée /1000 + " s.");
                    }
                });
    		mesThreads[k].start();
    	}
		// On attend un par un qu'ils aient tous terminé
    	for (int k = 0; k < nbThreads; k++){
			mesThreads[k].join();
		}

		final long fin = System.nanoTime();
		final long durée = (fin - début) / 1_000_000 ;
		System.out.println("Durée Totale = " + (long) durée /1000 + " s.");

        image.show();
    }

    // La fonction colorierPixel(i,j) colorie le pixel (i,j) de l'image
    public static void colorierPixel(int i, int j) {
        final Color gris = new Color(90, 90, 90) ;
        final Color blanc = new Color(255, 255, 255) ;
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


/* Test sur un Macbook Pro

   $ java -version
   java version "11.0.4" 2019-07-16 LTS
   Java(TM) SE Runtime Environment 18.9 (build 11.0.4+10-LTS)
   Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.4+10-LTS, mixed mode)
   $ make
   javac *.java
   jar cvmf MANIFEST.MF Mandelbrot.jar *.class
   manifeste ajouté
   ajout : Mandelbrot.class(entrée = 2507) (sortie = 1535)(compression : 38 %)
   ajout : Picture.class(entrée = 5689) (sortie = 3039)(compression : 46 %)
   rm *.class 
   $ java -jar Mandelbrot.jar
   Durée de Thread-0 = 3 s.
   Durée de Thread-3 = 3 s.
   Durée de Thread-1 = 14 s.
   Durée de Thread-2 = 15 s.
   Durée Totale = 15 s.
   $ sysctl hw.logicalcpu
   hw.logicalcpu: 8
   $ sysctl hw.physicalcpu
   hw.physicalcpu: 4

*/
