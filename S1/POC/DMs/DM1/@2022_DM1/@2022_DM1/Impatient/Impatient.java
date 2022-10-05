// -*- coding: utf-8 -*-

import java.util.Random;

public class Impatient {
    public static void main(String[] args) throws Exception {
        A a = new A();           // Création d'un objet "a" de la classe A
        a.start();               // Lancement du thread "a"
        Random aléa = new Random();
        Thread.sleep(1000*aléa.nextInt(10));
        a.fin = true;            // Fin du thread "a"
        synchronized(a) {
            a.notify();
        }                
    }
    
    static class A extends Thread {
        public volatile boolean fin = false;

        public void run() {
            synchronized(this) {
                try {
                    while(! fin) wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }                
            System.out.println("Pas trop tôt !");  
        }
    }  
}

/*
  $ make
  javac *.java
  $ java Impatient
  Pas trop tôt !
  $
*/
