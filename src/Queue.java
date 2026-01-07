public class Queue<T> implements Nodeable<Integer> {
    public class QNode<T>  {
        private T obj;
        public QNode<T> next = null;
        public QNode<T> prev = null;
        public Queue<T> parent;
        public QNode(T key, Queue<T> p) {
            this.obj = key;
            this.parent = p;
        }

        //if this node was in a linkedlist, then it can takes itself off.
        public void takeoff() {
            QNode<T> p = this.prev;
            QNode<T> n = this.next;
            p.next = n;
            n.prev = p;
            this.parent.countDown();
        }
    }


    private QNode<T> head = null;
    private int size = 0;
    public void countDown(){size--;}
    public void push(T k) {
        QNode<T> newHead = new QNode<>(k, this);
        newHead.next = this.head;
        if (this.head != null)
            this.head.prev = newHead;
        this.head = newHead;
        this.size++;
    }

    public T pop() {
        if (head == null) throw new IllegalArgumentException("Empty Queue");
        T ret = this.head.obj;
        this.head = this.head.next;
        this.head.prev = null;
        this.size--;
        return ret;
    }

    public QNode<T> getHeadNode() {
        return this.head;
    }

    public T next() {
        if (head == null) throw new IllegalArgumentException("Empty Queue");
        return this.head.obj;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public Integer getKey() {
        return this.size;
    }
    @Override
    public int getWeight(){return 1;}
}