public class Queue<T> {
    private class QNode<T>{
        private T obj;
        public QNode<T> next;
        public QNode(T key) {
            this.obj = key;
        }
    }


    private QNode<T> head;
    private int size = 0;
    public void push(T k){
        QNode<T> newHead = new QNode<>(k);
        newHead.next = this.head;
        this.head = newHead;
        this.size++;
    }
    public T pop(){
        if(head == null) throw new IllegalArgumentException("Empty Queue");
        T ret = this.head.obj;
        this.head = this.head.next;
        this.size --;
        return ret;
    }
    public T next(){
        if(head == null) throw new IllegalArgumentException("Empty Queue");
        return this.head.obj;
    }
}
