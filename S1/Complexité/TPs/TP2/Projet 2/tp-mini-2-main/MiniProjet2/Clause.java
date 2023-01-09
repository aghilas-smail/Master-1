import java.util.ArrayList;
import java.util.List;

public class Clause {

    private ArrayList<Integer> literals;

    public Clause(){
        this.literals = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getLiterals() {
        return literals;
    }

    public void addLiteral(int l){
        this.literals.add(l);
    }

    public String toString(){
        String s = "";
        for(int l : literals){
            s += l + " ";
        }
        return s;
    }
}
