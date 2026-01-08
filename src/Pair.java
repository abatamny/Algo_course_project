public class Pair {
    int first;
    int second;
    public Pair(int x, int y){
        this.first = x;
        this.second = y;
    }
    public int first(){
        return first;
    }
    public int second(){
        return second;
    }
    public void mines(int x, int y){
        this.first -= x;
        this.second -= y;
    }
    public int[] getPair(){
        return new int[]{first, second};
    }
}
