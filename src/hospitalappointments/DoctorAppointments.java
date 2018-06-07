package hospitalappointments;

import java.time.LocalDate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 *
 * @author ofentse
 */
public class DoctorAppointments extends BorderPane{
    
    public static ObservableList<Doctor> doctors = FXCollections.observableArrayList(
            new Doctor("20020", "Steven", "Crap", "72177941", "3607642", "stevecrap@gmail.com", "Male", "Haematology"),
            new Doctor("20021", "Modise", "Kemelo", "72353533", "3607640", "kemelomd@gmail.com", "Male", "Cardeology"),
            new Doctor("20022", "Tefo", "Bontsokwane", "72453433", "3607641", "tbontsokwane@gmail.com", "Male", "Gastroenterology"),
            new Doctor("20023", "Michael", "Gray", "72353533", "3607644", "micgray@gmail.com", "Male", "Microbiology"),
            new Doctor("20024", "Bernard", "Blue", "72353533", "3607643", "bernardblue@gmail.com", "Female", "Nephrology"),
            new Doctor("20025", "Stan", "Stenly", "72333433", "3607645", "stanstenly@gmail.com", "Female", "Neurology")        
    );
    private static FlowPane flowPane;
    private static Doctor selectedDoctor = null;

    public DoctorAppointments() {
        //-- Set the padding of the borderpane --
        setPadding(new Insets(10));
        
        Label doctorName = new Label("");
        doctorName.getStyleClass().add("title");
        
        //-- Create a listview of doctors --
        ListView<String> listView = new ListView<String>(getDoctorNames());
        listView.getStyleClass().add("mainList");
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                doctorName.setText("Dr "+newValue);
                
                selectedDoctor = getDoctorByName(newValue);
                
                updateDoctorAppointment();
            }
        });
        
        setLeft(listView);
                
        //-- Create toolbar --
        HBox toolbar = new HBox();
        toolbar.setSpacing(5);
        toolbar.setAlignment(Pos.CENTER_RIGHT);
        toolbar.getStyleClass().add("toolbar");
        
        Button addAppointment = new Button("Add Appointment");
        ImageView add = new ImageView(new Image(LoginStage.class.getResourceAsStream("res/add.png")));
        addAppointment.setGraphic(add);
        addAppointment.getStyleClass().add("custom-button");
        
        //-- Add button event handler --
        addAppointment.setOnAction((ActionEvent event) -> {
            new UpdateAppointment(null);
        });
        
        Button refresh = new Button("Refresh");
        ImageView ref = new ImageView(new Image(LoginStage.class.getResourceAsStream("res/refresh.png")));
        refresh.setGraphic(ref);
        refresh.getStyleClass().add("custom-button");
        refresh.setOnAction((ActionEvent event) -> {
            updateDoctorAppointment();
        });
        
        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);
                
        toolbar.getChildren().addAll(doctorName, space, refresh, addAppointment);
        
        
        BorderPane center = new BorderPane();
        center.setStyle("-fx-padding: 0 5 0 5");
        
        center.setTop(toolbar);
        
        //----------------------------------------------------------------------
        flowPane = new FlowPane(Orientation.HORIZONTAL);
        flowPane.setVgap(20);
        flowPane.setHgap(20);
        flowPane.setPadding(new Insets(10));
        
        center.setCenter(flowPane);
        
        setCenter(center);
        ////////////////////////////////////////////////////////////////////////
        
        
        
    }
    
    
    public static void updateDoctorAppointment(){
        
        if(selectedDoctor != null){
            ObservableList<Appointment> app = MainUI.connector.getAppointmentsFor(selectedDoctor.getId());
            flowPane.getChildren().clear();

            for(Appointment appointment: app){

                flowPane.getChildren().add(new AppointmentButton(appointment));
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
