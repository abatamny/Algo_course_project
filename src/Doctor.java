public class Doctor implements Nodeable<String> {
    private String DID;
    private Queue<Patient> wd;

    public Doctor(String id){
        DID = id;
        wd = new Queue<>();
    }
    @Override
    public String getKey(){return DID;}
    @Override
    public int getWeight(){return 1;}
    public void enterPatient(Patient p){
        wd.push(p);
        p.setPlace(wd.getHeadNode());
    }
    public Patient nextPatient() {
            return wd.next();
    }
    public Patient nextPatientLeave(){return wd.pop();}
    public int waitingNum(){return wd.getSize();}
}
