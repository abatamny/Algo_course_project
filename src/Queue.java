public class Queue<T> {
    private class QNode<T>{
        private T obj;
        public QNode<T> next;
        public QNode(T key) {
            this.obj = key;
        }
    }


    private QNode<T> head;

    public void push(T k){
        QNode<T> newHead = new QNode<>(k);
        newHead.next = this.head;
        this.head = newHead;
    }
    public T pop(){
        T ret = this.head.obj;
        this.head = this.head.next;
        return ret;
    }
    public T next(){
        return this.head.obj;
    }
}
