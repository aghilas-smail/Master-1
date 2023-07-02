package TP2.EX1;

/*
    2:  1.0
    12: 11.85
    22: 22.42
    32: 35.2
    42: 41.66
    52: 56.68
    62: 61.11
    72: 59.81
    82: 76.11
    92: 95.21
 */

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        System.setProperty("org.graphstream.ui", "swing");
//        testItenfcNbSommet(100, 10);
        testTempsCouverture();
//        testQuestion1();
//        testChemin();
//        testGrille();
    }

    private static void testQuestion1() throws FileNotFoundException, InterruptedException {
        Graphe graphe = Graphe.genererGraphe(20, 1);
        graphe.afficherGraphe();
        Thread.sleep(1000);
        System.out.println(graphe.marcheAleatoire(10));
    }

    private static void testGrille() throws FileNotFoundException, InterruptedException {
        Graphe graphe = Graphe.genererGrille(5,5);
        graphe.afficherGraphe();
        graphe.colorerPointsGrille(5);
        Thread.sleep(3000);
        System.out.println(graphe.marcheAleatoireGrille(5));
    }

    private static void testChemin() throws FileNotFoundException {
        Graphe graphe = Graphe.genererGrapheAvecChemin(10, 5, 1);
        graphe.afficherGraphe();
    }

    private static void testTempsCouverture() throws FileNotFoundException {
        Graphe graphe = Graphe.genererGraphe(15, 1);
        graphe.afficherGraphe();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(graphe.tempsCouverture());
    }

    private static double testMarcheAleatoire(int nbTests, int nbSommets) throws FileNotFoundException, InterruptedException {
        Graphe graphe = Graphe.genererGraphe(nbSommets, 1, false);
        double somme = 0;
        for (int i = 0; i < nbTests; i++) {
            somme += graphe.marcheAleatoire(nbSommets/2);
        }
        return somme / nbTests;
    }

    private static void testItenfcNbSommet(int nbSommetMax, int pas) throws FileNotFoundException, InterruptedException {
        ArrayList<Double> valeurs = new ArrayList<>();
        for (int i = 2; i <nbSommetMax ; i+=pas) {
            double valeur = testMarcheAleatoire(100, i);
            System.out.println(valeur);
            valeurs.add(valeur);
        }

        for (int i = 0; i < valeurs.size(); i++) {
            System.out.println("SommetMax: " + i*pas + " nb_It_Moy: " + valeurs.get(i));
        }
    }
}
