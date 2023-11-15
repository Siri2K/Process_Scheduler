package Schedule;

/* Import */
import Queue.Queue;
import Sort.MergeSort;

import java.util.ArrayList;

import Process.Process;

public class RoundRobin extends Schedule{
    /* Attribute */
    private int timeQuantum;
    
    public RoundRobin(int timeQuantum){
        this.setCurrentTime(0);
        this.setTimeQuantum(timeQuantum);
        this.setCurrentTime(0);
        this.setQueue(new Queue<>());
    }

    public RoundRobin(){
        this(0);
    }

    public void setTimeQuantum(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    public int getTimeQuantum(){
        return this.timeQuantum;
    } 

    

    public void schedule(Process[] processes, String attribute){
        /* Sort Process Based on Arrival Time */
        MergeSort mergeSort = new MergeSort();
        processes = mergeSort.sort(processes, attribute);
        int [] process_count = new int[processes.length];
        int [] total_process_count = process_count;

        /* Get Process Count */
        for(int i = 0; i<processes.length;i++){
           process_count[i] = processes[i].getCpuBurst()/(this.getTimeQuantum());
        }

        /* List Ordered Queue */
        this.getQueue().clear();
        int i = 0;
        for(Process process : processes){
            /* Place Process in Queue */
            this.print_status(processes);
            if(process.getArrivalTime() <= this.getCurrentTime()){
                Process temp_process = process;
                if(temp_process.getCpuBurst() > this.getTimeQuantum() && process_count[i] > 0){
                    temp_process.setCpuBurst(this.getTimeQuantum());
                }
                else{
                    temp_process.setCpuBurst(temp_process.getCpuBurst() - this.getTimeQuantum()*(total_process_count[i]-1));
                }
                this.getQueue().enqueue(temp_process);
                process.setState("Running");
                this.print_status(processes);
                process_count[i++]--; 
                process.setFinishTime(this.getCurrentTime() + process.getCpuBurst());
                this.setCurrentTime(this.getCurrentTime() + process.getCpuBurst());
                
                int j = i-1;
                if(process_count[j] == 0){
                    process.setState("Done");
                }
                else{
                    process.setState("Wating");
                }
                this.print_status(processes);
            }            
        }
        
        /* Setup Next Iteation of Process List */
        ArrayList<Process> newProcessesArray = new ArrayList<Process>();
        for(int j = 0; j<processes.length;j++){
            if(i >= processes.length){
                i--;
            }
            if(process_count[i] > 0){
                newProcessesArray.add(processes[j]);
            }
        }

        /* Check ArrayList */
        if(!newProcessesArray.isEmpty()){
            Process [] newProcessList = newProcessesArray.toArray(new Process [newProcessesArray.size()]);
            this.schedule(newProcessList,attribute);
        }    
    }

}
