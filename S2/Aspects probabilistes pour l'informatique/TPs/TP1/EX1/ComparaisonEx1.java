package TP1.EX1;

public class ComparaisonEx1 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Comparaison des méthodes pour le calcul de Pi:");
        System.out.println("On cherche le nombre d'itérations pour approcher de la valeur de Pi à 3 décimales.");
        double pi = 3.14159265358979;
        int n = 10;
        while(Math.abs(MonteCarlo1.main(n, false) - pi) > 0.0001){
            n *= 10;
        }
        System.out.println("Il faut " + n + " itérations pour la 1ère méthode");

        int m = 10;
        while(Math.abs(MonteCarlo2.main(m, false) - pi) > 0.0001){
            m *= 10;
        }
        System.out.println("Il faut " + m + " itérations pour la 2ème méthode");
    }
}
