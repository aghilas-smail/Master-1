package TP2.EX2;

import java.util.ArrayList;

public class Clause {
    private int l1, l2;

    public Clause(int l1, int l2) {
        this.l1 = l1;
        this.l2 = l2;
    }

    public boolean isSatisfied(ArrayList<Integer> valuation){
        return l1 == valuation.get(Math.abs(l1) - 1) || l2 == valuation.get(Math.abs(l2) - 1);
    }

    public int getL1() {
        return l1;
    }

    public int getL2() {
        return l2;
    }
}
