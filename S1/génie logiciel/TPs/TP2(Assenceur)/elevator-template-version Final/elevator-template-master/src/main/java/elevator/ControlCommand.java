package elevator;

public class ControlCommand implements IControlCommande{

    private Scheduler scheduler;
    private IElevator elevator;
    private int etages;
    private Scheduler.Direction direction;

    public ControlCommand(Scheduler scheduler, IElevator elevator){
        this.scheduler = scheduler;
        this.elevator = elevator;
        this.etages = 0;
        this.direction = Scheduler.Direction.UP;
    }

    @Override
    public void addGoRequest(int f) {
        scheduler.addGoRequest(f);
    }

    @Override
    public boolean getGoRequest(int f) {
        return scheduler.getGoRequest(f);
    }

    @Override
    public void addUpRequest(int f) {
        scheduler.addUpRequest(f);
    }

    @Override
    public boolean getUpRequest(int f) {
        return scheduler.getUpRequest(f);
    }

    @Override
    public void addDownRequest(int f) {
        scheduler.addDownRequest(f);
    }

    @Override
    public boolean getDownRequest(int f) {
        return scheduler.getGoRequest(f);
    }

    @Override
    public void deleteRequests(int f) {
        scheduler.deleteRequests(f);
    }

    @Override
    public int getNextFloor(int pos, Scheduler.Direction direction) {
        return scheduler.nextFloor(pos, direction);
    }

    public void checkAndProcess(){
        int suivant = this.getNextFloor(etages, direction);
        ElevatorSimulator e = (ElevatorSimulator) elevator;
        while(suivant != -1){
            System.out.println("Etage suivant " + suivant);
            if(suivant > etages){
                direction = Scheduler.Direction.UP;
                e.up();
                while(e.getState() == IElevator.State.UP){
                    e.oneStep();
                    if(e.getLevel() == (double)(suivant - 1)){
                        e.stopNext();
                    }
                    if(e.getAndResetStageSensor()) {
                        etages++;
                        System.out.println("FLOOR " + etages);
                    }
                }
            }
            if(suivant < etages){
                direction = Scheduler.Direction.DOWN;
                e.down();
                while(e.getState() == IElevator.State.DOWN){
                    e.oneStep();
                    suivant = this.getNextFloor(etages, direction);
                    if(e.getLevel() == (double)(suivant + 1)) e.stopNext();
                    if(e.getAndResetStageSensor()) {
                        etages--;
                        System.out.println("FLOOR " + etages);
                    }
                }
            }
            if(suivant == etages) {
                e.stopSimulator();
                System.out.println("La porte est ouvert " + etages);
                deleteRequests(etages);
                while(e.getState() != IElevator.State.STOP) {
                    e.oneStep();
                    System.out.print(".");
                }
                System.out.println();
                System.out.println("La porte est fermé");
            }
            suivant = this.getNextFloor(etages, direction);
        }

    }
}
