// -*- coding: utf-8 -*-

import java.awt.Color;

public class Mandelbrot {
    final static int taille = 500 ;   // nombre de pixels par ligne et par colonne
    final static Picture image = new Picture(taille, taille) ;
    // Il y a donc taille*taille pixels blancs ou gris à déterminer
    final static int max = 100_000 ;
    // C'est le nombre maximum d'itérations pour déterminer la couleur d'un pixel

    final static int nombre_thread = 4;

    // Focntion qui va lancé les 4 thread

    private final int maZone;
    public Mandelbrot(int zone) {
        this.maZone = zone;
    }

    public static void main(String[] args) throws InterruptedException {
        final long début = System.nanoTime() ;


        Thread[] MesThread = new Thread[nombre_thread];
        for (int i =0;i<nombre_thread; i ++) {
            final int maZone = i;
            MesThread[i] = new Thread(new Runnable() {
                public void run() {
                    final long debut = System.nanoTime();
                    for (int ligne = maZone * taille/4; ligne< (maZone+1) *taille/4; ligne++){
                        for (int k = 1; k<taille;k++) {
                            colorierPixel(ligne,k);
                        }
                    }
                    final long fin = System.nanoTime();
                    final long durée = (fin - debut) / 1_000_000;
                    System.out.println("la durée de chaque thread est " +Thread.currentThread().getName() + " = " +(long) durée / 1000 + "s.");
                }
            });
            MesThread[i].start();

        /*for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                colorierPixel(i,j) ;
            }
            // image.show();         // Pour visualiser l'évolution de l'image
        */}

        for (int k = 0; k < nombre_thread; k++){
        MesThread[k].join();
    }

    final long fin = System.nanoTime();
    final long durée = (fin - début) / 1_000_000 ;
		System.out.println("Durée Totale = " + (long) durée /1000 + " s.");
        image.show() ;
    }    

    // La fonction colorierPixel(i,j) colorie le pixel (i,j) de l'image en gris ou blanc
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
        double a = xc - region/2 + region*i/taille ;
        double b = yc - region/2 + region*j/taille ;
        // Le pixel (i,j) correspond au point (a,b)
        if (mandelbrot(a, b, max)) image.set(i, j, gris) ;
        else image.set(i, j, blanc) ; 
    }

    // La fonction mandelbrot(a, b, max) détermine si le point (a,b) est gris
    public static boolean mandelbrot(double a, double b, int max) {
        double x = 0 ;
        double y = 0 ;
        for (int t = 0; t < max; t++) {
            if (x*x + y*y > 4.0) return false ; // Le point (a,b) est blanc
            double nx = x*x - y*y + a ;
            double ny = 2*x*y + b ;
            x = nx ;
            y = ny ;
        }
        return true ; // Le point (a,b) est gris
    }
}


/* 
   $ make
   javac *.java 
   jar cvmf MANIFEST.MF Mandelbrot.jar *.class 
   manifeste ajouté
   ajout : Mandelbrot.class(entrée = 1697) (sortie = 1066)(compression : 37 %)
   ajout : Picture.class(entrée = 5689) (sortie = 3039)(compression : 46 %)
   rm *.class 
   $ java -version
   java version "11.0.3" 2019-04-16 LTS
   Java(TM) SE Runtime Environment 18.9 (build 11.0.3+12-LTS)
   Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.3+12-LTS, mixed mode)
   $ java -jar Mandelbrot.jar
   Durée = 35.851 s.
   ^C
*/
