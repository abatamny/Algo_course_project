public class InNode<T extends Comparable<T>> extends Node<T>{
    private Node<T> left = null;
    private Node<T> right = null;
    private Node<T> middle = null;

    public InNode(T key){
        super(key);
    }
    public Node<T> getRight(){return this.right;}
    public Node<T> getLeft(){return this.left;}
    public Node<T> getMiddle(){return this.middle;}

    public void setRight(Node<T> r) {this.right = r;}
    public void setMiddle(Node<T> m) {this.middle = m;}
    public void setLeft(InNode<T> l) {this.left = l;}

    public void updateKey(){
        T key = this.left.getKey();
        if(this.middle != null)
            key = this.middle.getKey();
        if(this.right != null)
            key = this.middle.getKey();

        this.setKey(key);
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
            if(z.getKey().compareTo(l.getKey()) < 0)
                setChildren(z,l,m);
            else if(z.getKey().compareTo(m.getKey()) < 0)
                setChildren(l,z,m);
            else
                setChildren(l,m,z);
            return null;
        }

        InNode<T> y = new InNode<>(null);
        if(z.getKey().compareTo(l.getKey()) < 0){
            this.setChildren(z,l,null);
            y.setChildren(m,r,null);
        }
        else if(z.getKey().compareTo(m.getKey()) < 0){
            setChildren(l,z,null);
            y.setChildren(m,r,null);
        }
        else if(z.getKey().compareTo(r.getKey()) < 0){
            setChildren(l,m,null);
            y.setChildren(z,r,null);
        }
        else{
            setChildren(l,m,null);
            y.setChildren(r,z,null);
        }
        return y;
    }
}
