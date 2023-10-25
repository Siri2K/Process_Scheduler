package Schedule;

/* Import */
import Process.Process;
import Queue.Queue;
import Sort.MergeSort;

abstract class Schedule {

    /* Attribute */
    private Queue<Process> queue;

    /* Setter and Getters */
    public void setQueue(Queue<Process> queue){
        this.queue = queue;
    }



    public Queue<Process> getQueue(){
        return this.queue;
    }

    /* Function */
    void schedule(Process[] processes, String attribute){

        /* Sort Process Based on Arrival Time */
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(processes, attribute);

        /* List Ordered Queue */
        this.getQueue().clear();
        for (Process process : processes){
            this.getQueue().enqueue(process);
        }
    }

    void execute(){
        /* Leave Empty until Sheduler class is given */
    }
}
