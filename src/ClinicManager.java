public class ClinicManager {
    public static final String MIN_ID = "";
    public static final String MAX_ID = "\uFFFF\uFFFF\uFFFF\uFFFF";


    private TwoThreeTree<Doctor> doctors;
    private TwoThreeTree<Patient> patients;
    private TwoThreeTree<NodeableInteger> Statictics;
    public ClinicManager() {
        doctors = new TwoThreeTree<>(MIN_ID, MAX_ID, 1);
        patients = new TwoThreeTree<>(MIN_ID, MAX_ID, 1);
        Statictics = new TwoThreeTree<>(MIN_ID, MAX_ID, -1);
    }

    public void doctorEnter(String doctorId) {
        Doctor newDoc = new Doctor(doctorId);
        Statictics.insert(new NodeableInteger(0));
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
        Statictics.delete("0");
    }

    public void patientEnter(String doctorId, String patientId) {
        Doctor doc = doctors.getByKey(doctorId);//log(D)

        if(doc == null)
            throw new IllegalArgumentException("this doctor is not exist.");

        Patient p = new Patient(patientId, doc);
        patients.insert(p);//log(P)
        Statictics.insert(new NodeableInteger(doc.waitingNum()));
        doc.enterPatient(p);
        Statictics.insert(new NodeableInteger(doc.waitingNum()));
    }

    public String nextPatientLeave(String doctorId) {
        Doctor doc = doctors.getByKey(doctorId);//log(D)
        if(doc == null)
            throw new IllegalArgumentException("this doctor is not exist.");
        Patient p = doc.nextPatientLeave();//O(1)
        if(p == null)
            throw new IllegalArgumentException("the waiting room of doctor is empty.");
        Statictics.delete(new Integer(p.getDoctor().waitingNum() + 1).toString());//O(log D)
        patients.delete(p.getKey());//log(D)
        Statictics.insert(new NodeableInteger(p.getDoctor().waitingNum()));//O(log D)
        return p.getKey();
    }

    public void patientLeaveEarly(String patientId) {
        Patient p = patients.getByKey(patientId);
        if(p == null) throw new IllegalArgumentException("patient does not exists.");

        Statictics.delete(new Integer(p.getDoctor().waitingNum()).toString());//O(log D)
        p.getPlace().takeoff();//O(1) remove from the waiting queue.
        patients.delete(p.getKey());//O(log(P)
        Statictics.insert(new NodeableInteger(p.getDoctor().waitingNum()));//O(log D)
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
        return Statictics.inRngeGetWeightAndSize(new Integer(low).toString(),new Integer(high).toString())[0];
    }

    public int averageLoadWithinRange(int low, int high) {
        int[] vals = Statictics.inRngeGetWeightAndSize(new Integer(low).toString(),new Integer(high).toString());
        return (int) Math.floor(vals[0]/vals[1]);
    }
}