package hospitalappointments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author ofentse
 */
public class Dashboard extends BorderPane{

    
    private PieChart pieChart;
    
    public Dashboard() {

        pieChart = new PieChart(generatePieChartData());
        
        HBox con = new HBox(pieChart);
        con.setAlignment(Pos.CENTER);
        con.setPadding(new Insets(20));
        
        setCenter(con);
    }
    
    
    public static ObservableList<PieChart.Data> generatePieChartData() {
        
        ObservableList<PieChart.Data> data =  FXCollections.observableArrayList();
        
        for(Doctor dr: DoctorAppointments.doctors){
            int size = MainUI.connector.getAppointmentsFor(dr.getId()).size();
            data.add(new PieChart.Data(dr.getFname()+" "+dr.getLname()+"-"+size, size));
        }
        
        return data;
    }
   
}
