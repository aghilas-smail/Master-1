import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LiterauxValidator {

    /**
     * On test la validité des Literaux
     */
    public boolean validity;

    /**
     * @param path chemin du fichier contenant les literaux
     */
    public LiterauxValidator(List<ArrayList<String>> clauses, int nbLiterals, String path) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line = "";

        if ((line = reader.readLine()) == null)
            throw new Exception("Aucune ligne trouvée");

        String[] datas = line.split(" ");

        //Vérification si le nombre de litéraux est correcte
        for (String data : datas) {
            if (Math.abs(Integer.parseInt(data)) > nbLiterals)
                throw new Exception("Il existe plus de litéraux: " + data + " que le CNF autorise: " + nbLiterals );
        }
        //Vérification si pour chaque clause ,
        //                    pour chaque literaux
        //                         le litéral existe bien
        for (ArrayList<String> clause : clauses) {
            validity = false;
            for (int i = 0; i < datas.length; i++) {
                //Ne pas tester l'égaliter car un litéral peut être négatif ! -1 =/= 1
                if (clause.contains(datas[i])) validity = true;
            }
        }
    }
}
