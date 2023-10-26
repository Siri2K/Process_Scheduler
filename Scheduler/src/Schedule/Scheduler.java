package Schedule;
import Process.Process;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Scheduler {
    private Schedule schedulingAlgorithm;

    public Scheduler() {
        // Default constructor
    }

    public void setSchedulingAlgorithm(Schedule schedulingAlgorithm) {
        this.schedulingAlgorithm = schedulingAlgorithm;
    }

    public void scheduleProcessesFromFile(String filePath) {
        List<Process> processes = new ArrayList<>();

        try {
            File inputFile = new File(filePath);
            Scanner scanner = new Scanner(inputFile);

            Process currentProcess = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(", ");
                if (tokens.length == 5) {
                    String name = tokens[0];
                    int priority = Integer.parseInt(tokens[1]);
                    int cpuBurst = Integer.parseInt(tokens[2]);
                    int arrivalTime = Integer.parseInt(tokens[3]);
                    int numChildren = Integer.parseInt(tokens[4]);

                    Process process = new Process(name, priority, cpuBurst, arrivalTime);
                    if (numChildren == 0) {
                        // No child processes
                        processes.add(process);
                    } else {
                        // This process has child processes
                        currentProcess = process;
                    }
                } else {
                    // This line represents a child process
                    String name = tokens[0];
                    int priority = Integer.parseInt(tokens[1]);
                    int cpuBurst = Integer.parseInt(tokens[2]);
                    int arrivalTime = Integer.parseInt(tokens[3]);

                    Process childProcess = new Process(name, priority, cpuBurst, arrivalTime);
                    currentProcess.addChild(childProcess);
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set the scheduling algorithm for the processes
        for (Process process : processes) {
            process.setSchedulingAlgorithm(schedulingAlgorithm);
        }

        // Schedule and execute processes based on the selected algorithm
        schedulingAlgorithm.schedule(processes, "Arrival Time");

        // Execute the scheduled processes
        schedulingAlgorithm.execute();
    }

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        int choice;

        // Prompt the user for the scheduling algorithm choice
        System.out.println("Select a scheduling algorithm:");
        System.out.println("1. FCFS (First-Come-First-Serve)");
        System.out.println("2. Round Robin");
        System.out.println("3. Priority Round Robin");

        Scanner inputScanner = new Scanner(System.in);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1:
               // scheduler.setSchedulingAlgorithm(new FCFS());
                break;
            case 2:
                System.out.println("Enter the time quantum for Round Robin:");
                int timeQuantum = inputScanner.nextInt();
              //  scheduler.setSchedulingAlgorithm(new RoundRobin(timeQuantum));
                break;
            case 3:
                System.out.println("Enter the time quantum for Priority Round Robin:");
                int priorityTimeQuantum = inputScanner.nextInt();
            //    scheduler.setSchedulingAlgorithm(new PriorityRoundRobin(priorityTimeQuantum));
                break;
            default:
                System.out.println("Invalid choice. Exiting.");
                return;
        }

        // Specify the file path to read processes from
        String filePath = "input.txt";

        // Schedule and execute processes
        scheduler.scheduleProcessesFromFile(filePath);
    }
}