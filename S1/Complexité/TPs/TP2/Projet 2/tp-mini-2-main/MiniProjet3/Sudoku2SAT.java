import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sudoku2SAT {
    private static Set<List<Integer>> clauses;
    private static int[][] sudoku;
    private static int[][][] correspondant;
    private static boolean isBonne_taille = true;


    //Les Fonctions de clauses :

    /**
     * Fonction principal qui va donné une grille de soduko et qui renverra dans un fichier qui l'appliquera à l'aide d'un solveur SAT pour résoudre le problème
     *
     * @param data
     * @return
     * @throws IOException
     */
    static String convertion(int[][] data) throws IOException {


        sudoku = data;
        clauses = new HashSet<>();
        if (isBonne_taille) {
            initValeurCorrespondant();
        }
        /** Go through sudoku reduction to SAT */

        /**  Passez par la réduction du sudoku au SAT */
        manipulationUneParLigne();
        manipulationUneParColonne();
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku.length; j++) {
                if (sudoku[i][j] != 0)
                    manipulationNumeroExistant(sudoku[i][j], i + 1, j + 1); // permet de mettre dans le cause ceux qui existent deja aux endroits qui correspondent
                else {
                    manipulationAuMoinUnNuméro(i + 1, j + 1);
                    manipulationAuMaxUnNuméro(i + 1, j + 1);
                }
            }
        }
        int regionX = (int) Math.ceil(Math.sqrt(sudoku.length)); //prend le plus petit entier égal au nombre donné
        int regionY = (int) Math.floor(Math.sqrt(sudoku.length)); //prend le plus grand entier égal au nombre donné
        for (int i = 1; i <= sudoku.length; i += regionX) {
            for (int j = 1; j <= sudoku.length; j += regionY) {
                manipulationDesRegions(i, j, regionX, regionY);
            }
        }
        /** Génération du fichier SAT  */
        String name = "sudoku2SAT-" + System.currentTimeMillis() + ".txt";
        ecriture(name);
        return name;
    }

    /**
     * Il est utilise si le sudoku a la bonne taille.Permet de construire un tableau de 3 dimension pour stocker les valeurs correspondantes
     * pour les couples ligne , colonne et nombre.
     */
    private static void initValeurCorrespondant() {
        correspondant = new int[sudoku.length][sudoku.length][sudoku.length];
        int value = 1;
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku.length; j++) {
                for (int k = 0; k < sudoku.length; k++) {
                    correspondant[i][j][k] = value++;
                }
            }
        }
    }
    /**
     * Gérer au moins un par ligne : au moins chaque numéro sera présent sur chaque ligne
     * et donc pour chaque couple (ligne, nombre) ajouter une clause qui dira que le nombre doit
     * être dans cette ligne c'est à dire au moins présent dans une des colonnes de cette ligne
     */
    private static void manipulationUneParLigne() {
        for (int i = 1; i <= sudoku.length; i++) {
            for (int k = 1; k <= sudoku.length; k++) {
                List<Integer> c = new ArrayList<>();
                for (int j = 1; j <= sudoku.length; j++) {
                    c.add(getLitteral(k, i, j));
                }
                clauses.add(c);
            }
        }
    }
    /**
     * Gérer au moins un par colonne : au moins chaque numéro sera présent sur chaque colonne
     * et donc pour chaque couple (colonne, nombre) ajouter une clause qui dira que le nombre doit
     * être dans cette colonne c'est à dire au moins présent dans une des lignes de cette colonne
     */
    private static void manipulationUneParColonne() {
        for(int j = 1; j<=sudoku.length; j++) {
            for(int k = 1; k<=sudoku.length; k++) {
                List<Integer> c = new ArrayList<>();
                for(int i = 1; i<=sudoku.length; i++) {
                    c.add(getLitteral(k, i, j));
                }
                clauses.add(c);
            }
        }
    }
    /**
     * Gérer le numéro existant : si le numéro est déjà présent dans le sudoku de base, appliquez des axiomes
     * @param k est la valeur du nombre
     * @param i est la valeur de la ligne
     * @param j est la valeur de la colonne
     */
    private static void manipulationNumeroExistant(int k, int i, int j) {

        /** Il faut le prendre (ajouter une clause de taille 1, avec le littéral correspondant à "mettre le nombre k sur [i,j]") */
        List<Integer> c = new ArrayList<>();
        c.add((getLitteral(k, i, j)));
        clauses.add(c);
        /** Il ne doit plus avoir le numéro k sur la ligne i */
        for(int iprime = 1; iprime<=sudoku.length; iprime++) {
            if(i == iprime) continue;
            c = new ArrayList<>();
            c.add(-(getLitteral(k, iprime, j)));
            clauses.add(c);
        }
        /** Il ne doit pas y avoir de nouveau k à la ligne j */
        for(int jprime = 1; jprime<=sudoku.length; jprime++) {
            if(j == jprime) continue;
            c = new ArrayList<>();
            c.add(-(getLitteral(k, i, jprime)));
            clauses.add(c);
        }
        /** Il ne doit pas y avoir de nombre autre que k sur le carré [i,j] */
        for(int kprime = 1; kprime<=sudoku.length; kprime++) {
            if(k == kprime) continue;
            c = new ArrayList<>();
            c.add(-(getLitteral(kprime, i, j)));
            clauses.add(c);
        }
    }
    /**
     *  Au moins un numéro sera présent dans ce cas
     * et pour chaque valeur k représentant une valeur numérique ajouter une clause qui dira qu'au moins
     * un numéro doit être pressé dans ce cas
     */
    private static void manipulationAuMoinUnNuméro(int i, int j) {
        List<Integer> c = new ArrayList<>();
        for(int k = 1; k<=sudoku.length; k++) {
            c.add(getLitteral(k, i, j));
        }
        clauses.add(c);
    }
    /**
     *  Un maximum d'un nombre sera présent dans ce cas
     * et pour chaque valeur k représentant une valeur numérique ; pour chaque valeur k' représentant une valeur numérique différente
     * ajouter une clause qui dira qu'au plus un numéro doit être pressé dans ce cas
     */
    private static void manipulationAuMaxUnNuméro(int i, int j) {
        for(int k = 1; k<=sudoku.length; k++) {
            for(int kprime = k+1; kprime<=sudoku.length; kprime++) {
                List<Integer> c = new ArrayList<>();
                c.add(-getLitteral(k, i, j));
                c.add(-getLitteral(kprime, i, j));
                clauses.add(c);
            }
        }
    }
    /**
     * Gérer la région : si un nombre k existe dans une région, alors il ne doit pas exister dans les autres cellules de la même région
     * @param i la coordonnée de ligne qui représente la ligne supérieure de la région
     * @param j la coordonnée de colonne qui représente la colonne supérieure de la région
     * @param regionX est la taille horizontale d'une région
     * @param regionY est la taille verticale d'une région
     */
    private static void manipulationDesRegions(int i, int j, int regionX, int regionY) {
        for(int iprime = i; iprime<=regionX; iprime++) {
            for(int jprime = j; jprime<=regionY; jprime++) {
                List<Integer> c = new ArrayList<>();
                for(int k = 1; k<sudoku.length; k++) {
                    c.add(-(getLitteral(k, iprime, jprime)));
                }
                clauses.add(c);
            }
        }
    }
    /**
     * Donnait un couple (nombre, coordonnée de ligne, coordonnée de colonne), renvoie sa valeur correspondante sous forme littérale pour SAT
     * @param k un nombre
     * @param i la coordonnée de ligne
     * @param j la coordonnée de la colonne
     * @return la valeur littérale correspondante pour ce couple
     */
    private static int getLitteral(int k, int i, int j) {
        if(isBonne_taille)
            return correspondant[i-1][j-1][k-1] ;
        else return Integer.parseInt(new String(""+i+j+k));

    }
    /** Main */
    public static void main(String[] args) throws Exception {
        //monexectuable arg1 arg2
        if(args.length != 1 && args.length != 2) {
            args =new String[2];
            args[0] = "MiniProjet3/all_sudoku/sudokutest";
            //System.out.println("Required at least one argument: path to sudoku file [*]\n* will turn off the requirement to only have (n^6) literals, for a more understandable output");
            //System.exit(2);
        }

        if(args.length == 2) isBonne_taille = false;
        long time = System.currentTimeMillis();
        int[][] sudoku = lecture(args[0]);
        time = System.currentTimeMillis() - time;
        System.out.println("[Converted Data to Java Object in "+time+"ms]");
        time = System.currentTimeMillis();
        String name = convertion(sudoku);
        time = System.currentTimeMillis() - time;
        System.out.println("[Reduced Sudoku Solving System to SAT in "+time+"ms]");
        System.out.println(">Output SAT formulas in "+name);
    }
    //Manipulation des fichiers :

    /**
     * Ecriture des clauses dans un fichier .txt
     *
     * @param name
     * @throws IOException
     */
    private static void ecriture(String name) throws IOException {
        File outFile = new File("MiniProjet3/SAT/"+name);
        FileWriter myWriter = new FileWriter(outFile);
        int nbLiterals;
        if (isBonne_taille == true) {
            nbLiterals = (int) Math.pow(Math.sqrt(sudoku.length), 6);
        } else {

            nbLiterals = Integer.parseInt(new String("" + sudoku.length + sudoku.length + sudoku.length));
        }
        myWriter.write("p cnf " + nbLiterals + " " + clauses.size() + "\n");
        for (List<Integer> clause : clauses) {
            for (int lit : clause) {
                myWriter.write(lit + " ");
            }
            myWriter.write("0\n");
        }
        myWriter.close();
    }

    /**
     * Lire la lecture d'un fichier .txt et implementation d'une matrice qui prend en valeur le sudoku
     * @param file
     * @return la matrice du sudoku
     * @throws IOException
     */
    private static int[][] lecture(String file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String ligne = "";
        int longueur = -1;
        int compteur_ligne = -1;
        int[][] matrice = new int[0][0];
        while ((ligne = bufferedReader.readLine()) != null) {
            compteur_ligne++;
            String[] séparationLigne = ligne.split(" ");
            if (longueur == -1) {
                longueur = séparationLigne.length;
                matrice = new int[longueur][longueur];
            } else if (longueur != séparationLigne.length) {
                System.out.println("Sudoku file is not well structured. The program was waiting a n² by n² matrice");
                System.exit(3);
            }
            for (int i = 0; i < séparationLigne.length; i++) {
                matrice[compteur_ligne][i] = Integer.parseInt(séparationLigne[i]);
            }
        }
        bufferedReader.close();
        return matrice;
    }

}
