public class Doctor implements Nodeable<String> {
    private String DID;
    private Queue<Patient> wd;

    public Doctor(String id){
        DID = id;
        wd = new Queue<>();
    }
    @Override
    public String getKey(){return DID;}

    public void enterPatient(Patient p){
        wd.push(p);
    }
    public Patient nextPatient() {
            return wd.next();
    }
    public Patient nextPatientLeave(){return wd.pop();}
}
