package hospitalappointments;

import static hospitalappointments.DoctorAppointments.getDoctorNames;
import static hospitalappointments.DoctorAppointments.updateDoctorAppointment;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author ofentse
 */
public class UpdateAppointment extends Stage{

    public UpdateAppointment(Appointment appointment) {
        
        BorderPane root = new BorderPane();
        
        GridPane grid = new GridPane();
        grid.getStyleClass().add("form");
        grid.setVgap(5);
        grid.setHgap(5);
        
        ComboBox<String> patientName = new ComboBox<>(MainUI.connector.getPatientNames());
        patientName.setPrefWidth(280);
        
        grid.add(new Label("Patient Name"), 0, 0);
        grid.add(patientName, 1, 0);
        
        ComboBox<String> doctor = new ComboBox<>(getDoctorNames());
        doctor.setValue(doctor.getItems().get(0));
        doctor.setPrefWidth(280);
        
        grid.add(new Label("Doctor Name"), 0, 1);
        grid.add(doctor, 1, 1);
        
        DatePicker date = new DatePicker();
        date.setPrefWidth(280);
        
        grid.add(new Label("Appointment Date"), 0, 2);
        grid.add(date, 1, 2);
        
        TextArea description = new TextArea();
        description.setPrefWidth(280);
        
        grid.add(new Label("Description"), 0, 3);
        grid.add(description, 1, 3);
        
        root.setCenter(grid);
        
        HBox status = new HBox(10);
        
        
        status.setPadding(new Insets(3, 0, 3, 0));
        RadioButton cancelled = new RadioButton("Cancelled");
        RadioButton closed = new RadioButton("Closed");
        RadioButton open = new RadioButton("Open");
        
        grid.add(new Label("Status"), 0, 4);
        grid.add(status, 1, 4);
        
        ToggleGroup tg = new ToggleGroup();
        cancelled.setToggleGroup(tg);
        closed.setToggleGroup(tg);
        open.setToggleGroup(tg);
        
        tg.selectToggle(open);
        
        status.getChildren().addAll(cancelled, closed, open);
        status.setAlignment(Pos.CENTER_LEFT);
        
        Button save = new Button("Save Changes");
        save.getStyleClass().add("custom-button");
        save.setOnAction((ActionEvent event) -> {
            
            String appontmentStatus = "";
            if(open.isSelected()){appontmentStatus = "1";}
            if(closed.isSelected()){appontmentStatus = "0";}
            if(cancelled.isSelected()){appontmentStatus = "2";}
            
            if(appointment != null){ //-- Update --
               
                //-- form validation --
                if(patientName.getValue() != null && !patientName.getValue().trim().equalsIgnoreCase("") &&
                   date.getValue()!= null && !date.getValue().equals("")){
               
                    appointment.setDescription(description.getText().trim());
                    appointment.setPatientID(MainUI.connector.getPatientByName(patientName.getValue().trim()).getId());
                    appointment.setDoctorID(DoctorAppointments.getDoctorByName(doctor.getValue()).getId());
                    appointment.setDate(date.getValue().toString());
                    appointment.setStatus(appontmentStatus);
                                      
                    if(MainUI.connector.updateAppointment(appointment)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successful Update");
                        alert.setHeaderText(null);
                        alert.setContentText("Appointment has been scheduled successfully.");
                        alert.show();
                      
                        updateDoctorAppointment();
                        close();
                      
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Update Failed");
                        alert.setHeaderText(null);
                        alert.setContentText("Exception encountered while trying to update appointment");
                        alert.show();
                    }
                  
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please ensure that patient name and appointment date fields are not empty.");
                    alert.show();
               }
               
            }else{ //-- New patient --
                if( patientName.getValue() != null && !patientName.getValue().trim().equalsIgnoreCase("") &&
                    date.getValue()!= null && !date.getValue().equals("")){
                                     
                    Appointment app = new Appointment("",
                            MainUI.connector.getPatientByName(patientName.getValue().trim()).getId(),
                            DoctorAppointments.getDoctorByName(doctor.getValue()).getId(),
                            description.getText().trim(), date.getValue().toString(),
                            appontmentStatus);
                  
                  
                    if(MainUI.connector.updateAppointment(app)){
                        
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successful Update");
                        alert.setHeaderText(null);
                        alert.setContentText("New patient profile has been added successfully.");
                        alert.show();
                        
                        updateDoctorAppointment();
                        close();
                        
                        
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Update Failed");
                        alert.setHeaderText(null);
                        alert.setContentText("Exception encountered while trying to create new patient profile");
                        alert.show();
                    }
                  
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please ensure that patient name and appointment date fields are not empty.");
                    alert.show();
               }
           }
           
        });
        
        Button cancel = new Button("Exit Window");
        cancel.getStyleClass().add("custom-button");
        cancel.setOnAction((ActionEvent event) -> {
            close();
        });
        
        HBox ctrl = new HBox(10);
        ctrl.getStyleClass().add("toolbar");
        ctrl.setAlignment(Pos.CENTER_RIGHT);
        ctrl.setPadding(new Insets(5, 0, 5, 0));
        
        ctrl.getChildren().addAll(cancel, save);
        grid.add(ctrl, 0, 8, 2, 1);
        
        
        //--- 
        if(appointment == null){
            setTitle("Create Appointment");
        }else{
            setTitle("Update Appointment");
            
            Patient pt = MainUI.connector.getPatientByID(appointment.getPatientID());
            patientName.setValue(pt.getFname()+" "+pt.getLname());
            
            Doctor dr = DoctorAppointments.getDoctorByID(appointment.getDoctorID());
            doctor.setValue(dr.getFname()+" "+ dr.getLname());
            
            description.setText(appointment.getDescription());
            date.setValue(DoctorAppointments.getLocalDate(appointment.getDate()));
            
            if(appointment.getStatus().equalsIgnoreCase("0")){tg.selectToggle(closed);}
            if(appointment.getStatus().equalsIgnoreCase("1")){tg.selectToggle(open);}
            if(appointment.getStatus().equalsIgnoreCase("2")){tg.selectToggle(cancelled);}
            
        }
        
        Scene scene = new Scene(root, 420, 250);
        scene.getStylesheets().add(MainUI.class.getResource("res/style.css").toExternalForm());
        
        //-- set stage icon --
        getIcons().add(new Image(LoginStage.class.getResourceAsStream("res/clinic.png")));
        setScene(scene);
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        show();
    }
    
}
