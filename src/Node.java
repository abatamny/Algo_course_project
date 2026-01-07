public abstract class Node<T extends Comparable<T>>
        implements Comparable<Node<T>>{
    private T key;
    private int weight = 0;
    private Node<T> parent;
    public Node(T key){
        this.key = key;
    }
    public void setKey(T key){this.key = key;}
    public T getKey(){return this.key;}
    public InNode<T> getParent(){return (InNode<T>)this.parent;}
    public void setParent(Node<T> parent){this.parent = parent;}
    public int getWeight(){return weight;}
    public void setWeight(int w){weight = w;}
    @Override
    public int compareTo(Node<T> other){
        int compareRes = this.key.compareTo(other.key);
        if(compareRes < 0) return -1;
        if(compareRes == 0) return 0;
        return 1;
    }

    public int compareTo(T key){
        int compareRes = this.key.compareTo(key);
        if(compareRes < 0) return -1;
        if(compareRes == 0) return 0;
        return 1;
    }
    public boolean equals(Node<T> other){
        int compareRes = this.key.compareTo(other.key);
        return compareRes == 0 ? true : false;
    }

    @Override
    public String toString() {
        return this.key.toString();
    }
}
