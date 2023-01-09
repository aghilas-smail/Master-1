import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Cnf {

    public static List<ArrayList<String>> clauses =new ArrayList<>();
    public static int nbLiterals;
    public static int nbClauses;

    public Cnf(String path) throws IOException {

        BufferedReader bufferedReaderFormula = new BufferedReader(new FileReader(path));
        String line = bufferedReaderFormula.readLine();

        //Vérificaion de la bonne structure du fichier : test si la définition est bien construite
        if(line == null || !line.contains("p cnf ") || line.split(" ").length != 4) {
            System.out.println("Première ligne manquante ou incorrecte: p cnf nbLiteral nbClause");
            System.exit(2);
        }

        //On enregistre les paramètre
        String[] data = line.split(" ");
        nbLiterals = Integer.parseInt(data[2]);
        nbClauses  = Integer.parseInt(data[3]);

        //Construction des clauses
        for(int i = 0; i< nbClauses; i++) {
            if((line =  bufferedReaderFormula.readLine()) != null) {
                data = line.split(" ");

                //On vérifie que la clause soit terminé correctement
                if(!data[data.length-1].equals("0")) {
                    System.out.println("Erreur à la ligne: "+i+" (clause non terminée '0' manquant)");
                    System.exit(3);
                }

                //Construction de la claus
                ArrayList<String> literals = new ArrayList<>();
                for(int j = 0; j<data.length-1; j++) {
                    String literal = data[j];

                    //On vérifie que la clause ne possède pas plus de litéraux que défini
                    if(Integer.parseInt(literal) > nbLiterals) {
                        System.out.println("La ligne "+i+" possède plus de litérale que défini");
                        System.exit(4);
                    }
                    literals.add(literal);
                }
                clauses.add(literals);
            }
        }
        bufferedReaderFormula.close();

    }
}
