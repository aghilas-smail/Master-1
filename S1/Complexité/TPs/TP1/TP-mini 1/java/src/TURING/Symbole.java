package TURING;

import java.util.Objects;

public class Symbole {

    public final String value;

    public Symbole(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Symbole symbole = (Symbole) o;

        return Objects.equals(value, symbole.value);
    }

    @Override
    public String toString() {
        return "Symbole{" +
                "value='" + value + '\'' +
                '}';
    }
}
