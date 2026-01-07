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
    public T getByKey(K key){
        Leaf<K,T> l = leafSearch(key);
        if(l != null)return l.obj();
        return null;
    }
    /* returns the object if founded, else null if not founded.*/
    private Leaf<K, T> leafSearch(K key){
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


    public void insert(T obj){
        Leaf<K, T> z = new Leaf<>(obj.getKey(), obj);
        Node<K> y = this.root;
        // this loop used to find the leaf that has the next value.
        while (y instanceof InNode) {
            InNode<K> _y = (InNode<K>) y;

            if (z.compareTo(_y.getLeft()) <= 0)
                y = _y.getLeft();

            else if (z.compareTo(_y.getMiddle()) <= 0)
                y = _y.getMiddle();

            else
                y = _y.getRight();
        }



        InNode<K> x = y.getParent();
        // x is the subtree.
        // at this moment y refs to the leaf that we want to put z next to it.
        if(y.getKey().equals(obj.getKey())){
            if( ((Leaf<K,T>) y).obj() instanceof Insertable ) {
                ((Insertable) ((Leaf<K, T>) y).obj()).insert();
                y.setWeight(y.getWeight()+1);
                y.setValue(y.getValue() + (Integer)((Leaf<?, ?>) y).obj().getKey());
                while(x != null){
                    x.setWeight(x.getWeight()+1);
                    x.setValue(x.getValue() + (Integer)((Leaf<?, ?>) y).obj().getKey());
                    x = x.getParent();
                }
                return;
            }
            else
                throw new IllegalArgumentException("this element already exists.");
        }
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
    public void deleteLeaf(Leaf<K,T> leaf){
        Node<K> x = leaf;
        if(leaf.obj() instanceof Insertable)
            if (leaf.obj().getWeight() > 1){
                ((Insertable) leaf.obj()).remove();
                leaf.setKey(leaf.getKey());
                while(x != null){
                    x.setWeight(x.getWeight()-1);
                    x.setValue(x.getValue()-(Integer)leaf.getKey());
                    x = x.getParent();
                }
                return;
            }
        InNode<K> y = leaf.getParent();
        if(leaf == y.getLeft())
            y.setChildren(y.getMiddle(), y.getRight(), null);
        else if(leaf == y.getMiddle())
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
        Leaf<K,T> leaf = leafSearch(key);
        if(leaf == null)
            throw new IllegalArgumentException("the key '" + key + "' does not exists.");


        deleteLeaf(leaf);
    }

    public int weightInRange(K min, K max){
        int weight = this.root.getWeight();
        Node<K> maxP = this.root, minP = this.root;
        while(maxP instanceof InNode){
            InNode<K> p = (InNode<K>) maxP;
            if(p.getLeft() != null){
                if(max.compareTo(p.getLeft().getKey()) <= 0) {
                    maxP = p.getLeft();
                    if(p.getMiddle() != null)
                        weight -= p.getMiddle().getWeight();
                    if(p.getRight() != null)
                        weight -= p.getRight().getWeight();
                    continue;
                }
            }
            if(p.getMiddle() != null){
                if(max.compareTo(p.getMiddle().getKey()) <= 0) {
                    maxP = p.getMiddle();
                    if(p.getRight() != null)
                        weight -= (p.getRight().getWeight());
                    if(maxP instanceof Leaf)
                        weight -= maxP.getWeight();
                    continue;
                }
            }

            if(p.getRight() != null){
                maxP = p.getRight();
                if(maxP instanceof Leaf && max.compareTo(maxP.getKey()) < 0)
                    weight -= maxP.getWeight();
            }
            else if(p.getMiddle() != null)
                maxP = p.getMiddle();
            else
                maxP = p.getLeft();
        }
        while(minP instanceof InNode){
            InNode<K> p = (InNode<K>) minP;
            if(p.getRight()!=null){
                if(p.getRight().getKey().compareTo(min) <= 0){
                    minP = p.getRight();
                    weight -= (p.getMiddle().getWeight() + p.getLeft().getWeight());
                    continue;
                }
            }
            if(p.getMiddle() != null){
                if(p.getMiddle().getKey().compareTo(min) <= 0){
                    minP = p.getMiddle();
                    weight -= (p.getLeft().getWeight());
                    continue;
                }
            }
            if(p.getLeft() != null){
                minP = p.getLeft();
            }
        }
        return weight;
    }


    public float averageValueInRange(K min, K max) {
        float value = this.root.getWeight();
        int size = this.root.getSize();

        Node<K> maxP = this.root, minP = this.root;

        while (maxP instanceof InNode) {
            InNode<K> p = (InNode<K>) maxP;
            if (p.getLeft() != null) {
                if (max.compareTo(p.getLeft().getKey()) <= 0) {
                    maxP = p.getLeft();
                    if (p.getMiddle() != null){
                        value -= p.getMiddle().getValue();
                        size -= p.getMiddle().getSize();
                    }


                    if (p.getRight() != null) {
                        value -= p.getRight().getValue();
                        size -= p.getRight().getSize();
                    }
                    continue;
                }
            }
            if (p.getMiddle() != null) {
                if (max.compareTo(p.getMiddle().getKey()) <= 0) {
                    maxP = p.getMiddle();
                    if (p.getRight() != null){
                        value -= p.getRight().getValue();
                        size -= p.getRight().getSize();
                    }

                    if (maxP instanceof Leaf){
                        value -= maxP.getValue();
                        size -= maxP.getSize();
                    }
                    continue;
                }
            }

            if (p.getRight() != null) {
                maxP = p.getRight();
                if (maxP instanceof Leaf && max.compareTo(maxP.getKey()) < 0){
                    value -= maxP.getValue();
                    size -= maxP.getSize();
                }
            } else if (p.getMiddle() != null)
                maxP = p.getMiddle();
            else
                maxP = p.getLeft();
        }

        while (minP instanceof InNode) {
            InNode<K> p = (InNode<K>) minP;
            if (p.getRight() != null) {
                if (p.getRight().getKey().compareTo(min) <= 0) {
                    minP = p.getRight();
                    value -= (p.getMiddle().getValue() + p.getLeft().getValue());
                    size -= (p.getMiddle().getSize() + p.getLeft().getSize());
                    continue;
                }
            }
            if (p.getMiddle() != null) {
                if (p.getMiddle().getKey().compareTo(min) <= 0) {
                    minP = p.getMiddle();
                    value -= p.getLeft().getValue();
                    size -= p.getLeft().getSize();
                    continue;
                }
            }
            if (p.getLeft() != null) {
                minP = p.getLeft();
            }
        }
        return value/size;
    }
}

