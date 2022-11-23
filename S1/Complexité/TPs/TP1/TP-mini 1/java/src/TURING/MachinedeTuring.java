package TURING;

import java.io.*;
import java.util.ArrayList;
public class MachinedeTuring {


    private ArrayList<Etate> etates = new ArrayList<>();
    private ArrayList<Transition> transitions = new ArrayList<>();
    private  ArrayList<Symbole> symboles = new ArrayList<>();
    private ArrayList<Etate> finalEtates = new ArrayList<>();
    private Symbole SymboleBlanc ;
    private Etate etateInitial;


    public MachinedeTuring(String file) throws Exception {

        BufferedReader r = new BufferedReader(new FileReader(new File(file))) ;
        String ligne = "";
        /**
         * Methode qui va permettre de stocker les etates de notre machine de
         * Turing.
         * En Va faire une lecture de Lignes de la machine de turing donnée.
         * **/
        if((ligne = r.readLine()) != null )
        {
            String [] StatesValue = ligne.split(",");
            for (int i = 0; i<StatesValue.length; i++) {
                etates.add(new Etate(StatesValue[i]));
            }
        }
        else
        {
            throw  new Exception("Erreur");
        }

        /***
         * Les ligne des Symboles.
         * Comme la precedent un if qui va verifier la presences des Symbole
         * de transition dans notre machine de Turing**/
        //--------------------------------------------------------------------------------------------------------------

        if((ligne = r.readLine()) != null)
        {
            String[] symbolsValues = ligne.split(",");
            for(int i = 0; i<symbolsValues.length; i++) {  //O(n)
                symboles.add(new Symbole(symbolsValues[i]));
            }
        }
        else
        {
            throw new Exception("Erreur");
        }

        //--------------------------------------------------------------------------------------------------------------

        if ((ligne = r.readLine()) != null)
        {
            Symbole t = new Symbole(ligne);
            if (!symboles.contains(t)) //O(n)
                throw new Exception("Le symbole blanc n'est pas dans la liste");
            SymboleBlanc = symboles.get(symboles.indexOf(t));
        }

        //--------------------------------------------------------------------------------------------------------------

        if((ligne = r.readLine()) != null)
        {
            Etate temp = new Etate(ligne);

            if(!etates.contains(temp)) //O(n)
                throw new Exception("Initial etats "+ligne+" n'est pas dans la liste");
            etateInitial = etates.get(etates.indexOf(temp));
        }
        else
        {
            throw new Exception("Erreur");
        }

        /* Final States */
        //--------------------------------------------------------------------------------------------------------------

        if((ligne = r.readLine()) != null)
        {
            String[] finalStatesValues = ligne.split(",");
            for(int i = 0; i<finalStatesValues.length; i++) {
                Etate temp = new Etate(finalStatesValues[i]);

                if(!etates.contains(temp))
                    throw new Exception("Final etate "+finalStatesValues[i]+" n'est pas dans la liste");
                finalEtates.add(etates.get(etates.indexOf(temp)));
            }
        }
        else
        {
            throw new Exception("Erreur");
        }

        // Transactions
        /**
         * Dans cette boucle de while que en crées les different trasaction trouver dans la machine de turing
         * et les faire stocké dans l'array liste que en a déclaré en haut
         **/
        while((ligne = r.readLine()) != null)
        {
            String[] newTransitions = ligne.split(",");

            transitions.add(new Transition(new Symbole(newTransitions[1]), new Etate(newTransitions[0]),
                    new Symbole(newTransitions[3]), new Etate(newTransitions[2]), newTransitions[4]));
        }

        System.out.println("Machine de turing\n---\nEtates: "+ etates +"\nSymbols: "+symboles+"\nLe symbole blac: "+SymboleBlanc+"\nInitial State: "+ etateInitial
                +"\nFinal Etats: "+ finalEtates +"\nTransitions :"+transitions+"\n---\n");
        }


        //--------------------------------------------------------------------------------------------------------------

    // La methode qui va ne permetre de construire notre Ruban de la machine.
    public boolean ruban(String w, boolean Boul) {
        System.out.println("est ce que il est  " + w + " reconnu?");
        Etate etatActuel = etateInitial;
        //Construction du ruban
        Case premierCase = new Case(new Symbole(w.substring(0, 1)), SymboleBlanc);
        Case caseActuel = premierCase;

        for (int i = 1; i < w.length(); i++) {
            Case newCase = new Case(caseActuel, new Symbole(w.substring(i, i + 1)), SymboleBlanc);
            caseActuel = newCase;
        }
        caseActuel = premierCase;
        //Fin construction ruban


        //Debut iteration
        while (true) {
            if (!symboles.contains(caseActuel.symbole))
                return false;

            Transition TTransition = new Transition(caseActuel.symbole, etatActuel, null, null, "Erreur");
            // Une condition qui va verifier si l'état ectuel appartien aux etats finaux de la machine de turing ou pas.

            if (!transitions.contains(TTransition)) {
                 if (Boul) {
                     System.out.println(caseActuel.ToString());
                 }
                return finalEtates.contains(etatActuel);
            }
            Transition t = transitions.get(transitions.indexOf(TTransition));
            caseActuel.symbole = t.SymSuiv;
            etatActuel = t.EtatSuiv;
            if (Boul) System.out.println(t.EtatSuiv);
            switch (t.direction) {

                case "-1":

                    if (caseActuel.CaseGauche == null) {
                        caseActuel.CaseGauche = new Case(finalEtates, caseActuel);
                    }
                    caseActuel = caseActuel.CaseGauche;
                    break;

                case "+1":

                    if (caseActuel.CaseDroit == null) {
                        caseActuel.CaseDroit = new Case(finalEtates, caseActuel);
                    }
                    caseActuel = caseActuel.CaseDroit;
                    break;
            }
        }
    }

    }
