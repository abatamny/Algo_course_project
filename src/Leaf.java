public class Leaf<T extends Comparable<T>,O extends Nodeable<T>> extends Node<T>{
    private final O obj;
    public Leaf(T key, O obj){
        super(key);
        this.obj = obj;
        setWeight(obj.getWeight());
    }
    public Leaf(T key){
        super(key);
        this.obj = null;
        setWeight(0);

    }
    public O obj(){return this.obj;}

    @Override
    public InNode<T> getParent() {return (InNode<T>) super.getParent();}
}

