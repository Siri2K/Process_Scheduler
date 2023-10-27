package Schedule;

/* Import */
import Queue.Queue;

public class FCFS extends Schedule{
    public FCFS(){
        this.setCurrentTime(0);
        this.setQueue(new Queue<>());
    }
}
