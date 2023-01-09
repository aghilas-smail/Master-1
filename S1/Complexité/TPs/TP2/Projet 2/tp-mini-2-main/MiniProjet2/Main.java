import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static boolean satChecker(String formuleFile, String entreeFile) {
        // Lecture de fichier.
        File myObj = new File(entreeFile);
        int nbVariables = 0;
        boolean[] entree = new boolean[0];
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] s = data.split(" ");

            // Une condition pour les valeur négative dans le fichier.
            nbVariables = s.length;
            entree = new boolean[nbVariables];
            for (int i = 0; i < nbVariables; i++) {
                String element = s[i];
                if (element.charAt(0) != '-') {
                    int variable = Integer.parseInt(element);
                    entree[variable - 1] = true;
                }
            }
        }
        myObj = new File(formuleFile);
        myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        myReader.nextLine();
        boolean b = true;
        while (myReader.hasNextLine() && b) {
            String data = myReader.nextLine();
            String[] s = data.split(" ");
            int i = 0;
            b = false;
            int l = Integer.parseInt(s[i]);
            while(l != 0 && !b){
                if(l<0) b = b | !entree[(-1) * l - 1];
                else b = b | entree[l - 1];
                i++;
                l = Integer.parseInt(s[i]);
            }

        }
        myReader.close();
        return b;
    }

    /**
     * Cette fonction permet de créer un fichier s'il n'existe pas, puis écrit les clauses dans ce dernier sous
     * le format DIMACS
     * @param fileName nom du fichier à créer
     * @param nbVariables nombres de variables de l'instance du problème SAT
     * @param clauses les clauses
     */
    public static void createDIMACS_CNFFile(String fileName, int nbVariables, List<Clause> clauses){
        // création du fichier
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }

        // écrire les clauses dans le fichier créé en respectant la forme DIMACS
        try {
            FileWriter myWriter = new FileWriter(fileName);
            // la première ligne qui définie la forme de notre formule (ici forme normale conjonctive)
            // ainsi que le nombre de variables et de clauses
            myWriter.write("p cnf "+ nbVariables + " "+ clauses.size() + System.getProperty( "line.separator" ));
            // écrire les clauses
            for (Clause clause:clauses) {
                myWriter.write(clause + "0" + System.getProperty( "line.separator" ));
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    /**
     * Cette fonction permet de réduire une instance du problème zone vide vers une instance du problème SAT
     * @param graph le graphe
     * @return une liste de clauses qui représente l'instance du problème SAT
     */
    public static ArrayList<Clause> stableToSAT(int[][] graph){
        ArrayList<Clause> clauses = new ArrayList<Clause>();
        for(int i=0; i<graph.length; i++){
            for(int j=i; j< graph.length; j++){
                if(graph[i][j] == 1) {
                    Clause c = new Clause();
                    c.addLiteral((-1)*(i+1));
                    c.addLiteral((-1)*(j+1));
                    clauses.add(c);
                }
            }
        }
        return clauses;
    }

    /**
     * Cette fonction permet de générer un graphe aléatoire de taille size
     * @param size la taille du graphe à générer
     * @return un graphe aléatoire de taille size
     */
    public static int[][] randomGraph(int size){
        int[][] graph = new int[size][size];
        for(int i=0; i<size; i++){
            for(int j=i; j<size; j++){
                if(i == j) graph[i][j] = 0;
                else {
                    double r = Math.random();
                    if(r > 0.5) {
                        graph[i][j] = 1;
                        graph[j][i] = 1;
                    }
                    else {
                        graph[i][j] = 0;
                        graph[j][i] = 0;
                    }
                }
            }
        }
        return graph;
    }

    public static void main(String[] args) {
        //System.out.println(satChecker("res/formule1", "res/entree1"));
        int [][] graph =
                {
                        {0,1,1,1,0,0,0,0,0},
                        {1,0,0,0,0,0,0,0,0},
                        {1,0,0,0,0,0,0,0,0},
                        {1,0,0,0,1,0,0,0,0},
                        {0,0,0,1,0,1,1,1,1},
                        {0,0,0,0,1,0,0,0,0},
                        {0,0,0,0,1,0,0,0,0},
                        {0,0,0,0,1,0,0,0,0},
                        {0,0,0,0,1,0,0,0,0}
                };
        ArrayList<Clause> clauses = stableToSAT(graph);
        createDIMACS_CNFFile("zoneVide.txt", graph.length, clauses);

        graph = randomGraph(10);
        long débutDuTri = System.nanoTime();
        clauses = stableToSAT(graph);
        createDIMACS_CNFFile("zoneVide1.txt", graph.length, clauses);
        long finDuTri = System.nanoTime();

        long duréeDuTri = (finDuTri - débutDuTri) / 1_000_000;
        System.out.println("obtenu en " + duréeDuTri + " millisecondes.") ;
    }
}








