package Schedule;

/* Import */
import Queue.Queue;

import java.util.ArrayList;

import Process.Process;

public class RoundRobin extends Schedule{
    /* Attribute */
    private int timeQuantum;
    
    public RoundRobin(int timeQuantum){
        this.setTimeQuantum(timeQuantum);
        this.setQueue(new Queue<>());
    }

    public RoundRobin(){
        this(0);
    }

    private void setTimeQuantum(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    private int getTimeQuantum(){
        return this.timeQuantum;
    }

    public Process[] divideProcess(Process[] processes){
        /* List Ordered Process */
        ArrayList<Process> processList = new ArrayList<>();
        for (int i=0; i<processes.length; i++ ){
            processList.add(i, processes[i]);
        }

        /* Split Based on Time Quantum */
        this.getQueue().clear();
        int count = 0;
        while(!processList.isEmpty()){
            if(processList.get(count).getArrivalTime() > this.getTimeQuantum() && this.getTimeQuantum() != 0){
                this.getQueue().enqueue(processList.get(count));
                this.getQueue().getFront().process().setArrivalTime(this.getTimeQuantum());
                processList.get(count).setArrivalTime(processList.get(count).getArrivalTime() - this.getTimeQuantum());
            }
            else{
                this.getQueue().enqueue(processList.get(count));
                count++;
            }
        }

        /* Convert to Array */
        return this.getQueue().toArray();
    }
}
