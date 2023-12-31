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
    private PIDManager manager = new PIDManager();

    /* Constructor */
    public Scheduler(){
        this.make_process_list();
    }

    /* Setter and Getter */
    public void set_process_list(List<Process> processList){
        this.processList = processList;
    }

    public List<Process> get_process_list(){
        return this.processList;
    }
    
    public void set_schedule(String schedule){
        this.schedule = schedule;
    }

    public String get_schedule(){
        return this.schedule;
    }

    public void set_queue(Queue<Process> queue){
        this.queue = queue;
    }

    public Queue<Process> get_queue(){
        return this.queue;
    }

    /* Roles */
    private void make_process_list(){
        List<Process> processList = new LinkedList<Process>();
        
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
            this.set_process_list(processList);
            
            
        } catch (FileNotFoundException  e) {
            System.out.print("File not found");
            e.printStackTrace();
        }
        
    }

    public void schedule_process(){
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
                    this.set_schedule("Arrival Time");
                    this.apply_FCFS();
                    break;
                case 2:
                    this.set_schedule("Arrival Time");
                    this.apply_RR();
                    break;
                case 3:
                    this.set_schedule("Priority");
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
            List<Process> processList = this.get_process_list();
            FCFS fcfs = new FCFS();
            Process [] new_list = (Process [])processList.toArray(new Process[processList.size()]);
            fcfs.schedule(new_list, this.get_schedule());
            this.queue = fcfs.getQueue();
        } catch (Exception e) {
            System.out.println("Process List is Empty");
            e.printStackTrace();
        }
        
    }

    private void apply_RR(){ /* Apply Round Robin Algorithm */
        try {
            List<Process> processList = this.get_process_list();
            Process [] new_list = (Process [])processList.toArray(new Process[processList.size()]);

            /* Setup Round Robin */
            System.out.print("Enter a Time quantum: ");
            Scanner inputScanner = new Scanner(System.in);
            int timeQuantum = inputScanner.nextInt();
            RoundRobin roundRobin = new RoundRobin(timeQuantum);
            roundRobin.schedule(new_list, this.get_schedule());
            this.queue = roundRobin.getQueue();
            inputScanner.close();
        } catch (NullPointerException e) {
            System.out.println("Process List is Empty");
            e.printStackTrace();
        }
    }

    private void apply_PRR(){ /* Apply Priority Round Robin Algorithm */
        try {
            List<Process> processList = this.get_process_list();
            Process [] new_list = (Process [])processList.toArray(new Process[processList.size()]);

            /* Setup Round Robin */
            System.out.print("Enter a Time quantum: ");
            Scanner inputScanner = new Scanner(System.in);
            int timeQuantum = inputScanner.nextInt();
            PriorityRoundRobin priorityRoundRobin = new PriorityRoundRobin(timeQuantum);
            priorityRoundRobin.schedule(new_list, this.get_schedule());
            inputScanner.close();
            this.queue = priorityRoundRobin.getQueue();
        } catch (NullPointerException e) {
            System.out.println("Process List is Empty");
            e.printStackTrace();
        }
    }

    private double calculate_average_turn_around_time(){
        Queue<Process> temp_queue = this.get_queue();
        Process[] process_list = new Process[this.get_queue().size()];
        int numberOfProcesses = this.get_process_list().size();
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

    private double calculate_average_response_time(){
        Queue<Process> temp_queue = this.get_queue();
        Process[] process_list = new Process[this.get_queue().size()];
        int numberOfProcesses = this.get_process_list().size();
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

    private double calculate_average_wait_time(){
        return this.calculate_average_turn_around_time() - this.calculate_average_response_time();
    }

    public void print(){
        System.out.println("\nAverage of Schedule : ");
        System.out.print(" Average Turn Around Time = " + this.calculate_average_turn_around_time() + "\n");
        System.out.print(" Average Wait Time = " + this.calculate_average_wait_time() + "\n");
        System.out.print(" Average Response Time = " + this.calculate_average_response_time() + "\n");
    }

    

}