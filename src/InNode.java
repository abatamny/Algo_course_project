public class InNode<T extends Comparable<T>> extends Node<T>{
    private Node<T> left = null;
    private Node<T> middle = null;
    private Node<T> right = null;

    public InNode(T key){
        super(key);
    }
    public Node<T> getRight(){return this.right;}
    public Node<T> getLeft(){return this.left;}
    public Node<T> getMiddle(){return this.middle;}


    public void updateKey(){
        T key = null;
        int w = 0;
        int size = 0;
        int value = 0;
        if(this.left != null) {
            key = this.left.getKey();
            w += this.left.getWeight();
            size+= this.left.getSize();
            value += this.left.getValue();
        }
        if(this.middle != null) {
            key = this.middle.getKey();
            w += this.middle.getWeight();
            size += this.middle.getSize();
            value += this.middle.getValue();
        }
        if(this.right != null) {
            key = this.right.getKey();
            w += this.right.getWeight();
            size += this.right.getSize();
            value += this.right.getValue();
        }

        this.setKey(key);
        this.setWeight(w);
        this.setSize(size);
        this.setValue(value);
    }
    public void setChildren(Node<T>l, Node<T>m, Node<T>r){
        this.left = l;
        this.middle = m;
        this.right = r;
        l.setParent(this);
        if(m != null) m.setParent(this);
        if(r != null) r.setParent(this);
        updateKey();
    }
    public Node<T> insertAndSplit(Node<T> z){
        Node<T> l, m, r;
        l = this.left;
        m = this.middle;
        r = this.right;

        if(r == null){
            if(z.compareTo(l) < 0)
                setChildren(z,l,m);
            else if(z.compareTo(m) < 0)
                setChildren(l,z,m);
            else
                setChildren(l,m,z);
            return null;
        }

        InNode<T> y = new InNode<>(null);
        if(z.compareTo(l) < 0){
            this.setChildren(z,l,null);
            y.setChildren(m,r,null);
        }
        else if(z.compareTo(m) < 0){
            this.setChildren(l,z,null);
            y.setChildren(m,r,null);
        }
        else if(z.compareTo(r) < 0){
            this.setChildren(l,m,null);
            y.setChildren(z,r,null);
        }
        else{
            this.setChildren(l,m,null);
            y.setChildren(r,z,null);
        }
        return y;
    }
}
