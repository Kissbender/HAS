package hospitalappointments;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author ofentse
 */
public class UpdatePatient extends Stage{

    public UpdatePatient(Patient patient) {
        
        BorderPane root = new BorderPane();
        
        VBox left = new VBox();
        left.setStyle("-fx-border-color:#cfd8dc; -fx-border-width:0 1 0 0;-fx-padding: 0 10 10 0");
        left.setAlignment(Pos.TOP_CENTER);
        ImageView ic = new ImageView(new Image(LoginStage.class.getResourceAsStream("res/user.png")));
        
        left.getChildren().add(ic);
        root.setLeft(left);
        
        GridPane grid = new GridPane();
        grid.setStyle("-fx-padding: 20");
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(20);
        grid.setHgap(5);
        
        JFXTextField fname = new JFXTextField();
        fname.setPrefWidth(450);
        fname.setLabelFloat(true);
        fname.setPromptText("First Name");
        grid.add(fname, 0, 0);
        
        JFXTextField lname = new JFXTextField();
        lname.setPrefWidth(450);
        lname.setLabelFloat(true);
        lname.setPromptText("Last Name");
        grid.add(lname, 0, 1);
        
        JFXTextField email = new JFXTextField();;
        email.setPrefWidth(450);
        email.setLabelFloat(true);
        email.setPromptText("Email Address");
        grid.add(email, 0, 2);
        
        JFXTextField cell = new JFXTextField();
        cell.setPrefWidth(450);
        cell.setLabelFloat(true);
        cell.setPromptText("Cell Phone");
        grid.add(cell, 0, 3);
        
        JFXTextField occupation = new JFXTextField();
        occupation.setPrefWidth(450);
        occupation.setLabelFloat(true);
        occupation.setPromptText("Occupation");
        grid.add(occupation, 0, 4);
        
        HBox gendr = new HBox(10);
        gendr.setPadding(new Insets(3, 0, 3, 0));
        JFXRadioButton male = new JFXRadioButton("Male");
        JFXRadioButton female = new JFXRadioButton("Female");
        
        ToggleGroup tg = new ToggleGroup();
        male.setToggleGroup(tg);
        female.setToggleGroup(tg);
        tg.selectToggle(male);
        
        gendr.getChildren().addAll(male, female);
        gendr.setAlignment(Pos.CENTER_LEFT);
        
        grid.add(gendr, 0, 5);
        
        JFXTextArea postal = new JFXTextArea();
        postal.setPrefRowCount(4);
        postal.setPrefWidth(450);
        postal.setLabelFloat(true);
        postal.setPromptText("Postal Address");
        grid.add(postal, 0, 6);
        
        
        JFXTextArea physical = new JFXTextArea();
        physical.setPrefRowCount(4);
        physical.setPrefWidth(450);
        physical.setLabelFloat(true);
        physical.setPromptText("Phyical Address");
        grid.add(physical, 0, 7);
        
        root.setCenter(grid);
        
        Button save = new JFXButton("Save Changes");
        save.getStyleClass().addAll("btn", "btn-success", "btn-sm");
        save.setOnAction((ActionEvent event) -> {
            
            if(patient != null){ //-- Update --
               
                //-- form validation --
                if(!fname.getText().trim().equalsIgnoreCase("") &&
                    !lname.getText().trim().equalsIgnoreCase("") &&
                    !cell.getText().trim().equalsIgnoreCase("")){
               
                    patient.setCell(cell.getText().trim());
                    patient.setEmail(email.getText().trim());
                    patient.setFname(fname.getText().trim());
                    patient.setLname(lname.getText().trim());
                    patient.setOccupation(occupation.getText().trim());
                    patient.setPhysicalAddress(physical.getText().trim());
                    patient.setPostalAddress(postal.getText().trim());
                  
                    String gndr = (male.isSelected())?"male":"female";
                    patient.setGender(gndr);
                  
                    if(MainUI.connector.updatePatient(patient)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successful Update");
                        alert.setHeaderText(null);
                        alert.setContentText("Patient profile has been updated successfully.");
                        alert.show();
                      
                        close();
                        PatientsProfile.ps.restart();
                      
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Update Failed");
                        alert.setHeaderText(null);
                        alert.setContentText("Exception encountered while trying to update patient profile");
                        alert.show();
                    }
                  
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please ensure that First Name, Last Name and Cell Phone fields are not empty.");
                    alert.show();
               }
               
            }else{ //-- New patient --
                if(!fname.getText().trim().equalsIgnoreCase("") &&
                  !lname.getText().trim().equalsIgnoreCase("") &&
                  !cell.getText().trim().equalsIgnoreCase("")){
                  
                    String gndr = (male.isSelected())?"male":"female";
                   
                    Patient pat = new Patient("", fname.getText().trim(), lname.getText().trim(),
                          cell.getText().trim(), email.getText().trim(), 
                          gndr, physical.getText().trim(), postal.getText().trim(),
                          occupation.getText().trim());
                  
                  
                    if(MainUI.connector.updatePatient(pat)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successful Update");
                        alert.setHeaderText(null);
                        alert.setContentText("New patient profile has been added successfully.");
                        alert.show();
                        
                        close();
                        PatientsProfile.ps.restart();
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
                    alert.setContentText("Please ensure that First Name, Last Name and Cell Phone fields are not empty.");
                    alert.show();
               }
           }
           
        });
        
        Button cancel = new JFXButton("Close");
        cancel.getStyleClass().addAll("btn", "btn-primary", "btn-sm");
        cancel.setOnAction((ActionEvent event) -> {
            close();
        });
        
        HBox ctrl = new HBox(10);
        ctrl.getStyleClass().add("tool-bar");
        ctrl.setAlignment(Pos.CENTER_RIGHT);
        ctrl.setPadding(new Insets(5, 0, 5, 0));
        
        ctrl.getChildren().addAll(cancel, save);
        grid.add(ctrl, 0, 8, 2, 1);
        
        
        //--- 
        if(patient == null){
            setTitle("Add New Patient");
        }else{
            setTitle("Update Patient");
            fname.setText(patient.getFname());
            lname.setText(patient.getLname());
            cell.setText(patient.getCell());
            email.setText(patient.getEmail());
            postal.setText(patient.getPostalAddress());
            physical.setText(patient.getPhysicalAddress());
            occupation.setText(patient.getOccupation());
            
            if(patient.getGender().equalsIgnoreCase("male")){ male.setSelected(true);}
            else{ female.setSelected(true);}
        }
        
        Scene scene = new Scene(root, 550, 500);
        scene.getStylesheets().add(MainUI.class.getResource("res/style.css").toExternalForm());
        
        //-- set stage icon --
        getIcons().add(new Image(LoginStage.class.getResourceAsStream("res/clinic.png")));
        setScene(scene);
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        show();
    }
    
}
