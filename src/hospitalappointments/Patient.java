package hospitalappointments;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ofentse
 */
public class Patient {

    SimpleStringProperty id, fname, lname, cell, email, physicalAddress,
                         postalAddress, occupation, gender;

    public Patient(String id,String fname,String lname, String cell,String email, String gender,
                   String physicalAddress, String postalAddress, String occupation) {
        
        this.id = new SimpleStringProperty(id);
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
        this.cell = new SimpleStringProperty(cell);
        this.email = new SimpleStringProperty(email);
        this.gender = new SimpleStringProperty(gender);
        this.physicalAddress = new SimpleStringProperty(physicalAddress);
        this.postalAddress = new SimpleStringProperty(postalAddress);
        this.occupation = new SimpleStringProperty(occupation);
    }
    
    /**
     * Default constructor ---
     */
    public Patient() {
        
        this.id = new SimpleStringProperty("");
        this.fname = new SimpleStringProperty("");
        this.lname = new SimpleStringProperty("");
        this.cell = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.gender = new SimpleStringProperty("");
        this.physicalAddress = new SimpleStringProperty("");
        this.postalAddress = new SimpleStringProperty("");
        this.occupation = new SimpleStringProperty("");
    }

    //-- Getter methods --
    public String getId() { return id.get();
    }

    public String getFname() { return fname.get();
    }

    public String getLname() { return lname.get();
    }

    public String getCell() { return cell.get();
    }

    public String getEmail() { return email.get();
    }
    
    public String getGender() { return gender.get();
    }

    public String getPhysicalAddress() { return physicalAddress.get();
    }

    public String getPostalAddress() { return postalAddress.get();
    }

    public String getOccupation() { return occupation.get();
    }

    
    //-- Setter methods --
    public void setId(String id) {
        this.id.set(id);
    }

    public void setFname(String fname) {
        this.fname.set(fname);
    }

    public void setLname(String lname) {
        this.lname.set(lname);
    }

    public void setCell(String cell) {
        this.cell.set(cell);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
    
    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress.set(physicalAddress);
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress.set(postalAddress);
    }

    public void setOccupation(String occupation) {
        this.occupation.set(occupation);
    }
        
}
