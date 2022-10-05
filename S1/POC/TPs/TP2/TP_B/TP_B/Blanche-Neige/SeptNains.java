// -*- coding: utf-8 -*-

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SeptNains {
    final static BlancheNeige bn = new BlancheNeige();
    final static int nbNains = 7;
    final static String noms [] = {"Simplet", "Dormeur",  "Atchoum", "Joyeux", "Grincheux",
                                   "Prof", "Timide"};
  
    public static void main(String[] args) throws InterruptedException {
        affiche("Début du programme");
        final Nain nain [] = new Nain [nbNains];
        for(int i = 0; i < nbNains; i++) nain[i] = new Nain(noms[i]);
        for(int i = 0; i < nbNains; i++) nain[i].start();
    }

    static void affiche(String message) {
        SimpleDateFormat sdf=new SimpleDateFormat("'['hh'h 'mm'mn 'ss','SSS's] '");  
        Date heure = new Date(System.currentTimeMillis());
        System.out.println(sdf.format(heure) + "[" + Thread.currentThread().getName() + "] "
                           + message + ".");        
    }
    
    static class Nain extends Thread {
        public Nain(String nom) {
            this.setName(nom);
        }
        public void run() {
            while(true) {
                bn.requérir();
                try {
                    bn.accéder();
                } catch (InterruptedException e) {e.printStackTrace();}
                affiche("a un accès (exclusif) à Blanche-Neige");
                try {
                    sleep(2000);
                } catch (InterruptedException e) {e.printStackTrace();}
                affiche("s'apprête à quitter Blanche-Neige");
                bn.relâcher();
            }
            // affiche("dit \"Au revoir !\"");
        }	
    }
    
    static class BlancheNeige {
        private volatile boolean libre = true;     // Initialement, Blanche-Neige est libre.
        public synchronized void requérir() {
            affiche("veut un accès exclusif à la ressource");
        }
        public synchronized void accéder() throws InterruptedException {
            if ( ! libre ) wait() ;            // Le nain attend passivement
            libre = false;
            affiche("accède à la ressource");
        }
        public synchronized void relâcher() {
            affiche("relâche la ressource");
            notifyAll();
            libre = true;
        }
    }
}    



/*
  $ java SeptNains
  [09h 02mn 15,517s] [main] Début du programme.
  [09h 02mn 15,562s] [Simplet] veut un accès exclusif à la ressource.
  [09h 02mn 15,563s] [Simplet] accède à la ressource.
  [09h 02mn 15,563s] [Simplet] a un accès (exclusif) à Blanche-Neige.
  [09h 02mn 15,563s] [Joyeux] veut un accès exclusif à la ressource.
  [09h 02mn 15,563s] [Atchoum] veut un accès exclusif à la ressource.
  [09h 02mn 15,563s] [Dormeur] veut un accès exclusif à la ressource.
  [09h 02mn 15,563s] [Timide] veut un accès exclusif à la ressource.
  [09h 02mn 15,563s] [Prof] veut un accès exclusif à la ressource.
  [09h 02mn 15,564s] [Grincheux] veut un accès exclusif à la ressource.
  [09h 02mn 17,568s] [Simplet] s'apprête à quitter Blanche-Neige.
  [09h 02mn 17,568s] [Joyeux] accède à la ressource.
  [09h 02mn 17,569s] [Joyeux] a un accès (exclusif) à Blanche-Neige.
  [09h 02mn 17,569s] [Simplet] relâche la ressource.
  [09h 02mn 17,569s] [Simplet] veut un accès exclusif à la ressource.
  [09h 02mn 17,570s] [Simplet] accède à la ressource.
  [09h 02mn 17,570s] [Simplet] a un accès (exclusif) à Blanche-Neige.
  [09h 02mn 17,570s] [Atchoum] accède à la ressource.
  [09h 02mn 17,570s] [Atchoum] a un accès (exclusif) à Blanche-Neige.
  [09h 02mn 17,570s] [Grincheux] accède à la ressource.
  [09h 02mn 17,571s] [Grincheux] a un accès (exclusif) à Blanche-Neige.
  [09h 02mn 17,571s] [Prof] accède à la ressource.
  [09h 02mn 17,571s] [Prof] a un accès (exclusif) à Blanche-Neige.
  [09h 02mn 17,571s] [Timide] accède à la ressource.
  [09h 02mn 17,572s] [Timide] a un accès (exclusif) à Blanche-Neige.
  [09h 02mn 17,572s] [Dormeur] accède à la ressource.
  ...
*/


/* En remplaçant if par while
  $ java SeptNains
  [09h 05mn 12,134s] [main] Début du programme.
  [09h 05mn 12,174s] [Simplet] veut un accès exclusif à la ressource.
  [09h 05mn 12,174s] [Simplet] accède à la ressource.
  [09h 05mn 12,174s] [Simplet] a un accès (exclusif) à Blanche-Neige.
  [09h 05mn 12,174s] [Timide] veut un accès exclusif à la ressource.
  [09h 05mn 12,174s] [Prof] veut un accès exclusif à la ressource.
  [09h 05mn 12,174s] [Atchoum] veut un accès exclusif à la ressource.
  [09h 05mn 12,175s] [Grincheux] veut un accès exclusif à la ressource.
  [09h 05mn 12,175s] [Joyeux] veut un accès exclusif à la ressource.
  [09h 05mn 12,175s] [Dormeur] veut un accès exclusif à la ressource.
  [09h 05mn 14,175s] [Simplet] s'apprête à quitter Blanche-Neige.
  [09h 05mn 14,176s] [Simplet] relâche la ressource.
  [09h 05mn 14,176s] [Simplet] veut un accès exclusif à la ressource.
  [09h 05mn 14,176s] [Simplet] accède à la ressource.
  [09h 05mn 14,177s] [Simplet] a un accès (exclusif) à Blanche-Neige.
  [09h 05mn 16,182s] [Simplet] s'apprête à quitter Blanche-Neige.
  [09h 05mn 16,183s] [Simplet] relâche la ressource.
  [09h 05mn 16,183s] [Simplet] veut un accès exclusif à la ressource.
  [09h 05mn 16,183s] [Simplet] accède à la ressource.
  [09h 05mn 16,184s] [Simplet] a un accès (exclusif) à Blanche-Neige.
  [09h 05mn 18,184s] [Simplet] s'apprête à quitter Blanche-Neige.
  [09h 05mn 18,184s] [Simplet] relâche la ressource.
  [09h 05mn 18,185s] [Simplet] veut un accès exclusif à la ressource.
  [09h 05mn 18,185s] [Simplet] accède à la ressource.
  [09h 05mn 18,185s] [Simplet] a un accès (exclusif) à Blanche-Neige.
  [09h 05mn 20,189s] [Simplet] s'apprête à quitter Blanche-Neige.
  [09h 05mn 20,189s] [Simplet] relâche la ressource.
  [09h 05mn 20,189s] [Simplet] veut un accès exclusif à la ressource.
  [09h 05mn 20,190s] [Simplet] accède à la ressource.
  [09h 05mn 20,190s] [Simplet] a un accès (exclusif) à Blanche-Neige.
  ...
*/
