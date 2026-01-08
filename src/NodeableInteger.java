public class NodeableInteger implements Nodeable{
    Integer key;
    public NodeableInteger(int key){
        this.key = key;
    }
    @Override
    public String getKey(){return key.toString();}

    @Override
    public int getValue(){return key;}
}
