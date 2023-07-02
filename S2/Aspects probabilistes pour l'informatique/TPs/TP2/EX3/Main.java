package TP2.EX3;

public class Main {
    public static void main(String[] args) {
        Experience experience = new Experience("000", "010");
//        experience.realiserExperience(1000000);
        System.out.println(experience.probaUneSequence("000", 1000000));
        System.out.println(experience.probaUneSequence("010", 1000000));
    }

}
