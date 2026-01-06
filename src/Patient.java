public class Patient implements Nodeable<String> {
    private String PID;
    private Doctor doctor;
    public Patient(String id, Doctor wr){
        PID = id;
        this.doctor = wr;
    }
    public Doctor getDoctorID(){return doctor;}
    @Override
    public String getKey(){return PID;}
}
