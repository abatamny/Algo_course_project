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
}
