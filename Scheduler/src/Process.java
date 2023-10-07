import java.util.ArrayList;
import java.util.List;

public class Process {
    private String name;
    private int priority;
    private int cpuBurst;
    private int arrivalTime;
    private List<Process> children;

    public Process(String name, int priority, int cpuBurst, int arrivalTime) {
        this.name = name;
        this.priority = priority;
        this.cpuBurst = cpuBurst;
        this.arrivalTime = arrivalTime;
        this.children = new ArrayList<>();
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
    }

    // Check if the process has children
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    @Override
    public String toString() {
        return "Process [name=" + name + ", priority=" + priority + ", cpuBurst=" + cpuBurst +
                ", arrivalTime=" + arrivalTime + ", children=" + children.size() + "]";
    }
}





