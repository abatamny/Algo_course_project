public class Leaf<O extends Nodeable<String>> extends Node{

    private final O obj;

    public Leaf(String key, O obj){
        super(key);
        this.obj = obj;
        increaseSize();
    }
    public Leaf(String key){
        super(key);
        this.obj = null;
    }
    public void updateWeight(){
        this.setWeight(this.getSize() * this.obj().getValue());
    }
    public O obj(){return this.obj;}


}

