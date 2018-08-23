package hospitalappointments;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import static hospitalappointments.DoctorAppointments.getDoctorNames;
import static hospitalappointments.DoctorAppointments.updateDoctorAppointment;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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
        grid.setStyle("-fx-padding: 20");
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(20);
        grid.setHgap(5);
        
        JFXComboBox<String> patientName = new JFXComboBox(MainUI.connector.getPatientNames());
        patientName.setPromptText("Patient Name");
        patientName.setLabelFloat(true);
        patientName.setPrefWidth(400);
        grid.add(patientName, 0, 0);
        
        JFXComboBox<String> doctor = new JFXComboBox<>(getDoctorNames());
        doctor.setValue(doctor.getItems().get(0));
        doctor.setLabelFloat(true);
        doctor.setPromptText("Doctors");
        doctor.setPrefWidth(400);
        grid.add(doctor, 0, 1);
        
        JFXDatePicker date = new JFXDatePicker();
        date.setPromptText("Appointment date");
        date.setPrefWidth(380);
        grid.add(date, 0, 2);
        
        JFXTextArea description = new JFXTextArea();
        description.setPrefRowCount(4);
        description.setPrefWidth(400);
        description.setLabelFloat(true);
        description.setPromptText("Appointment Description");
        grid.add(description, 0, 3);
        
        root.setCenter(grid);
        
        HBox status = new HBox(10);
        
        status.setPadding(new Insets(5, 0, 5, 0));
        JFXRadioButton cancelled = new JFXRadioButton("Cancelled");
        RadioButton closed = new JFXRadioButton("Closed");
        RadioButton open = new JFXRadioButton("Open");
        
        grid.add(status, 0, 4);
        
        ToggleGroup tg = new ToggleGroup();
        cancelled.setToggleGroup(tg);
        closed.setToggleGroup(tg);
        open.setToggleGroup(tg);
        
        tg.selectToggle(open);
        
        status.getChildren().addAll(cancelled, closed, open);
        status.setAlignment(Pos.CENTER_LEFT);
        
        Button save = new JFXButton("Save Changes");
        save.getStyleClass().addAll("btn", "btn-primary", "btn-sm");
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
        
        Button cancel = new JFXButton("Close");
        cancel.getStyleClass().addAll("btn", "btn-danger", "btn-sm");
        cancel.setOnAction((ActionEvent event) -> {
            close();
        });
        
        HBox ctrl = new HBox(10);
        ctrl.getStyleClass().add("tool-bar");
        ctrl.setAlignment(Pos.CENTER_RIGHT);
        ctrl.setPadding(new Insets(5, 0, 5, 0));
        
        ctrl.getChildren().addAll(cancel, save);
        grid.add(ctrl, 0, 5, 2, 1);
        
        
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
        
        Scene scene = new Scene(root, 450, 320);
        scene.getStylesheets().add(MainUI.class.getResource("res/style.css").toExternalForm());
        
        //-- set stage icon --
        getIcons().add(new Image(LoginStage.class.getResourceAsStream("res/clinic.png")));
        setScene(scene);
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        show();
    }
    
}
