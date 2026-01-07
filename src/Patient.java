public class Patient implements Nodeable<String> {
    private String PID;
    private Doctor doctor;
    private Queue.QNode place = null;
    public Patient(String id, Doctor wr){
        PID = id;
        this.doctor = wr;
    }
    public void setPlace(Queue.QNode place){
        this.place = place;
    }
    public Queue.QNode getPlace(){return place;}
    public Doctor getDoctor(){return doctor;}
    @Override
    public String getKey(){return PID;}
    @Override
    public int getWeight(){return 1;}
}
