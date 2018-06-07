package hospitalappointments;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ofentse
 */
public class Appointment {

    SimpleStringProperty id, patientID, doctorID, date, description, status;

    public Appointment(String id,String patientID, String doctorID, String date, String description, String status) {
        
        this.id = new SimpleStringProperty(id);
        this.patientID = new SimpleStringProperty(patientID);
        this.doctorID = new SimpleStringProperty(doctorID);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
        this.status = new SimpleStringProperty(status);
    }
    
    /**
     * Default constructor ---
     */
    public Appointment() {
        
        this.id = new SimpleStringProperty("");
        this.patientID = new SimpleStringProperty("");
        this.doctorID = new SimpleStringProperty("");
        this.date = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.status = new SimpleStringProperty("");
    }

    //-- Getter methods --
    public String getId() { return id.get();
    }

    public String getPatientID() { return patientID.get();
    }

    public String getDoctorID() { return doctorID.get();
    }

    public String getDate() { return date.get();
    }

    public String getDescription() { return description.get();
    }
    
    public String getStatus() { return status.get();
    }
    
    //-- Setter methods --
    public void setId(String id) {
        this.id.set(id);
    }

    public void setPatientID(String pID) {
        this.patientID.set(pID);
    }

    public void setDoctorID(String dID) {
        this.doctorID.set(dID);
    }

    public void setDate(String dat) {
        this.date.set(dat);
    }

    public void setDescription(String descr) {
        this.description.set(descr);
    }
    
    public void setStatus(String status) {
        this.status.set(status);
    }
     
}
