public class Patient implements Nodeable<String> {
    private String PID;
    private Doctor doctor;
    private Queue<Patient>.QNode<Patient> place = null;
    public Patient(String id, Doctor wr){
        PID = id;
        this.doctor = wr;
    }
    public void setPlace(Queue<Patient>.QNode<Patient> place){
        this.place = place;
    }
    public Queue<Patient>.QNode<Patient> getPlace(){return place;}
    public Doctor getDoctor(){return doctor;}
    @Override
    public String getKey(){return PID;}
    @Override
    public int getWeight(){return 1;}
    @Override
    public float getValue(){return 0;}
}
