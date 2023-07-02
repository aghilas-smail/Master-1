package TP2.EX3;

public class Experience {
    String seq1, seq2;
    String seqCourante;
    public Experience(String seq1, String seq2){
        this.seq1 = seq1;
        this.seq2 = seq2;
    }

    public void realiserExperience(int k){
        double nbVict1 = 0, nbVict2 = 0;
        boolean vict1 = false, vict2 = false;
        for (int i = 0; i < k; i++) {
            seqCourante = Math.random() < 0.5 ? "1" : "0";
            while(!vict1 & !vict2){
                if(seqCourante.length() > 2)
                    seqCourante = seqCourante.substring(seqCourante.length() - 2);
                seqCourante += Math.random() < 0.5 ? "1" : "0";
                if(seqCourante.equals(seq1))
                    vict1 = true;
                if(seqCourante.equals(seq2))
                    vict2 = true;
            }
            if(vict1) nbVict1++;
            else nbVict2++;
            vict1 = false;
            vict2 = false;
        }
        System.out.println("Pourcentage seq1: " + nbVict1/k);
        System.out.println("Pourcentage seq2: " + nbVict2/k);
    }

    public double probaUneSequence(String sequence, int nbIt){
        double nbOcc = 0;
        for (int i = 0; i < nbIt; i++) {
            String randomSeq = Math.random() < 0.5 ? "1" : "0";
            randomSeq += Math.random() < 0.5 ? "1" : "0";
            randomSeq += Math.random() < 0.5 ? "1" : "0";
            if(sequence.equals(randomSeq))
                nbOcc++;
        }
        return nbOcc / nbIt;
    }
}
