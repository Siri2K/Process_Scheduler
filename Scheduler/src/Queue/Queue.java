package Queue;

@SuppressWarnings("unchecked")
public class Queue<Process>{
    /* Attribute */
    private Node<Process> front,rear;

    /* Constructors */
    public Queue(Node<Process> front, Node<Process> rear){
        this.setFront(front);
        this.setRear(rear);
    }
    
    public Queue(){
        this(null,null);
    }

    /* Setter and Getter */
    public void setFront(Node<Process> front){
        this.front = front;
    }

    public void setRear(Node<Process> rear){
        this.rear = rear;
    }

    public Node<Process> getFront(){
        return this.front;
    }

    public Node<Process> getRear(){
        return this.rear;
    }

    /* Roles */
    public void enqueue(Process item){
        Node<Process> newNode = new Node<>(item);
        if(this.getRear() == null){ // First Node
            this.setFront(newNode);
            this.setRear(newNode);
        }
        else{ // Next Node
             this.getRear().setNext(newNode);
             this.setRear(newNode);
        }
    }

    public Node<Process> dequeue(){
        if(this.isEmpty()){
            System.out.print("Queue is Empty");
        }
        else{
            Node<Process> removedNode = this.getFront();
            this.setFront(this.getFront().next());
            if(this.getFront() == null){
                this.setRear(null);
            }
            return removedNode;
        }
        return null;
    }

    public boolean isEmpty(){
        return this.getFront() == null;
    }

    public int size(){
        int count = 0;
        Node<Process> current = this.getFront();

        while(current !=null){
            count++;
            current=current.next();
        }
        return count;
    }

    public void clear(){
        this.setFront(null);
        this.setRear(null);
    }

    public Process[] toArray(){
        Process[] processes = (Process[])new Object[this.size()];
        Node<Process> current = this.getFront();

        /* Convert All Nodes to Array */
        int i = 0;
        while(current != null){
            processes[i] = current.process();
            current = current.next();
        }
        return processes;
    }
}
