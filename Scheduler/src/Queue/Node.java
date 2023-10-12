package Queue;

/* Import */
import Process.Process;

public class Node <Process>{
    /* Attribute */
    Process process;
    Node <Process> next;

    /* Contructors */
    public Node(Process process, Node <Process> next){
        this.setItem(process);
        this.setNext(next);
    }

    public Node(Process process){
        this(process,null);
    }

    public void setItem(Process process){
        this.process = process;
    }

    public void setNext(Node <Process> next){
        this.next = next;
    }

    public Process process(){
        return this.process;
    }

    public Node <Process> next(){
        return this.next;
    }
}