// -*- coding: utf-8 -*-

class SyncTest extends Thread { 
    String msg;
    private static Object unObjet = new Object() ;

    public SyncTest(String s) { 
        msg = s;
    } 

    public void run() { 
        synchronized (unObjet) { 
            System.out.print("[" + msg); 
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignorée) {}; 
            System.out.println("]");
        }
    }

    public static void main(String [] args) { 
        new SyncTest("Hello").start();
        new SyncTest("World").start();
    } 
} 
