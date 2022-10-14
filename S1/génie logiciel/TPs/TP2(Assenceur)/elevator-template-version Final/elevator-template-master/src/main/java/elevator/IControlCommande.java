package elevator;

//import com.sun.net.httpserver.Request;

public interface IControlCommande {
    
    boolean getGoRequest(int etage);

    boolean getUpRequest(int etage);
    
    boolean getDownRequest(int etage);

    void addGoRequest(int etage);

    void addUpRequest(int etage);
    void addDownRequest(int etage);

    void deleteRequests(int etage);

    int getNextFloor(int pos, Scheduler.Direction direction);
}
