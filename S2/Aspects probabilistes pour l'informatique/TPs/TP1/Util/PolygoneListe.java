package TP1.Util;

import java.util.ArrayList;

public class PolygoneListe {
    private ArrayList<Point2D> sommets;
    private double valeurMax;
    public PolygoneListe(int nbSommets, double valeurMax){
        this.valeurMax = valeurMax;
        sommets = new ArrayList<>();
        for (int i = 0; i < nbSommets; i++) {
            sommets.add(new Point2D(Math.random(), Math.random()));
        }
    }

    public void printSommets(){
        for (int i = 0; i < sommets.size(); i++) {
            System.out.println("Sommet" + (i+1) + ": x = " + sommets.get(i).getX() + "; y = " + sommets.get(i).getY() + ";");
        }
    }

    public double getValeurMax() {
        return valeurMax;
    }

    public PolygoneListe(double[] valeursX, double[] valeursY, double valeurMax){
        this.valeurMax = valeurMax;
        sommets = new ArrayList<>();
        for (int i = 0; i < valeursX.length; i++) {
            sommets.add(new Point2D(valeursX[i], valeursY[i]));
        }
    }

    public Point2D getSommet(int i){
        return sommets.get(i%sommets.size());
    }

    public int size(){
        return sommets.size();
    }

    // Fonction qui renvoie vrai si le point donné en entrée est dans le polygone
    public boolean contient(Point2D p){
        // Idée: on créé un point à l'extrême et pour chacune des arrêtes du polygone, on regarde si le
        // segment entre le point et son extrême croise l'arrête. Si c'est le cas, on incrémente une valeur.
        Point2D extreme = new Point2D(valeurMax, p.getY());
        int count = 0, i = 0;
        do{
            if(intersect(getSommet(i), getSommet(i+1), p, extreme)){
                if(orientation(getSommet(i), p, getSommet(i+1)) == 0){
                    return estSurSegment(getSommet(i), p, getSommet(i+1));
                }
                count++;
            }
            i++;
        }while(i < sommets.size());
        // Si segment traverse le bord du polygone un nombre pair de foi, on est dedans.
        return (count%2 == 1);
    }
    // Cette fonction donne le sens, horaire ou anti-horaire de l'angle. Elle renvoie 0 si les trois points sont alignés
    private int orientation(Point2D p, Point2D q, Point2D r){
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX())
                - (q.getX() - p.getX()) * (r.getY() - q.getY());
        if(val == 0){
            return 0;            // 0 = alignés
        }
        return (val > 0)? 1 : 2; // 1 = anti-horaire; 2 = horaire
    }

    // Cette fonction renvoie Vrai si le point q est sur le segment pr
    private boolean estSurSegment(Point2D p, Point2D q, Point2D r){
        if (q.getX() <= Math.max(p.getX(), r.getX()) &&
                q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) &&
                q.getY() >= Math.min(p.getY(), r.getY()))
        {
            return true;
        }
        return false;
    }
    // p1 = sommet 1, p2 = sommet 2, q1 = point en entrée, q2 = point extrême
    private boolean intersect(Point2D p1, Point2D p2, Point2D q1, Point2D q2){
        // Deux sommets et points
        int o1 = orientation(p1, p2, q1);
        // Deux sommets et extrême
        int o2 = orientation(p1, p2, q2);
        // TP2_Willy.Point, extrême et sommet1
        int o3 = orientation(q1, q2, p1);
        // TP2_Willy.Point, extrême et sommet 2
        int o4 = orientation(q1, q2, p2);

        // Il y à croisement car le point et son extrême sont de pars et d'autre du segment entre les deux sommets
        // et les deux sommets sont de part et d'autre du segment entre le point et son extrême
        if (o1 != o2 && o3 != o4)
        {
            return true;
        }

        // Les deux sommets et le point extrême sont alignés, alors on croise l'arrête ssi:
        // le point extrême est sur l'arrête.
        if (o2 == 0 && estSurSegment(p1, q2, p2))
        {
            return true;
        }

        // Le premier sommet est sur la droite qui relie les deux points, alors on croise l'arrête ssi:
        // le premier sommet est sur le segment entre les deux points.
        if (o3 == 0 && estSurSegment(q1, p1, q2))
        {
            return true;
        }

        // Le deuxième sommet est sur la droite qui relie les deux points, alors on croise l'arrête ssi:
        // le deuxième sommet est sur le segment entre les deux points.
        if (o4 == 0 && estSurSegment(q1, p2, q2))
        {
            return true;
        }

        return false;
    }

}
