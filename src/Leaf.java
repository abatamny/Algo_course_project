public class Leaf<T extends Comparable<T>,O> extends Node<T>{
    private final O obj;
    public Leaf(T key, O obj){
        super(key);
        this.obj = obj;
    }
    public O obj(){return this.obj;}
}

