public class Leaf<T extends Comparable<T>,O extends Nodeable<T>> extends Node<T>{

    private final O obj;

    public Leaf(T key, O obj){
        super(key);
        this.obj = obj;
        setWeight(obj.getWeight());
        setSize(1);
        setValue(obj.getValue());
    }

    public Leaf(T key){
        super(key);
        this.obj = null;
        setSize(0);
        setValue(0);
    }
    public O obj(){return this.obj;}

    @Override
    public float getValue(){
        if(obj != null)
            return obj.getValue();
        return 0;
    }

    @Override
    public InNode<T> getParent() {return super.getParent();}
}

