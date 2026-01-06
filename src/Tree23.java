
public class Tree23<K extends Comparable<K>, T extends Nodeable<K>> {
    private InNode<K> root;
    private final K minKey;
    private final K maxKey;

    public Tree23(K minKey, K maxKey){
        this.minKey = minKey;
        this.maxKey = maxKey;
        this.init();
    }
    private void init(){
        root = new InNode<K>(null);
        Leaf<K,T> l = new Leaf<>(this.minKey);
        Leaf<K,T> m = new Leaf<>(this.maxKey);
        root.setChildren(l,m,null);
    }

    /* returns the object if founded, else null if not founded.*/
    public Leaf<K, T> search(K key){
        Node<K> y = this.root;
        while (y instanceof InNode) {
            InNode<K> _y = (InNode<K>) y;
            if (key.compareTo(_y.getLeft().getKey()) <= 0) {
                if (key.equals(_y.getLeft().getKey())){
                    if(_y.getLeft() instanceof Leaf) return (Leaf<K,T>)_y.getLeft();
                }
                y = _y.getLeft();
            } else if ((key.compareTo(_y.getMiddle().getKey()) <= 0)){
                if (key.equals(_y.getMiddle().getKey()))
                    if(_y.getMiddle() instanceof Leaf) return (Leaf<K,T>)_y.getMiddle();
                y = _y.getMiddle();
            }
            else {
                if (key.equals(_y.getRight().getKey()))
                    if(_y.getRight() instanceof Leaf) return (Leaf<K,T>)_y.getRight();
                y = _y.getRight();

            }
        }
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

        //that beacause the min key in the tree is -inf.
        x = x.getParent().getMiddle();
        if(x.getKey() != maxKey)
            return ((Leaf<K,T>) x).obj();
        return null;
    }

    public void insert(T obj){
        Leaf<K, T> z = new Leaf<>(obj.getKey(), obj);
        Node<K> y = this.root;
        // this loop used to find the leaf that has the next value.
        while (y instanceof InNode<?>) {
            InNode<K> _y = (InNode<K>) y;
            if (z.compareTo(_y.getLeft()) <= 0) {
                if (z.equals(_y.getLeft()))
                    throw new IllegalArgumentException("Element already exists");
                y = ((InNode<K>) y).getLeft();
            } else if (z.compareTo(_y.getMiddle()) <= 0) {
                if (z.equals(_y.getMiddle()))
                    throw new IllegalArgumentException("Element already exists");
                y = _y.getMiddle();
            }
            else {
                if (z.equals(_y.getRight()))
                    throw new IllegalArgumentException("Element already exists");
                y = _y.getRight();

            }
        }

        // at this moment y refs to the leaf that we want to put z next to it.
        InNode<K> x = y.getParent();
        // x is the subtree.

        Node<K> temp; //of course its not a leaf.
        temp = x.insertAndSplit(z);

        while (x != this.root){
            x = x.getParent();
            if (temp != null)
                temp = x.insertAndSplit(temp);
            else
                x.updateKey();
        }
        if(temp != null){
            InNode<K> w = new InNode<>(null);
            w.setChildren(x,temp,null);
            this.root = w;
        }
    }

    private InNode<K> borrowOrMerge(InNode<K> y){
        InNode<K> x, z = y.getParent();
        if(y == z.getLeft()){
            x = (InNode<K>)z.getMiddle();
            if (x.getRight() != null){
                y.setChildren(y.getLeft(), x.getLeft(),null);
                x.setChildren(x.getMiddle(), x.getRight(),null);
            }
            else{
                x.setChildren(y.getLeft(), x.getLeft(), x.getMiddle());
                z.setChildren(x,z.getRight(),null);
            }
            return z;
        }
        if(y == z.getMiddle()){
            x = (InNode<K>)z.getLeft();
            if (x.getRight() != null){
                y.setChildren(x.getRight(), y.getLeft(), null);
                x.setChildren(x.getLeft(), x.getMiddle(), null);
            }
            else{
                x.setChildren(x.getLeft(), x.getMiddle(), y.getLeft());
                z.setChildren(x, z.getRight(), null);
            }
            return z;
        }
        x = (InNode<K>) z.getMiddle();
        if(x.getRight() != null){
            y.setChildren(x.getRight(), y.getLeft(), null);
            x.setChildren(x.getLeft(), x.getMiddle(),null);
        }
        else{
            x.setChildren(x.getLeft(), x.getMiddle(),y.getLeft());
            z.setChildren(z.getLeft(), x, null);
        }
        return z;
    }
    public void deleteLeaf(Leaf<K,T> x){
        InNode<K> y = x.getParent();
        if(x == y.getLeft())
            y.setChildren(y.getMiddle(), y.getRight(), null);
        else if(x == y.getMiddle())
            y.setChildren(y.getLeft(), y.getRight(), null);
        else
            y.setChildren(y.getLeft(), y.getMiddle(), null);

        while (y != null){
            if(y.getMiddle() != null){
                y.updateKey();
                y = y.getParent();
            }
            else{
                if(y != this.root){
                    y = borrowOrMerge(y);
                }
                else{
                    this.root = (InNode<K>)(y.getLeft());
                    this.root.setParent(null);
                    return;
                }
            }
        }
    }
    public void delete(K key){
        Leaf<K,T> leaf = search(key);
        if(leaf == null)
            throw new IllegalArgumentException("the key '" + key + "' does not exists.");

        deleteLeaf(leaf);
    }
}


