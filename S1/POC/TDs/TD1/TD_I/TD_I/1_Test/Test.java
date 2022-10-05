// -*- coding: utf-8 -*-

class Test implements Runnable { 
    String msg;
    
    public Test(String s) { 
        msg = s;
    }
    
    public void run() { 
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {e.printStackTrace();}; 
        System.out.print(msg + " ");
    }

    public static void main(String [] args) { 
        Thread t1 = new Thread(new Test("Hello"));
        Thread t2 = new Thread(new Test("World"));
        t1.start();
        t2.start();        
    } 

    public static void complete_main(String [] args) throws InterruptedException { 
        Thread t1 = new Thread(new Test("Hello"));
        Thread t2 = new Thread(new Test("World"));
        t1.start();
        t2.start();        
        t1.join();
        t2.join();
        System.out.println();      
    } 
} 
