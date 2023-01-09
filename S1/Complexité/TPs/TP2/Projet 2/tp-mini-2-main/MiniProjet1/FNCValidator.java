public class FNCValidator {

    static long duree;

    static final String nameFile = "v155"; // à changer
    static final String sufix = "model"; //true, false, model
    static final String pathCnf = "MiniProjet1\\CNF-files\\" + nameFile +".cnf";
    static final String pathLiteraux = "MiniProjet1\\Literaux\\"+ nameFile +"_"+ sufix + ".txt";


    public static void main(String[] args) throws Exception {
        resetClock(); // lancement du chrono

        //Construire un objet JAVA stockant les informations lu du fichier CNF
        Cnf cnf = new Cnf(pathCnf);
        System.out.println("[duree ms] construction CNF : "+ (System.currentTimeMillis() - duree));

        //Valider le CNF via les Litéraux donnés
        resetClock();
        LiterauxValidator literauxValidator = new LiterauxValidator(cnf.clauses, cnf.nbLiterals,pathLiteraux);
        if(! literauxValidator.validity)
            System.out.println("Les litéraux donnés ne sont pas solution du CNF donné");
        System.out.println("Les litéraux donnés sont une solution du CNF donné");

        System.out.println("[duree ms] validation Litéraux : "+ (System.currentTimeMillis() - duree));


    }

    private static void resetClock() {
        duree = System.currentTimeMillis();
    }


}
