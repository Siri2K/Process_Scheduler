package Queue;

public class Queue<Key,Item>{
    /* Attribute */
    private Node<Key,Item> front,rear;
    private int count;

    /* Constructors */
    public Queue(Node<Key,Item> front, Node<Key,Item> rear){
        this.setFront(front);
        this.setRear(rear);
    }
    
    public Queue(){
        this(null,null);
    }

    /* Setter and Getter */
    public void setFront(Node<Key,Item> front){
        this.front = front;
    }

    public void setRear(Node<Key,Item> rear){
        this.rear = rear;
    }

    public void setCount(int count){
        this.count = count;
    }

    public Node<Key,Item> getFront(){
        return this.front;
    }

    public Node<Key,Item> getRear(){
        return this.rear;
    }

    public int getCount(){
        return this.count;
    }


    /* Roles */
    public void enqueue(Key key, Item item){
        Node<Key,Item> newNode = new Node<>(key,item);
        if(this.getRear() == null){ // First Node
            this.setFront(newNode);
            this.setRear(newNode);
        }
        else{ // Next Node
             this.getRear().setNext(newNode);
             this.setRear(newNode);
        }
        this.setCount(this.getCount()+1);
    }

    public Node<Key,Item> dequeue(){
        if(this.isEmpty()){
            System.out.print("Queue is Empty");
        }
        else{
            Node<Key,Item> removedNode = this.getFront();
            this.setFront(this.getFront().next());
            if(this.getFront() == null){
                this.setRear(null);
            }
            this.setCount(this.getCount()-1);
            return removedNode;
        }
        return null;
    }

    public Node<Key,Item> peek(){
        if(this.isEmpty()){
            return null;
        }
        return this.getFront();
    }

    public boolean isEmpty(){
        return (this.getFront() == null) && (this.getCount() == 0);
    }

    public int size(){
        return this.getCount();
    }
}
