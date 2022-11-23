package GRAPH;

import java.util.*;

public class Graph {

    /**
     * Cette fonction vérifie si un ensemble de sommets d'un graphe est une zone vide
     * @param graph la matrice qui représente le graphe
     * @param X l'ensemble à vérifier
     * @return vrai si l'ensemble X est une zone vide et faux sinon
     */
    public static boolean arrayIsVoid(int[][] graph ,ArrayList<Integer> X){
        for(int i:X){
            for(int j:X){
                if(graph[i][j] == 1) return false;
            }
        }
        return true;
    }

    /**
     * Cette fonction calcule la zone vide maximale X d'un graphe, tels que X n'est pas le sous-ensemble strict d'une autre zone vide du graphe
     * @param graph la matrice qui représente le graphe
     * @return la zone vide maximale
     */
    public static List<Integer> getMaximalVoid(int[][] graph){
        List<Integer> result = new ArrayList();
        for(int i =0; i<graph.length; i++){
            if(checkVoid(graph, result, i)) result.add(i);
        }
        return result;
    }

    // la taille du graphe dans la fonction "getAllMaximalVoids()"
    static int size;

    // la zone vide maximale pour chaque permutation de l'ensemble des sommets du graphe dans la fonction "getAllMaximalVoids()"
    static ArrayList<Integer> voidTemp = new ArrayList<>();

    // la zone vide maximum optimale dans la fonction "getAllMaximalVoids()"
    static List<Integer> voidMax = new ArrayList<>();

    // un vecteur de boolean qui permet de savoir si le i-ème elements de la permutation a été testé
    static boolean[] testedElements;

    /**
     * Cette fonction parcours toutes les permutations possible de l'ensemble des sommets du graphe et calcule à chaque fois la zone vide maximale, puis stocker la plus grande dans la variable "voidMax"
     * @param graph la matrice qui représente le graphe
     * @param level le i-éme élément de la permutation qu'on calcule.
     */
    public static void getAllMaximalVoids(int[][] graph, int level){
        // condition d'arrêt de la récursivité : si  on a atteint la fin de la permutation
        if(level>=size) {
            // mettre à jour voidMax si la zone vide voidTemp est plus grande
            if(voidTemp.size()>voidMax.size()) voidMax = (List<Integer>) voidTemp.clone();
            return;
        }
        // parcourir tous les sommets
        for(int i=0; i<size;i++){
            // si le n'a pas été testé (cela revient à dire que dans la permutation je prends un élément une seule fois)
            if(!testedElements[i] ) {
                testedElements[i] = true;
                // ajouter l'élément si voidTemp reste une zone vide
                if(checkVoid(graph, voidTemp, i)) voidTemp.add(i);
                // calculer la prochaine case
                getAllMaximalVoids(graph, level + 1);
                // l'élément n'est pas testé pour les prochaines permutation
                testedElements[i] = false;
                voidTemp.remove(Integer.valueOf(i));
            }
        }
    }

    /**
     * Cette fonction vérifie si on peut ajouter un élément à une zone vide d'un graphe
     * @param graph la matrice qui représente le graphe
     * @param list une zone vide du graphe
     * @param s l'élément à tester
     * @return vrai si l'élément s forme toujours une zone vide avec list et faux sinon
     */
    public static boolean checkVoid(int[][] graph, List<Integer> list, int s){
        for(int x: list){
            if(graph[s][x]==1) return false;
        }
        return true;
    }

    /**
     * Cette fonction calcule la zone vide maximum X d'un graphe, tels qu'il n'existe pas une autre zone vide dans le graphe avec une taille plus grande que la taille de X.
     * Le résultat retourner est optimal, mais la fonction n'est pas efficace en termes de temps de calcul surtout pour un graph de grande taille
     * @param graph la matrice qui représente le graphe
     * @return la zone vide maximum optimal
     */
    public static List<Integer> getMaxVoid(int[][] graph){
        size = graph.length;
        testedElements = new boolean[size];
        getAllMaximalVoids(graph, 0);
        return voidMax;
    }

    /**
     * Cette fonction calcule la zone vide maximum X d'un graphe
     * Cette fonction ne garantie pas l'optimalité du résultat, mais elle est très efficace en termes de temps de calcul
     * La méthode consiste à trier les sommets selon le nombre de leurs voisins, puis chercher la zone vide dans l'ordre décroissant
     * @param graph la matrice qui représente le graphe
     * @return la zone vide maximum non optimale
     */
    public static List<Integer> getIncompleteMaxVoid(int[][] graph){
        int n = graph.length;
        int[] nbNeighbours = new int[n];

        // calculer le nombre de voisins pour chaque sommet
        for(int i = 0; i< n; i++) {
            for (int j = 0; j < n; j++) {
                nbNeighbours[i]+=graph[i][j];
            }
        }

        // trier la liste
        ArrayList<Integer> sortedList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int minJ = 0;
            for (int j = 0; j <+ n; j++) {
                if((nbNeighbours[j]>=0 && nbNeighbours[j]<nbNeighbours[minJ]) || (nbNeighbours[minJ] == -1)) {
                    minJ = j;
                }
            }
            nbNeighbours[minJ] = -1;

            sortedList.add(minJ);
        }

        // calculer la zone vide maximum
        ArrayList<Integer> result = new ArrayList<>();
        for (int s : sortedList){
            if(checkVoid(graph, result, s)) result.add(s);
        }
        return result;
    }

    public static void main(String[] args){
        int [][] matrix =
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

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1); list.add(3); list.add(7); list.add(5);

        System.out.println(list + " est une zone vide : " + arrayIsVoid(matrix, list));
        System.out.println("zone vide maximale : " + getMaximalVoid(matrix));
        System.out.println("zone vide maximum complète = " + getMaxVoid(matrix));
        System.out.println("zone vide maximum incomplète = " + getIncompleteMaxVoid(matrix));

    }
}