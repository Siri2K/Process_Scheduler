package Process;

/* Imports */
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Process {
    private String name;
    private int pid;
    private int priority;
    private int cpuBurst;
    private int arrivalTime;
    private int finishTime;
    private String state;
    private List<Process> children;
    private Map<Process, Integer> waitingFor; // Map of child processes and the number of children to wait for



    public Process(String name, int priority, int cpuBurst, int arrivalTime, int pid) {
        this.name = name;
        this.priority = priority;
        this.pid = pid;
        this.cpuBurst = cpuBurst;
        this.arrivalTime = arrivalTime;
        this.finishTime = 0;
        this.state = "Ready";
        this.children = new ArrayList<>();
        this.waitingFor = new HashMap<>();
    }

    // setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public void setCpuBurst(int cpuBurst) {
        this.cpuBurst = cpuBurst;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setPID(int pid) {
        this.pid = pid;
    }

    public void setChildren(List<Process> children) {
        this.children = children;
    }

    
    // Getter methods
    public String getName() {
        return name;
    }

    public String getState() {
        return state;
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

    public int getFinishTime() {
        return finishTime;
    }

    public int getPID() {
        return pid;
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
            this.setState("Ready"); // No more children to wait for
        }
    }
    


    @Override
    public String toString() {
        return "Process [name=" + name + ", priority=" + priority + ", cpuBurst=" + cpuBurst +
                ", arrivalTime=" + arrivalTime + ", children=" + children.size() + "]";
    }

    public boolean compareTo(Process process, String attribute) {
        switch (attribute) {
            case "Arrival Time":
                return (this.getArrivalTime()- process.getArrivalTime()) < 0;
            case "Priority":
                return (this.getPriority()- process.getPriority()) < 0;
            case "CPU Burst Time":
                return (this.getCpuBurst()- process.getCpuBurst()) < 0;
            default:
                return false;
        }
        
    }
}
