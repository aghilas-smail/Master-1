package TURING;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {


    public static void main(String[] args) throws Exception {

        MachinedeTuring tm1 = new MachinedeTuring("./java/src/Turing/Machines/machine1");
        System.out.println(tm1.ruban("sss", true));


       MachinedeTuring tm2 = new MachinedeTuring("./java/src/Turing/Machines/machine2");
        System.out.println(tm2.ruban("a", false));
        System.out.println(tm2.ruban("aaa", false));
        System.out.println(tm2.ruban("wd", false));

/*

        MachinedeTuring machine3 = new MachinedeTuring("./java/src/Turing/Machines/machine3");
        System.out.println(machine3.ruban("aaa", true));
        System.out.println(machine3.ruban("abc", true));
        System.out.println(machine3.ruban("bbbc", true));
        System.out.println(machine3.ruban("aba", true));
        System.out.println(machine3.ruban("ccccccba", true));*/
       /* File file = new File("./java/src/Turing/machine1");

        FileReader fis = new FileReader(file);

        int charCode;
        while((charCode = fis.read()) != -1) {
            System.out.println((char)charCode + "  " + charCode);
        }
        fis.close();

*/
    }



}
