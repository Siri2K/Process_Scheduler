/* Import */
import Schedule.Scheduler;


public class Main {
    public static void main(String[] args) {
        System.out.print("\n");
        
        /* Run Scheduler */
        Scheduler scheduler = new Scheduler();
        scheduler.schedule_process();
        scheduler.print();
    }
}