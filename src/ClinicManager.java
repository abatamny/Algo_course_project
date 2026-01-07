public class ClinicManager {
    public static final String MIN_ID = "";
    public static final String MAX_ID = "\uFFFF\uFFFF\uFFFF\uFFFF";


    private Tree23<String, Doctor> doctors;
    private Tree23<String, Patient> patients;
    private Tree23<Integer, Waitings> waitings;
    public ClinicManager() {
        doctors = new Tree23<>(MIN_ID, MAX_ID);
        patients = new Tree23<>(MIN_ID, MAX_ID);
        waitings = new Tree23<>(-1, Integer.MAX_VALUE);
    }

    public void doctorEnter(String doctorId) {
        Doctor newDoc = new Doctor(doctorId);
        waitings.insert(new Waitings());
        try{
            doctors.insert(newDoc);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("the doctor is already exists.");
        }
    }

    public void doctorLeave(String doctorId) {
        Doctor d = doctors.getByKey(doctorId);
        if(d == null)
            throw new IllegalArgumentException("this doctor is not exist.");

        if(d.waitingNum() > 0)
            throw new IllegalArgumentException("can't leave: petients are waiting");

        doctors.delete(doctorId);
        waitings.delete(0);
    }

    public void patientEnter(String doctorId, String patientId) {
        Doctor doc = doctors.getByKey(doctorId);//log(D)

        if(doc == null)
            throw new IllegalArgumentException("his doctor is not exist.");

        Patient p = new Patient(patientId, doc);
        patients.insert(p);//log(P)
        doc.enterPatient(p);
        waitings.delete(doc.waitingNum()-1);
        waitings.insert(new Waitings(doc.waitingNum()));
    }

    public String nextPatientLeave(String doctorId) {
        Doctor doc = doctors.getByKey(doctorId);//log(D)
        Patient p = doc.nextPatientLeave();//O(1)
        waitings.delete(p.getDoctor().waitingNum() + 1);//O(log D)
        patients.delete(p.getKey());//log(D)
        waitings.insert(new Waitings(p.getDoctor().waitingNum()));//O(log D)
        return p.getKey();
    }

    public void patientLeaveEarly(String patientId) {
        Patient p = patients.getByKey(patientId);
        if(p == null) throw new IllegalArgumentException("patient does not exists.");

        waitings.delete(p.getDoctor().waitingNum());//O(log D)
        p.getPlace().takeoff();//O(1) remove from the waiting queue.
        patients.delete(p.getKey());//O(log(P)
        waitings.insert(new Waitings(p.getDoctor().waitingNum()));//O(log D)
    }

    public int numPatients(String doctorId){
        Doctor doc = doctors.getByKey(doctorId);//log(D)
        if(doc == null) throw new IllegalArgumentException("the doctorid does not exist.");
        return doc.waitingNum();
    }

    public String nextPatient(String doctorId) {
        Doctor doc = doctors.getByKey(doctorId);//log(D)
        if(doc == null) throw new IllegalArgumentException("the doctorid does not exist.");
        try {
            return doc.nextPatient().getKey();
        }catch (Exception e){
            throw new IllegalArgumentException("no patients waiting");
        }
    }

    public String waitingForDoctor(String patientId) {
        Patient p = patients.getByKey(patientId);
        if(p == null) throw new IllegalArgumentException("patient does not exists.");
        return p.getDoctor().getKey();
    }

    public int numDoctorsWithLoadInRange(int low, int high) {
        return waitings.weightInRange(low,high);
    }

    public int averageLoadWithinRange(int low, int high) {
        return 0;
    }
}