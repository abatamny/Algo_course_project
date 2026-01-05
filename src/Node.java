public abstract class Node<T extends Comparable<T>>{
    private final T key;
    private Node<T> parent;
    public Node(T key){
        this.key = key;
    }
    public T getKey(){return this.key;}
    public Node<T> getParent(){return this.parent;}
    public void setParent(Node<T> parent){this.parent = parent;}
}
