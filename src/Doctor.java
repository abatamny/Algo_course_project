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
        wd.insert(p);
        p.setPlace(wd.getLastNode());
    }
    public Patient nextPatient() {
            return wd.peek();
    }
    public Patient nextPatientLeave(){return wd.remove();}
    public int waitingNum(){return wd.getSize();}
    @Override
    public float getValue(){
        return 0;
    }
}
