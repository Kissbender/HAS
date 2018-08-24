package hospitalappointments;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;

/**
 *
 * @author ofentse
 */
public class PatientsProfile extends BorderPane{
    public static PatientsService ps;
    TableView<Patient> patientTableView;
    

    public PatientsProfile() {
        //-- Set the padding of the borderpane --
        setPadding(new Insets(10));
        
        ps = new PatientsService();
        
        //-- Create toolbar --
        HBox toolbar = new HBox();
        toolbar.setSpacing(5);
        toolbar.setAlignment(Pos.CENTER_RIGHT);
        toolbar.getStyleClass().addAll("panel-success", "panel-body");
        setTop(toolbar);
        
        JFXButton addPatient = new JFXButton("Add Patient");
        ImageView add = new ImageView(new Image(LoginStage.class.getResourceAsStream("res/add.png")));
        addPatient.setGraphic(add);
        addPatient.getStyleClass().addAll("btn", "btn-primary", "btn-xs");
        
        //-- Add button event handler --
        addPatient.setOnAction((ActionEvent event) -> {
            new UpdatePatient(null);
        });
        
        JFXButton refresh = new JFXButton("Refresh");
        ImageView ref = new ImageView(new Image(LoginStage.class.getResourceAsStream("res/refresh.png")));
        refresh.setGraphic(ref);
        refresh.getStyleClass().addAll("btn", "btn-success", "btn-xs");
        refresh.setOnAction((ActionEvent event) -> {
            ps.restart();
        });
        
        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);
                
        toolbar.getChildren().addAll(refresh, addPatient, space);
        
        
        //------ Patient table -------------------------------------------------
        patientTableView = new TableView<>();
        
        TableColumn id = new TableColumn("");
        id.setMinWidth(70);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
                
        TableColumn fname = new TableColumn("FIRST NAME");
        fname.setMinWidth(100);
        fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        
        TableColumn sname = new TableColumn("SURNAME");
        sname.setMinWidth(100);
        sname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        
        TableColumn email = new TableColumn("EMAIL");
        email.setMinWidth(200);
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        TableColumn cell = new TableColumn("CELL PHONE");
        cell.setMinWidth(100);
        cell.setCellValueFactory(new PropertyValueFactory<>("cell"));
        
        TableColumn gender = new TableColumn("GENDER");
        gender.setMinWidth(70);
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        
        TableColumn control = new TableColumn("");
        control.setMinWidth(100);
        control.setCellValueFactory(new PropertyValueFactory<>("id"));
        control.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
            @Override 
            public TableCell<String, String> call(TableColumn<String, String> patientID) {
                return new TableCell<String, String>() {
                    
                    @Override 
                    public void updateItem(final String ID, boolean empty) {
                        super.updateItem(ID, empty);
                        if(!empty){
                            Button edit = new JFXButton("Edit");
                            edit.setPrefWidth(30);
                            edit.getStyleClass().addAll("btn", "btn-info", "btn-xs");
                            edit.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    new UpdatePatient(MainUI.connector.getPatientByID(ID));
                                }
                            });
                            
                            setGraphic(edit);
                        }else{ setGraphic(null); }
                        
                    }
                };
            }
        });
        
        patientTableView.getColumns().addAll(id, fname, sname, email, cell, gender, control);
        
        patientTableView.itemsProperty().bind(ps.valueProperty());
        
        setCenter(patientTableView);
        
        ps.start();
        ps.restart();
    }






    /**
     * Run network based tasks in the background to avoid blocking other 
     * UI functionalities
     */

    public class PatientsTask extends Task<ObservableList<Patient>> {       
        @Override 
        protected ObservableList<Patient> call() throws Exception {
            
            return MainUI.connector.getPatients();
        } 
    }

    
    public class PatientsService extends Service<ObservableList<Patient>> {

        @Override
        protected Task createTask() {
            return new PatientsTask();
        }
    }
  
}