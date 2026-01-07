public class Waitings implements Nodeable<Integer>, Insertable {
    private Integer numOfWaiting;
    private Integer numOfRooms;

    public Waitings(){
        this.numOfWaiting = 0;
        this.numOfRooms = 1;
    }
    public Waitings(int numOfWaiting){
        this.numOfWaiting = numOfWaiting;
        this.numOfRooms = 1;
    }
    @Override
    public Integer getKey(){return numOfWaiting;}

    @Override
    public int getWeight(){return numOfRooms;}

    public void removeRoom(){
        this.numOfRooms--;
    }
    public void addRoom(){
        this.numOfRooms++;
    }

    public void insert(){
        numOfRooms++;
    }
    public void remove(){
        numOfRooms--;
    }

    @Override
    public float getValue(){return numOfWaiting*numOfRooms;}

}
