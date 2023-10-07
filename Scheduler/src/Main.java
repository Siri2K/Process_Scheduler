
public class Main {
    public static void main(String[] args) {
        // Create processes
        Process p1 = new Process("T1", 1, 20, 0);
        Process p2 = new Process("T1.1", 2, 5, 4);
        Process p3 = new Process("T1.2", 3, 10, 5);
        Process p4 = new Process("T1.3", 3, 15, 6);
        Process p5 = new Process("T2", 5, 10, 0);

        // Add children to the parent process
        p1.addChild(p2);
        p1.addChild(p3);
        p1.addChild(p4);

        // Check if the parent has children
        System.out.println("Does p1 have children? " + p1.hasChildren());

        // Print process information
        System.out.println("Process p1: " + p1);
        System.out.println("Process p2: " + p2);
        System.out.println("Process p3: " + p3);
        System.out.println("Process p4: " + p4);
        System.out.println("Process p5: " + p5);
    }
}