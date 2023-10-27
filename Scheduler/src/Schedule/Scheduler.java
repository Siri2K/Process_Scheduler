package Schedule;
import Process.Process;
import Queue.Queue;
import PIDManager.PIDManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.util.LinkedList;


public class Scheduler{
    /* Attributes */
    private List<Process> processList; // List of Process to put in Queue
    private String schedule; // Schedule based on wihch parameter
    private Queue<Process> queue; // Process Queues
    private PIDManager manager;

    /* Constructor */
    public Scheduler(){
        this.makeProcessList();
    }

    /* Setter and Getter */
    public void setProcessList(List<Process> processList){
        this.processList = processList;
    }

    public List<Process> getProcessList(){
        return this.processList;
    }
    
    public void setSchedule(String schedule){
        this.schedule = schedule;
    }

    public String getSchedule(){
        return this.schedule;
    }

    public void setQueue(Queue<Process> queue){
        this.queue = queue;
    }

    public Queue<Process> getQueue(){
        return this.queue;
    }

    public void setManager(PIDManager manager){
        this.manager = manager;
    }

    public PIDManager getManager(){
        return this.manager;
    }

    /* Roles */
    private void makeProcessList(){
        List<Process> processList = new LinkedList<Process>();
        this.setManager(new PIDManager());
        
        /* Read Input File */
        try {
            String current_directory = System.getProperty("user.dir");
            String file_location = "Scheduler/resources/File.txt";
            File file = new File(current_directory,file_location);
            
            /* Find File */
            while(!file.exists()){
                String[] temp_file_split = file_location.split("/");
                String[] file_split = Arrays.copyOfRange(temp_file_split, 1,temp_file_split.length);
                
                StringBuffer buffer_file = new StringBuffer("/") ;
                for(String fl : file_split){
                    buffer_file.append(fl);
                }
                file_location = buffer_file.toString();
                file = new File(current_directory,file_location);
            }
            
            /* read File */
            Scanner read_file = new Scanner(file);
            while(read_file.hasNextLine()){
                String data = read_file.nextLine();
                String [] data_array = data.split(", ");
                processList.add(
                    new Process(data_array[0], 
                    Integer.parseInt(data_array[1]), 
                    Integer.parseInt(data_array[2]), 
                    Integer.parseInt(data_array[3]),
                    manager.allocatePid()
                ));
            }
            read_file.close();
            this.setProcessList(processList);
            
            
        } catch (FileNotFoundException  e) {
            System.out.print("File not found");
            e.printStackTrace();
        }
        
    }

    public void scheduleProcess(){
        // Prompt the user for the scheduling algorithm choice
        System.out.println("1. FCFS (First-Come-First-Serve)");
        System.out.println("2. Round Robin");
        System.out.println("3. Priority Round Robin");
        System.out.print("Select a scheduling algorithm: ");
        
        try {
            Scanner inputScanner = new Scanner(System.in);
            int choice = inputScanner.nextInt();

            switch (choice) {
                case 1:
                    this.setSchedule("Arrival Time");
                    this.apply_FCFS();
                    break;
                case 2:
                    this.setSchedule("Arrival Time");
                    this.apply_RR();
                    break;
                case 3:
                    this.setSchedule("Priority");
                    this.apply_PRR();
                    break;
                default:
                    break;
            }
            inputScanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }

    private void apply_FCFS(){ /* Apply First Come First Serve Algorithm */
        try {
            List<Process> processList = this.getProcessList();
            FCFS fcfs = new FCFS();
            Process [] new_list = (Process [])processList.toArray(new Process[processList.size()]);
            fcfs.schedule(new_list, this.getSchedule());
            this.queue = fcfs.getQueue();
        } catch (Exception e) {
            System.out.println("Process List is Empty");
            e.printStackTrace();
        }
        
    }

    private void apply_RR(){ /* Apply Round Robin Algorithm */
        try {
            List<Process> processList = this.getProcessList();
            Process [] new_list = (Process [])processList.toArray(new Process[processList.size()]);

            /* Setup Round Robin */
            System.out.print("Enter a Time quantum: ");
            Scanner inputScanner = new Scanner(System.in);
            int timeQuantum = inputScanner.nextInt();
            RoundRobin roundRobin = new RoundRobin(timeQuantum);
            roundRobin.schedule(new_list, this.getSchedule());
            this.queue = roundRobin.getQueue();
            inputScanner.close();
        } catch (NullPointerException e) {
            System.out.println("Process List is Empty");
            e.printStackTrace();
        }
    }

    private void apply_PRR(){ /* Apply Priority Round Robin Algorithm */
        try {
            List<Process> processList = this.getProcessList();
            Process [] new_list = (Process [])processList.toArray(new Process[processList.size()]);

            /* Setup Round Robin */
            System.out.print("Enter a Time quantum: ");
            Scanner inputScanner = new Scanner(System.in);
            int timeQuantum = inputScanner.nextInt();
            PriorityRoundRobin priorityRoundRobin = new PriorityRoundRobin(timeQuantum);
            priorityRoundRobin.schedule(new_list, this.getSchedule());
            inputScanner.close();
            this.queue = priorityRoundRobin.getQueue();
        } catch (NullPointerException e) {
            System.out.println("Process List is Empty");
            e.printStackTrace();
        }
    }

    private double calculateAverageTurnAroundTime(){
        Queue<Process> temp_queue = this.getQueue();
        Process[] process_list = new Process[this.getQueue().size()];
        int numberOfProcesses = this.getProcessList().size();
        int sum = 0;

        /* Get Sum for Queue */
        int i = 0;
        while(!temp_queue.isEmpty()){
            Process process = temp_queue.dequeue().process();
            sum += process.getFinishTime() - process.getArrivalTime();
            process_list[i++] = process;
        }

        /* Restore Queue */
        for (Process process:process_list){
            temp_queue.enqueue(process);
        }

        return sum/numberOfProcesses;
    }

    private double calculateAverageWaitTime(){
        Queue<Process> temp_queue = this.getQueue();
        Process[] process_list = new Process[this.getQueue().size()];
        int numberOfProcesses = this.getProcessList().size();
        int sum = 0;

        /* Get Sum for Queue */
        int i = 0;
        while(!temp_queue.isEmpty()){
            Process process = temp_queue.dequeue().process();
            sum += process.getCpuBurst();
            process_list[i++] = process;
        }

        /* Restore Queue */
        for (Process process:process_list){
            temp_queue.enqueue(process);
        }

        return this.calculateAverageTurnAroundTime() - sum/numberOfProcesses;
    }

    private double calculateAverageResponseTime(){
        Queue<Process> temp_queue = this.getQueue();
        Process[] process_list = new Process[this.getQueue().size()];
        int numberOfProcesses = this.getProcessList().size();
        int sum = 0;

        /* Get Sum for Queue */
        int i = 0;
        while(!temp_queue.isEmpty()){
            Process process = temp_queue.dequeue().process();
            sum += process.getCpuBurst();
            process_list[i++] = process;
        }

        /* Restore Queue */
        for (Process process:process_list){
            temp_queue.enqueue(process);
        }
        
        return sum/numberOfProcesses;
    }

    public void print(){
        System.out.println("\nAverage of Schedule : ");
        System.out.print(" Average Turn Around Time = " + this.calculateAverageTurnAroundTime() + "\n");
        System.out.print(" Average Wait Time = " + this.calculateAverageWaitTime() + "\n");
        System.out.print(" Average Response Time = " + this.calculateAverageResponseTime() + "\n");
    }

    

}