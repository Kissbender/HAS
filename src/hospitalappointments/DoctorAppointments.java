package hospitalappointments;

import com.jfoenix.controls.JFXListView;
import java.time.LocalDate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 *
 * @author ofentse
 */
public class DoctorAppointments extends BorderPane{
    
    
    //-- To be populated with data from the database --
    public static ObservableList<Doctor> doctors = FXCollections.observableArrayList(
        new Doctor("20020", "Steven", "Crap", "72177941", "3607642", "stevecrap@gmail.com", "Male", "Haematology"),
        new Doctor("20021", "Modise", "Kemelo", "72353533", "3607640", "kemelomd@gmail.com", "Male", "Cardeology"),
        new Doctor("20022", "Tefo", "Bontsokwane", "72453433", "3607641", "tbontsokwane@gmail.com", "Male", "Gastroenterology"),
        new Doctor("20023", "Michael", "Gray", "72353533", "3607644", "micgray@gmail.com", "Male", "Microbiology"),
        new Doctor("20024", "Bernard", "Blue", "72353533", "3607643", "bernardblue@gmail.com", "Female", "Nephrology"),
        new Doctor("20025", "Stan", "Stenly", "72333433", "3607645", "stanstenly@gmail.com", "Female", "Neurology")        
    );
    
    
    private static JFXListView<VBox> appointments;
    private static Doctor selectedDoctor = null;

    public DoctorAppointments(){
        
        //-- Set the padding of the borderpane --
        setPadding(new Insets(10));
        
        //-- Create a listview of doctors --
        JFXListView<String> listView = new JFXListView<String>();
        listView.setItems(getDoctorNames());
        listView.getStyleClass().add("jfx-custom-list");
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedDoctor = getDoctorByName(newValue);
                updateDoctorAppointment();
            }
        });
        
        VBox.setVgrow(listView, Priority.ALWAYS);        
        
        BorderPane panel = new BorderPane();
        panel.getStyleClass().addAll("panel", "panel-info");
        
        Label heading = new Label("Doctors");
        heading.getStyleClass().add("panel-title");
        
        HBox panelHeading = new HBox();
        panelHeading.setAlignment(Pos.CENTER_LEFT);
        panelHeading.getStyleClass().add("panel-heading");
        
        panel.setTop(panelHeading);
        
        VBox panelBody = new VBox(listView);
        panelBody.getStyleClass().add("panel-body");
        
        panel.setCenter(panelBody);
        panelHeading.getChildren().add(heading);
        
        setLeft(panel);
                
        //-- Create toolbar --
        HBox toolbar = new HBox();
        toolbar.setSpacing(5);
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.getStyleClass().addAll("panel-default", "panel-body");
        
        Button addAppointment = new Button("New Appointment");
        ImageView add = new ImageView(new Image(LoginStage.class.getResourceAsStream("res/add.png")));
        addAppointment.setGraphic(add);
        addAppointment.getStyleClass().addAll("btn", "btn-success", "btn-xs");
        
        //-- Add button event handler --
        addAppointment.setOnAction((ActionEvent event) -> {
            new UpdateAppointment(null);
        });
        
        Button refresh = new Button("Refresh");
        ImageView ref = new ImageView(new Image(LoginStage.class.getResourceAsStream("res/refresh.png")));
        refresh.setGraphic(ref);
        refresh.getStyleClass().addAll("btn", "btn-primary", "btn-xs");
        refresh.setOnAction((ActionEvent event) -> {
            updateDoctorAppointment();
        });
        
        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);
                
        toolbar.getChildren().addAll(addAppointment, refresh, space);
        
        BorderPane center = new BorderPane();
        center.setStyle("-fx-padding: 0 5 0 5");
        
        center.setTop(toolbar);
        
        //----------------------------------------------------------------------
        appointments = new JFXListView<>();
        appointments.setExpanded(true);
        appointments.getStyleClass().add("jfx-app-list");
        appointments.setDepth(2);
        appointments.setPadding(new Insets(10));
        
        center.setCenter(appointments);
        
        setCenter(center);
        //----------------------------------------------------------------------
        
    }
    
    
    public static void updateDoctorAppointment(){
        
        if(selectedDoctor != null){
            ObservableList<Appointment> app = MainUI.connector.getAppointmentsFor(selectedDoctor.getId());
            appointments.getItems().clear();

            for(Appointment appointment: app){

                appointments.getItems().add(new AppointmentListItem(appointment));
            }
        }
    }
    
    
    /**
     * 
     * @param dt
     * @return 
     */
    public static LocalDate getLocalDate(String dt){
        try {
            if(dt == null){
                return null; }

            if(!"".equals(dt)){
                String[] date = dt.split("-");
                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int day = Integer.parseInt(date[2]);
                LocalDate localdate = LocalDate.of(year, month, day);

                return localdate;
            }
            return null;
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    
    
    /**
     * 
     * @return 
     */
    public static ObservableList<String> getDoctorNames(){
        
        ObservableList<String> dr_names = FXCollections.observableArrayList();
        
        for(Doctor dr: doctors){
            dr_names.add(dr.getFname()+" "+dr.getLname());
        }
        
        return dr_names;
    }
    
    /**
     * 
     * @return 
     */
    public static Doctor getDoctorByName(String fullname){
        
        
        for(Doctor dr: doctors){
            if((dr.getFname()+" "+dr.getLname()).equalsIgnoreCase(fullname)){
                return dr;
            }
        }
        
        return new Doctor();
    }
    
    /**
     * 
     * @return 
     */
    public static Doctor getDoctorByID(String id){
                
        for(Doctor dr: doctors){
            if(dr.getId().equalsIgnoreCase(id)){
                return dr;
            }
        }
        
        return new Doctor();
    }
}
