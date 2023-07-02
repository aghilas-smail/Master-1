package TP1.EX2;

import TP1.EX1.MonteCarlo1;

public class ComparaisonEx2 {
    public static void main(String[] args) {
        System.out.println("Comparaison des méthodes pour le calcul de e:");
        System.out.println("On cherche le nombre d'itérations pour approcher de la valeur de e à 3 décimales.");
        double e = 2.7182818284;
        int n = 10;
        while(Math.abs(CalculE1.main(n) - e) > 0.0001){
            n *= 10;
        }
        System.out.println("Il faut " + n + " itérations pour la 1ère méthode");

        int m = 10;
        while(Math.abs(CalculE1.main(m) - e) > 0.0001){
            m *= 10;
        }
        System.out.println("Il faut " + m + " itérations pour la 2ème méthode");
    }
}
