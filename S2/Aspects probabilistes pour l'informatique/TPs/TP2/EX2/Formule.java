package TP2.EX2;

import java.util.ArrayList;

public class Formule {
    private int nbClauses, nbVar;
    private ArrayList<Clause> clauses;

    public Formule(int nbVar){
        this.clauses = new ArrayList<>();
        this.nbVar = nbVar;
    }

    public void addClause(Clause clause){
        this.clauses.add(clause);
        nbClauses++;
    }


    public static Formule genererFormule(int nbClauses, int nbVar){
        Formule formule = new Formule(nbVar);
        for (int i = 0; i < nbClauses; i++) {
            int l1 = (int) ((Math.random() * (nbVar)) + 1);
            int l2 = (int) ((Math.random() * (nbVar)) + 1);
            if(Math.random() < 0.5)
                l1 *= -1;
            if(Math.random() < 0.5)
                l2 *= -1;
            formule.addClause(new Clause(l1, l2));
        }
        return formule;
    }

    public boolean evaluer(ArrayList<Integer> valuation){
        boolean sat = true;
        for(Clause clause : clauses){
            if(!clause.isSatisfied(valuation))
                sat = false;
        }
        return sat;
    }

    public ArrayList<Clause> getUnSatClauses(ArrayList<Integer> valuation){
        ArrayList<Clause> unSatClauses = new ArrayList<>();
        for(Clause clause : clauses){
            if(!clause.isSatisfied(valuation)){
                unSatClauses.add(clause);
            }
        }
        return unSatClauses;
    }

    public int getNbClauses() {
        return nbClauses;
    }

    public int getNbVar() {
        return nbVar;
    }

    public ArrayList<Clause> getClauses() {
        return clauses;
    }
}
