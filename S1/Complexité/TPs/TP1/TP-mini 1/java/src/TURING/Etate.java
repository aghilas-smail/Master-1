package TURING;

import java.util.Objects;

public class Etate {

     public final String value;

    public Etate(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Etat{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  //O(n)
        if (o == null || getClass() != o.getClass()) return false;   //O(n)

        Etate etate = (Etate) o;

        return Objects.equals(value, etate.value);
    }

}
