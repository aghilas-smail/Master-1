package elevator.CC;

public interface ControlCommande {
    
    void monter(int etage);;

    void decend(int etage);
    
    int getPosition();

    boolean stop();


}
