package TP1.EX2;

public class CalculE1 {
    public static double main(double n) {
        double result = estimerE(n);
        return result;
    }

    private static double estimerE(double n){
        double somme = 0;
        for (int i = 0; i < n; i++) {
            somme += findN();
        }
        return somme/n;
    }

    private static int findN(){
        double somme = 0;
        int n = 0;
        while(somme <= 1){
            double r = Math.random();
            somme += r;
            n++;
        }
        return n;
    }
}
