package Schedule;

/* Import */
import Process.Process;
import Queue.Queue;
import Sort.MergeSort;

abstract class Schedule {

    /* Attribute */
    private Queue<Process> queue;
    private int currentTime; // Elapsed Time

    /* Setter and Getters */
    public void setQueue(Queue<Process> queue){
        this.queue = queue;
    }

    public Queue<Process> getQueue(){
        return this.queue;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public int getCurrentTime(){
        return this.currentTime;
    } 

    /* Function */
    public void schedule(Process[] processes, String attribute){

        /* Sort Process Based on Arrival Time */
        MergeSort mergeSort = new MergeSort();
        processes = mergeSort.sort(processes, attribute);

        /* List Ordered Queue */
        this.getQueue().clear();

        /* Add State to Queue */
        for (Process process : processes){
            this.printStatus(processes);
            this.getQueue().enqueue(process);
            process.setState("Running");
            this.printStatus(processes);
            process.setFinishTime(this.getCurrentTime() + process.getCpuBurst());
            this.setCurrentTime(this.getCurrentTime() + process.getCpuBurst());
            process.setState("Done");
            this.printStatus(processes);
        }
    }

    public void printStatus(Process[] processes){
        System.out.println("\nStatus of Processes");
        
        /* Check for Running process */
        boolean running_process = false;
        for(Process process:processes){
            if(process.getState() == "Running"){
                running_process = true;
                break;
            }
        }

        for(Process process:processes){
            if(running_process && process.getState() == "Ready"){
                process.setState("Waiting");
            }
            else if(process.getState() != "Done" && process.getState() != "Running"){
                process.setState("Ready");
            }
            System.out.println(process.getName() + " : " + process.getState());
        }
    }
}
