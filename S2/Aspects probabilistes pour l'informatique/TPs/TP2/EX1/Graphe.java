package TP2.EX1;

import org.graphstream.graph.implementations.MultiGraph;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Graphe {
    private HashMap<Integer, Sommet> sommets;
    private Sommet sommetCourant;
    private MultiGraph graph;
    private boolean isDisplayGraph;

    public Graphe() throws FileNotFoundException {
        this(true);
    }
    public Graphe(boolean isDisplayGraph) throws FileNotFoundException {
        this.isDisplayGraph = isDisplayGraph;
        sommets = new HashMap<>();
        Scanner sc = new Scanner(new File("src/TP2/EX1/graph.css"));
        StringBuilder sb = new StringBuilder();
        while(sc.hasNext()){
            sb.append(sc.next());
        }
        if(isDisplayGraph){
            this.graph = new MultiGraph("Graphe");
            graph.setAttribute("ui.stylesheet", sb.toString());
        }

    }

    public void addSommet(Sommet sommet){
        sommets.put(sommet.getIndex(), sommet);
    }

    public Sommet getSommet(int index){
        return sommets.get(index);
    }

    public boolean isDisplayGraph(){ return isDisplayGraph;}

    public static Graphe genererGraphe(int nbSommets, double densite) throws FileNotFoundException {
       return Graphe.genererGraphe(nbSommets, densite, true);
    }
    public static Graphe genererGraphe(int nbSommets, double densite, boolean isDisplayGraph) throws FileNotFoundException {
        Graphe graphe = new Graphe(isDisplayGraph);
        for (int i = 0; i < nbSommets; i++) {
            Sommet sommet = new Sommet(i);
            graphe.addSommet(sommet);
        }
        for (int i = 0; i < nbSommets; i++) {
            Sommet sommet = graphe.getSommet(i);
            for (int j = 0; j < nbSommets; j++) {
                if(i != j){
                    double rand = ThreadLocalRandom.current().nextDouble();
                    if(rand <= densite){
                        sommet.addVoisin(graphe.getSommet(j));
                    }
                }
            }
        }
        if(isDisplayGraph){
            graphe = generateGraphContent(graphe);
        }
        return graphe;
    }

    public static Graphe genererChemin(int longeur) throws FileNotFoundException {
        Graphe graphe = new Graphe();
        return Graphe.genererCheminAvecIndice(graphe,0, longeur);
    }

    private static Graphe genererCheminAvecIndice(Graphe graphe, int indiceDepart, int longeur) throws FileNotFoundException {
        int indiceFin = indiceDepart + longeur;
        for (int i = indiceDepart; i < indiceFin; i++) {
            graphe.addSommet(new Sommet(i));
        }
        for (int i = indiceDepart; i < indiceFin - 1; i++) {
            int j = i+1;
            graphe.getSommet(i).addVoisin(graphe.getSommet(j));
            graphe.getSommet(j).addVoisin(graphe.getSommet(i));

        }
        return generateGraphContent(graphe);
    }

    public static Graphe genererGrapheAvecChemin(int nbSommets, int tailleChemin, double densite) throws FileNotFoundException {
        Graphe graphe = Graphe.genererGraphe(nbSommets, 1);
        graphe = Graphe.genererCheminAvecIndice(graphe, graphe.getSize()-1, tailleChemin);
        return graphe;
    }

    private static Graphe generateGraphContent(Graphe graphe){
        MultiGraph graph = graphe.getGraph();
        for (int i = 0; i < graphe.getSize(); i++) {
            Sommet sommet = graphe.getSommet(i);
            if(graph.getNode(sommet.getIndex()+"") == null)
                graphe.getGraph().addNode(sommet.getIndex() + "");
        }
        for (int i = 0; i < graphe.getSize(); i++) {
            Sommet sommet = graphe.getSommet(i);
            for (int j = 0; j < sommet.getVoisins().size(); j++) {
                String iS = i+"";
                String jS = sommet.getVoisins().get(j).getIndex()+"";
                if(graph.getEdge(iS+"_"+jS) == null)
                    graph.addEdge(iS+"_"+jS, iS, jS);
            }
        }
        return graphe;
    }

    public static Graphe genererGrille(int x, int y) throws FileNotFoundException {
        Graphe graphe = new Graphe();
        for (int i = 0; i < x*y; i++) {
            graphe.addSommet(new Sommet(i));
        }

//        for (int i = 0; i < (x * (y-1))-1; i++) {
//            Sommet sommet1 = graphe.getSommet(i);
//            Sommet sommet2 = graphe.getSommet(x*y+1);
//            Sommet sommet3 = graphe.getSommet(x*(y+1));
//            sommet1.addVoisin(sommet2);
//            sommet2.addVoisin(sommet1);
//            sommet1.addVoisin(sommet3);
//            sommet3.addVoisin(sommet1);
//        }

        for (int i = 0; i < x-1; i++) {
            for (int j = 0; j < y-1; j++) {
                Sommet sommet1 = graphe.getSommet(i*x + j);
                Sommet sommet2 = graphe.getSommet((i+1)*x + j);
                Sommet sommet3 = graphe.getSommet(i*x + j+1);
                sommet1.addVoisin(sommet2);
                sommet2.addVoisin(sommet1);
                sommet1.addVoisin(sommet3);
                sommet3.addVoisin(sommet1);
            }
        }
        for (int i = 0; i < x-1; i++) {
            int index1 = (x-1) * x + i;
            int index2 = (x-1) * x + i + 1;
            Sommet sommet1 = graphe.getSommet(index1);
            Sommet sommet2 = graphe.getSommet(index2);
            sommet1.addVoisin(sommet2);
            sommet2.addVoisin(sommet1);

            int index3 = i * x + (x-1);
            int index4 = (i+1) * x + (x-1);
            Sommet sommet3 = graphe.getSommet(index3);
            Sommet sommet4 = graphe.getSommet(index4);
            sommet3.addVoisin(sommet4);
            sommet4.addVoisin(sommet3);
        }
        graphe = Graphe.generateGraphContent(graphe);
        return graphe;
    }

    public void colorerPointsGrille(double distance)    {
        int nbSommets = sommets.size();
        double rowSize = Math.sqrt(nbSommets);
        for (int i = 0; i < nbSommets; i++) {
            int x = (int)(i/rowSize)+1;
            int y = (int)(i%rowSize)+1;
            if(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) >= distance){
                colorNode(i, 1);
            }
        }
    }

    public int marcheAleatoireGrille(double distance) throws InterruptedException {
        double rowSize = Math.sqrt(sommets.size());
        sommetCourant = sommets.get(0);
        double distanceCourante = 0;
        colorNode(0, 2);
        int nbIt = 0;
        while(distanceCourante < distance){
            Sommet suivant = sommetCourant.getRandomVoisin();
            resetNodeStyle(sommetCourant.getIndex());
            colorNode(suivant.getIndex(), 3);
            int index = suivant.getIndex();
            int x = (int)(index/rowSize)+1;
            int y = (int)(index%rowSize)+1;
            distanceCourante = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            sommetCourant = suivant;
            nbIt++;
            Thread.sleep(100);
        }
        return nbIt;
    }



    public int getSize(){ return sommets.size(); }

    public void colorNode(int index, int color){
        if(color == 1)
            graph.getNode(index+"").setAttribute("ui.class", "start");
        else if(color == 2)
            graph.getNode(index+"").setAttribute("ui.class", "destination");
        else if (color == 3)
            graph.getNode(index+"").setAttribute("ui.class", "colored");
        else
            graph.getNode(index+"").setAttribute("ui.class", "default");
    }

    public void resetNodeStyle(int index){
        graph.getNode(index+"").setAttribute("ui.class", "default");
    }

    private void resetEdgeStyle(String edgeName){
        graph.getEdge(edgeName).setAttribute("ui.class", "default");
    }

    private void colorEdge(String edgeName){
        if(graph.getEdge(edgeName) != null)
            graph.getEdge(edgeName).setAttribute("ui.class", "colored");
    }

    public MultiGraph getGraph(){ return graph;}

    public void print(){
        for (int i = 0; i < sommets.size(); i++) {
            System.out.println(sommets.get(i));
        }
    }

    public int tempsCouverture(){
        sommetCourant = sommets.get(0);
        colorNode(0, 3);
        HashSet<Sommet> parcourus = new HashSet<>();
        parcourus.add(sommetCourant);
        int nbIt = 0;
        while(parcourus.size() != sommets.size()){
            colorNode(sommetCourant.getIndex(), 1);
            sommetCourant = sommetCourant.getRandomVoisin();
            colorNode(sommetCourant.getIndex(), 3);
            parcourus.add(sommetCourant);
            nbIt++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return nbIt;
    }

    public int marcheAleatoire(int indexDest) throws InterruptedException {
        sommetCourant = sommets.get(0);
        if(isDisplayGraph){
            colorNode(0, 1);
            colorNode(indexDest, 2);
        }
        boolean found = false;
        int nbIt = 0;
        while(!found){
            nbIt++;
            Sommet suivant = sommetCourant.getRandomVoisin();
            if(suivant.getIndex() == indexDest){
                found = true;
            }
            String edgeName = sommetCourant.getIndex()+"_"+ suivant.getIndex();
            Thread.sleep(50);
            if(isDisplayGraph)
                colorEdge(edgeName);
            sommetCourant = suivant;
        }
        return nbIt;
    }

    public void afficherGraphe(){
        graph.display();
    }
}
