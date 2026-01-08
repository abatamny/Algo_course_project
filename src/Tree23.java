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
                    if(maxP instanceof Leaf && !(maxP.getKey().equals(max)))
                        weight -= maxP.getWeight();
                    continue;
                }
            }

            if(p.getRight() != null){
                maxP = p.getRight();
            }
            else if(p.getMiddle() != null)
                maxP = p.getMiddle();
            else
                maxP = p.getLeft();

            if(maxP instanceof Leaf && max.compareTo(maxP.getKey()) < 0)
                    weight -= maxP.getWeight();
        }
        while(minP instanceof InNode){
            InNode<K> p = (InNode<K>) minP;
            if(p.getLeft()!= null){
                if(min.compareTo(p.getLeft().getKey()) <= 0){
                    minP = p.getLeft();
                    continue;
                }
            }
            if(p.getMiddle()!=null){
                if(min.compareTo(p.getMiddle().getKey()) <= 0){
                    minP = p.getMiddle();
                    weight -= (p.getLeft().getWeight());
                    continue;
                }
            }
            if(p.getRight()!=null){
                if(min.compareTo(p.getRight().getKey()) <= 0){
                    minP = p.getRight();
                    weight -= (p.getLeft().getWeight() + p.getMiddle().getWeight());
                    continue;
                }
            }
            weight -= p.getWeight();
            break;
            
        }
        return weight;
    }


    public int averageValueInRange(K min, K max) {
        float value = this.root.getValue();
        int weight = this.root.getWeight();

        Node<K> maxP = this.root, minP = this.root;

        while(maxP instanceof InNode){
            InNode<K> p = (InNode<K>) maxP;
            if(p.getLeft() != null){
                if(max.compareTo(p.getLeft().getKey()) <= 0) {
                    maxP = p.getLeft();
                    if(p.getMiddle() != null) {
                        value -= p.getMiddle().getValue();
                        weight -= p.getMiddle().getWeight();
                    }
                    if(p.getRight() != null){
                        value -= p.getRight().getValue();
                        weight -= p.getRight().getWeight();
                    }
                    if(maxP instanceof Leaf && (max.compareTo(maxP.getKey()) < 0)){
                        value -= maxP.getValue();
                        weight -= maxP.getWeight();
                    }
                    continue;
                }
            }
            if(p.getMiddle() != null){
                if(max.compareTo(p.getMiddle().getKey()) <= 0) {
                    maxP = p.getMiddle();
                    if(p.getRight() != null){
                        value -= p.getRight().getValue();
                        weight -= p.getRight().getWeight();
                    }
                    if(maxP instanceof Leaf && !(max.compareTo(maxP.getKey()) < 0)){
                        value -= maxP.getValue();
                        weight -= maxP.getWeight();
                    }
                    continue;
                }
            }

            if(p.getRight() != null){
                maxP = p.getRight();
                if(maxP instanceof Leaf && max.compareTo(maxP.getKey()) < 0){
                    value -= maxP.getValue();
                    weight -= maxP.getWeight();
                }
            }
            else if(p.getMiddle() != null)
                maxP = p.getMiddle();
            else
                maxP = p.getLeft();
        }
        while(minP instanceof InNode){
            InNode<K> p = (InNode<K>) minP;
            if(p.getLeft()!= null){
                if(min.compareTo(p.getLeft().getKey()) <= 0){
                    minP = p.getLeft();
                    continue;
                }
            }
            if(p.getMiddle()!=null){
                if(min.compareTo(p.getMiddle().getKey()) <= 0){
                    minP = p.getMiddle();
                    value -= (p.getLeft().getValue());
                    weight -= (p.getLeft().getWeight());
                    continue;
                }
            }
            if(p.getRight()!=null){
                if(min.compareTo(p.getRight().getKey()) <= 0){
                    minP = p.getRight();
                    value -= (p.getMiddle().getValue() + p.getLeft().getValue());
                    weight -= (p.getMiddle().getWeight() + p.getLeft().getWeight());
                    continue;
                }
            }
            weight -= p.getWeight();
            value -= p.getValue();
            break;
        }
        if (weight == 0) return 0;
        return (int) Math.floor((value/weight));
    }
}

