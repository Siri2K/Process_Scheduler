import java.util.Queue;
import java.util.LinkedList;

public class FCFSScheduler {
    private Queue<Process> readyQueue;
    private PIDManager pidManager;

    public FCFSScheduler(PIDManager pidManager) {
        this.readyQueue = new LinkedList<>();
        this.pidManager = pidManager;
    }

    public void addToReadyQueue(Process process) {
        readyQueue.add(process);
    }

    public void execute() {
        while (!readyQueue.isEmpty()) {
            Process currentProcess = readyQueue.poll();
            System.out.println("Running Process: " + currentProcess.getName());

            // Simulate the process execution
            executeProcess(currentProcess);

            System.out.println("Process " + currentProcess.getName() + " completed.");

            // Handle child processes
            for (Process child : currentProcess.getChildren()) {
                childCompleted(child); // Notify parent that a child has completed
                addToReadyQueue(child);
            }
        }
    }

    private void executeProcess(Process process) {
        try {
            Thread.sleep(process.getCpuBurst());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void childCompleted(Process child) {
        Process parent = findParent(child);
        if (parent != null) {
            parent.childCompleted(child);
        }
    }

    private Process findParent(Process child) {
        for (Process process : readyQueue) {
            if (process.isWaiting() && process.getChildren().contains(child)) {
                return process;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        PIDManager pidManager = new PIDManager(300, 5000);
        FCFSScheduler scheduler = new FCFSScheduler(pidManager);

        // Define processes
        Process p1 = new Process("T1", 1, 20, 0);
        Process p2 = new Process("T1.1", 2, 5, 4);
        Process p3 = new Process("T1.2", 3, 10, 5);
        Process p4 = new Process("T1.3", 3, 15, 6);
        Process p5 = new Process("T2", 5, 10, 0);

        // Add children to the parent process
        p1.addChild(p2);
        p1.addChild(p3);
        p1.addChild(p4);

        // Add processes to the ready queue
        scheduler.addToReadyQueue(p1);
        scheduler.addToReadyQueue(p5);

        // Execute processes using FCFS
        scheduler.execute();
    }
}