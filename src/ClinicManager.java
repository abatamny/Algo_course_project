public class ClinicManager {
    public static final String MIN_ID = "";
    public static final String MAX_ID = "\uFFFF\uFFFF\uFFFF\uFFFF";


    private Tree23<String, Doctor> doctors;
    private Tree23<String, Patient> patients;
    public ClinicManager() {
        doctors = new Tree23<>(MIN_ID, MAX_ID);
        patients = new Tree23<>(MIN_ID, MAX_ID);
    }

    public void doctorEnter(String doctorId) {
        Doctor newDoc = new Doctor(doctorId);
        doctors.insert(newDoc);
    }

    public void doctorLeave(String doctorId) {
        doctors.delete(doctorId);
    }

    public void patientEnter(String doctorId, String patientId) {
        Doctor doc = doctors.getByKey(doctorId);//log(D)
        if(doc == null) throw new IllegalArgumentException("the doctorid does not exist.");

        Patient p = new Patient(patientId, doc);

        patients.insert(p);//log(P)

    }

    public String nextPatientLeave(String doctorId) {
        Doctor doc = doctors.getByKey(doctorId);//log(D)
        Patient p = doc.nextPatientLeave();//O(1)
        patients.delete(p.getKey());//log(D)
        return p.getKey();
    }

    public void patientLeaveEarly(String patientId) {
        Patient p = patients.getByKey(patientId);
        if(p == null) throw new IllegalArgumentException("patient does not exists.");
    }

    public int numPatients(String doctorId) {
        return 0;
    }

    public String nextPatient(String doctorId) {
        return null;
    }

    public String waitingForDoctor(String patientId) {
        return null;
    }

    public int numDoctorsWithLoadInRange(int low, int high) {
        return 0;
    }

    public int averageLoadWithinRange(int low, int high) {
        return 0;
    }
}