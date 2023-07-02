package TP1;

import TP1.EX1.ComparaisonEx1;
import TP1.EX1.MonteCarlo1;
import TP1.EX1.MonteCarlo2;
import TP1.EX2.CalculE1;
import TP1.EX2.CalculE2;
import TP1.EX2.ComparaisonEx2;
import TP1.EX3.EX3;

public class Main {
    // Les compaisons sont commentées car elles prennent un peu de temps
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\nRésultats du TP\n");
        System.out.println("Exercice 1:");
        testExercice1();
//
    }

    private static void testExercice1() throws InterruptedException {
        double n = 10_000_000;
        System.out.printf("Valeur de Pi avec 1ère méthode:\t");
        System.out.println(MonteCarlo1.main(n, true));
        System.out.printf("Valeur de Pi avec 2ème méthode:\t");
        System.out.println(MonteCarlo2.main(n, true));
    }


}
