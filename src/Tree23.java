import jdk.nashorn.api.tree.Tree;

public class Tree23<K extends Comparable<K>, T> {
    private InNode<K> root;
    private final K minKey;
    private final K maxKey;

    public Tree23(K minKey, K maxKey){
        this.minKey = minKey;
        this.maxKey = maxKey;
        this.init();
    }
    private void init(){
        root = new InNode<K>(maxKey);
        InNode<K> l = new InNode<>(minKey);
        InNode<K> m = new InNode<>(maxKey);
    }

    /* returns the object if founded, else null if not founded.*/
    public T search(K key){
        Node<K> x = root;
        Node<K> res = searchHelper(x, key);
        if(res != null) return ((Leaf<K,T>)res).obj();
        return null;
    }
    private Node<K> searchHelper(Node<K> x, K key){
        if (x instanceof Leaf){
            if(x.getKey() == key) return x;
            else return null;
        }

        InNode<K> inX  = (InNode<K>) x;

        if (key.compareTo(inX.getLeft().getKey()) <= 0)
            return searchHelper(inX.getLeft(), key);

        else if (key.compareTo(inX.getMiddle().getKey()) <= 0)
            return searchHelper(inX.getMiddle(), key);

        else return searchHelper(inX.getRight(), key);

    }

    public T minimum(){
        Node<K> x = this.root;
        while (x instanceof InNode<?>)
            x = ((InNode<K>) x).getLeft();
        x = ((InNode<K>) x.getParent()).getMiddle();
        if(x.getKey() != maxKey)
            return ((Leaf<K,T>) x).obj();
        return null;
    }

}
