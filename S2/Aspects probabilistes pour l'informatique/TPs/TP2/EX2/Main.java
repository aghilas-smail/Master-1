package TP2.EX2;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        Formule formule = new Formule(3);
//        formule.addClause(new Clause(-1, 2));
//        formule.addClause(new Clause(1, 3));
//        ArrayList<Integer> solution = new ArrayList<>();
//        solution.add(1);
//        solution.add(-2);
//        solution.add(-3);
//        System.out.println(formule.evaluer(solution));

        Formule formule = Formule.genererFormule(20, 10);
        Solveur solveur = new Solveur(formule, 1000000);
        System.out.println(solveur.solve());
    }
}
