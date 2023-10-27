package Schedule;

import Queue.Queue;

public class PriorityRoundRobin extends RoundRobin{
    public PriorityRoundRobin(int timeQuantum){
        this.setCurrentTime(0);
        this.setTimeQuantum(timeQuantum);
        this.setQueue(new Queue<>());
    }

    public PriorityRoundRobin(){
        this(0);
    }
}
