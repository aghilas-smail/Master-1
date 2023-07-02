package TP1.EX2;

public class CalculE2 {
    public static double main(double n) {
        double result = calculE(n);
        return result;
    }

    private static double calculE(double M){
        double somme = 0;
        for (int i = 0; i < M; i++) {
            somme += findM();
        }
        return somme/M;
    }

    private static int findM(){
        double r1 = Math.random();
        double r2 = Math.random();
        int m = 2;
        while(r1 > r2){
            r1 = r2;
            r2 = Math.random();
            m++;
        }
        return m;
    }
}
