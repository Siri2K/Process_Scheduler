package Queue;

public class Node <Key, Item>{
    /* Attribute */
    Key key;
    Item item;
    Node <Key,Item> next;

    /* Contructors */
    public Node(Key key, Item item, Node <Key,Item> next){
        this.setKey(key);
        this.setItem(item);
        this.setNext(next);
    }

    public Node(Key key, Item item){
        this(key,item,null);
    }

    /* Setter & Getters */
    public void setKey(Key key){
        this.key = key;
    }

    public void setItem(Item item){
        this.item = item;
    }

    public void setNext(Node <Key,Item> next){
        this.next = next;
    }

    public Key key(){
        return this.key;
    }

    public Item item(){
        return this.item;
    }

    public Node <Key,Item> next(){
        return this.next;
    }
}