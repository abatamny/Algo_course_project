public class TwoThreeTree <T extends Nodeable>{
    private InNode root;
    private final String minKey;
    private final String maxKey;
    private final int leafMaxWeight;

    public TwoThreeTree(String minKey, String maxKey, int leafMaxWeight){
        this.minKey = minKey;
        this.maxKey = maxKey;
        this.leafMaxWeight = leafMaxWeight;
        this.init();
    }
    private void init(){
        root = new InNode(null);
        Leaf<T> l = new Leaf<>(this.minKey);
        Leaf<T> m = new Leaf<>(this.maxKey);
        root.setChildren(l,m,null);
    }

    public T getByKey(String key){
        Leaf <T> foundedLeaf = leafSearch(key);
        if(foundedLeaf != null){
            if(foundedLeaf.equals(key))
                return foundedLeaf.obj();
        }
        return null;
    }
    /* returns the object if founded, else the previous leaf.*/
    private Leaf<T> leafSearch(String key){
        Node pointer = this.root;
        while (pointer instanceof InNode) {
            InNode _pointer = (InNode) pointer;
            if (key.compareTo(_pointer.getLeft().getKey()) <= 0) {
                pointer = _pointer.getLeft();
            } else if ((key.compareTo(_pointer.getMiddle().getKey()) <= 0)){
                pointer = _pointer.getMiddle();
            }
            else {
                pointer = _pointer.getRight();
            }
        }
        return (Leaf<T>) pointer;
    }

    public void insert(T obj){
        String newKey = obj.getKey();
        Leaf<T> newLeaf = new Leaf<>(obj.getKey(), obj);
        Leaf<T> founded = leafSearch(newKey);

        if(founded.equals(newKey)){
            if(this.leafMaxWeight > -1){
                founded.increaseSize();
                founded.updateWeight();
                founded.getParent().updateTillRoot();
                return;
            }
            else
                throw new IllegalArgumentException("this element already exists.");

        }

        InNode currentsubTree = founded.getParent();
        Node newSubTree = currentsubTree.insertAndSplit(newLeaf);
        while (currentsubTree != this.root){
            currentsubTree = currentsubTree.getParent();
            if (newSubTree != null)
                newSubTree = currentsubTree.insertAndSplit(newSubTree);
            else
                currentsubTree.updateParameters();
        }
        if(currentsubTree != null){
            InNode newRoot = new InNode(null);
            newRoot.setChildren(currentsubTree,newSubTree,null);
            this.root = newRoot;
        }
    }

    private InNode borrowOrMerge(InNode y){
        InNode x, z = y.getParent();
        if(y == z.getLeft()){
            x = (InNode)z.getMiddle();
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
            x = (InNode)z.getLeft();
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
        x = (InNode) z.getMiddle();
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
    public void deleteLeaf(Leaf<T> leaf){

        if(leaf.obj() instanceof Insertable)
            if (leaf.getSize() > 1){
                leaf.decreaseSize();
                leaf.updateWeight();
                leaf.getParent().updateTillRoot();
                return;
            }
        InNode currentSubTree = leaf.getParent();


        if(leaf == currentSubTree.getLeft())
            currentSubTree.setChildren(currentSubTree.getMiddle(), currentSubTree.getRight(), null);
        else if(leaf == currentSubTree.getMiddle())
            currentSubTree.setChildren(currentSubTree.getLeft(), currentSubTree.getRight(), null);
        else
            currentSubTree.setChildren(currentSubTree.getLeft(), currentSubTree.getMiddle(), null);

        while (currentSubTree != null){
            if(currentSubTree.getMiddle() != null){
                currentSubTree.updateKey();
                currentSubTree = currentSubTree.getParent();
            }
            else{
                if(currentSubTree != this.root){
                    currentSubTree = borrowOrMerge(currentSubTree);
                }
                else{
                    this.root = (InNode)(currentSubTree.getLeft());
                    this.root.setParent(null);
                    return;
                }
            }
        }
    }
    public void delete(String key){
        Leaf<T> leaf = leafSearch(key);
        if(leaf == null || !leaf.equals(key))
            throw new IllegalArgumentException("the key '" + key + "' does not exists.");

        deleteLeaf(leaf);
    }

    public int[] inRngeGetWeightAndSize(String min, String max) {
        Node current = this.root;

        Pair currentValues = new Pair(current.getWeight(), current.getSize());
        while (current instanceof InNode) {
            InNode temp = (InNode) current;

            if (temp.getLeft().compareTo(min) >= 0)
                current = temp.getLeft();

            else if (temp.getMiddle() != null) {
                if (temp.getMiddle().compareTo(min) >= 0) {
                    currentValues.mines(temp.getLeft().getWeight(), temp.getLeft().getSize());
                    current = temp.getMiddle();
                }
            } else if (temp.getRight() != null) {
                if (temp.getRight().compareTo(min) >= 0) {
                    currentValues.mines(temp.getMiddle().getWeight(), temp.getMiddle().getSize());
                    currentValues.mines(temp.getLeft().getWeight(), temp.getLeft().getSize());
                    current = temp.getRight();
                }
            }
        }
        if (current.getKey().compareTo(min) < 0)
            currentValues.mines(current.getWeight(), current.getSize());

        current = this.root;
        while (current instanceof InNode) {
            InNode temp = (InNode) current;

            if (temp.getRight() != null) {
                if (temp.getRight().compareTo(max) <= 0) {
                    current = temp.getRight();
                }
            }
            if(temp.getMiddle() != null){
                if(temp.getMiddle().compareTo(max) <= 0){
                    currentValues.mines(temp.getRight().getWeight(), temp.getRight().getSize());
                    current = temp.getMiddle();
                }
            }

            currentValues.mines(temp.getMiddle().getWeight(), temp.getMiddle().getSize());
            currentValues.mines(temp.getRight().getWeight(), temp.getRight().getSize());
            current = temp.getLeft();
        }
        if (current.getKey().compareTo(max) > 0)
            currentValues.mines(current.getWeight(), current.getSize());

        return new int[] {currentValues.first, currentValues.second};
    }

}
