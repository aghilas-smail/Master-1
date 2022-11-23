package TURING;

import java.util.ArrayList;

public class Case {

    public Symbole symbole;
    public Case CaseGauche;
    public Case CaseDroit;

    public Case(Case CaseGauche, Symbole symbole, Case CaseDroit) {
        this.symbole = symbole;
        this.CaseGauche = CaseGauche;
        this.CaseDroit = CaseDroit;
    }

    public Case(Symbole symbole, Case CaseDroit) {
        this.symbole = symbole;
        this.CaseGauche = null;
        this.CaseDroit = CaseDroit;
        CaseDroit.CaseGauche = this;
    }

    public Case(Case CaseGauche, Symbole symbole, Symbole SymboleBlanc ) {
        this.symbole = symbole;
        this.CaseGauche = CaseGauche;
        this.CaseDroit = new Case(SymboleBlanc, this);
        CaseGauche.CaseDroit = this;
    }

    public Case(Case currentCase, Symbole symbole) {
        this.symbole = symbole;
        this.CaseGauche = currentCase;
        this.CaseDroit = null;
        CaseGauche.CaseDroit = this;
    }

    public Case(Symbole symbol, Symbole SymboleBlanc) {
        this.symbole = symbol;
        this.CaseGauche = new Case(null, SymboleBlanc, this);
        this.CaseDroit = new Case(this, SymboleBlanc, (Case) null);
    }

    public Case(ArrayList<Etate> finalEtates, Case currentCase) {
    }


    public String ToString() {
        String str = symbole.toString();
        if(CaseGauche != null) str = CaseGauche +", "+str;
        if(CaseDroit != null) str = str+", "+ CaseDroit;
        return str;
    }
}
