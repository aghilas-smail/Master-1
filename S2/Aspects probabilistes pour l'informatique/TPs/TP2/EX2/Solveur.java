package TP2.EX2;

import java.util.ArrayList;

public class Solveur {
    private Formule formule;
    private ArrayList<Integer> valuation;
    private int nbEtapes;

    public Solveur (Formule formule, int nbEtapes){
        this.formule = formule;
        this.valuation = new ArrayList<>();
        this.nbEtapes = nbEtapes;
    }

    public ArrayList<Integer> solve(){
        initValuation();
        int i = 0;
        while(!formule.evaluer(valuation) && i < nbEtapes){
            ArrayList<Clause> unSatClauses = formule.getUnSatClauses(valuation);
            int index = (int) ((Math.random() * (unSatClauses.size()-1)));
            int litNumber;
            if(Math.random() < 0.5)
                litNumber = unSatClauses.get(index).getL1();
            else
                litNumber = unSatClauses.get(index).getL2();
            valuation.set(Math.abs(litNumber)-1, valuation.get(Math.abs(litNumber)-1)*-1);
            i++;
        }
        if(i < nbEtapes)
            return valuation;
        else{
            System.out.println("UNSAT après " + i + " étapes");
            return valuation;
        }
    }

    private void initValuation(){
        for (int i = 0; i < formule.getNbVar(); i++) {
            valuation.add(-(i+1));
        }
    }
}
