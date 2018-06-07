package hospitalappointments;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ofentse
 */
public class Doctor {

    SimpleStringProperty id, fname, lname, cell, tel, email, department, gender;

    public Doctor(String id,String fname,String lname, String cell, String tel, 
                  String email, String gender, String departments) {
        
        this.id = new SimpleStringProperty(id);
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
        this.cell = new SimpleStringProperty(cell);
        this.tel = new SimpleStringProperty(tel);
        this.email = new SimpleStringProperty(email);
        this.gender = new SimpleStringProperty(gender);
        this.department = new SimpleStringProperty(departments);
    }
    
    /**
     * Default constructor ---
     */
    public Doctor() {
        
        this.id = new SimpleStringProperty("");
        this.fname = new SimpleStringProperty("");
        this.lname = new SimpleStringProperty("");
        this.cell = new SimpleStringProperty("");
        this.tel = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.gender = new SimpleStringProperty("");
        this.department = new SimpleStringProperty("");
        
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

    public String getTel() { return tel.get();
    }


    public String getEmail() { return email.get();
    }
    
    public String getGender() { return gender.get();
    }
    
    public String getDepartment() { return department.get();
    
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

    public void setTel(String tel) {
        this.tel.set(tel);
    }


    public void setEmail(String email) {
        this.email.set(email);
    }

        
}
