package elevator;

public class Scheduler {

    public static final int NO_FLOOR = -1;
    private int nbEtages;
    private boolean[] goRequests;
    private boolean[] upRequests;
    private boolean[] downRequests;

    enum Direction {
        UP, DOWN
    }
    public Scheduler(int nbEtages){
        this.nbEtages = nbEtages;
        this.goRequests = new boolean[nbEtages + 1];
        this.upRequests = new boolean[nbEtages + 1];
        this.downRequests = new boolean[nbEtages + 1];
        for(int i = 0; i<=nbEtages; i++) {
            this.goRequests[i] = false;
            this.upRequests[i] = false;
            this.downRequests[i] = false;
        }
    }
    public boolean getGoRequest(int etage) { return this.goRequests[etage]; }

    public boolean getUpRequest(int etage) { return this.upRequests[etage]; }

    public boolean getDownRequest(int etage) { return this.downRequests[etage]; }

    public void addGoRequest(int etage){
        this.goRequests[etage] = true;
    }

    public void addUpRequest(int etage){
        this.upRequests[etage] = true;
    }

    public void addDownRequest(int etage){
        this.downRequests[etage] = true;
    }

    public void deleteRequests(int etage){
        this.goRequests[etage] = false;
        this.upRequests[etage] = false;
        this.downRequests[etage] = false;
    }

    public int nextFloor(int pos, Direction direction){
        int i = pos;
        boolean changeDirection = false;
        if(direction == Direction.UP){
            while(!goRequests[i] & !upRequests[i] & !changeDirection){
                if(i == nbEtages) changeDirection = true;
                else i++;
               // return i;
            }
            if(changeDirection) {
                while(!goRequests[i] & !downRequests[i]){
                    if(i == 0) return NO_FLOOR;
                    else i--;


                }
            }
            return i;
        }
        else {
            while(!goRequests[i] & !downRequests[i] & !changeDirection){
                if(i == 0) changeDirection = true;
                else i--;
            }
            if(changeDirection) {
                while(!goRequests[i] & !upRequests[i]){
                    if(i == nbEtages) return NO_FLOOR;
                    else i++;
                }
            }
        }
        return i;
    }

}