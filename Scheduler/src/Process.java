import java.util.*;

public class Process {
    private String name;
    private int priority;
    private int cpuBurst;
    private int arrivalTime;
    private List<Process> children;
    private Map<Process, Integer> waitingFor; // Map of child processes and the number of children to wait for
    private boolean waiting;

    public Process(String name, int priority, int cpuBurst, int arrivalTime) {
        this.name = name;
        this.priority = priority;
        this.cpuBurst = cpuBurst;
        this.arrivalTime = arrivalTime;
        this.children = new ArrayList<>();
        this.waitingFor = new HashMap<>();
        this.waiting = false;
    }

    // Getter methods

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public int getCpuBurst() {
        return cpuBurst;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public List<Process> getChildren() {
        return children;
    }

    // Add a child process
    public void addChild(Process child) {
        children.add(child);
        waitingFor.put(child, 1); // Initialize to wait for 1 child
    }

    // Check if the process has children
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    // Mark the process as waiting
    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    // Check if the process is waiting for its children
    public boolean isWaiting() {
        return waiting;
    }

    // Decrement the number of children to wait for
    public void childCompleted(Process child) {
        Integer remaining = waitingFor.get(child);
        if (remaining != null) {
            if (remaining > 1) {
                waitingFor.put(child, remaining - 1);
            } else {
                waitingFor.remove(child);
            }
        }
        if (waitingFor.isEmpty()) {
            waiting = false; // No more children to wait for
        }
    }

    @Override
    public String toString() {
        return "Process [name=" + name + ", priority=" + priority + ", cpuBurst=" + cpuBurst +
                ", arrivalTime=" + arrivalTime + ", children=" + children.size() + "]";
    }
}





