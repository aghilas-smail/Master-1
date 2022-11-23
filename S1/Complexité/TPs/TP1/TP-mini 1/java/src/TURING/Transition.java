package TURING;

import java.util.Objects;

public class Transition {

    public Etate EtatSource;
    public Etate EtatSuiv;
    public Symbole SymSource;
    public Symbole SymSuiv;
    public String direction;  // allez a gauche c'est négative et allez a droite est positive.


    public Transition(Symbole symSource, Etate etatSource, Symbole symSuiv, Etate etatSuiv, String direction) {
        SymSource = symSource;
        EtatSource = etatSource;
        SymSuiv = symSuiv;
        EtatSuiv = etatSuiv;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return " ( "+EtatSource+ " & " +SymSource+ " -> " +EtatSuiv+ " & " +SymSuiv+ " & " +direction + " ) " + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;     //O(n)
        if (o == null || getClass() != o.getClass()) return false;   //O(n)

        Transition that = (Transition) o;

        if (!Objects.equals(EtatSource, that.EtatSource)) return false;  //O(n)
        if (!Objects.equals(EtatSuiv, that.EtatSuiv)) return false;  //O(n)
        if (!Objects.equals(SymSource, that.SymSource)) return false;    //O(n)
        if (!Objects.equals(SymSuiv, that.SymSuiv)) return false;    //O(n)
        return Objects.equals(direction, that.direction);
    }

}
