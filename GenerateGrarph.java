import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class GenerateGrarph {
	@SuppressWarnings("unchecked")
	public static BarChart<Number, String> display(PlayerInfo p) {
		int whichGreater = 0;
		//int whichless = 0 
		int gap = 0;
		int max = 0;
		
		if(p.getlost() > p.getWin()) {
			whichGreater = p.getlost();
		} else {
			whichGreater = p.getWin();
		}
		
		if(whichGreater < 50) {
				max = 10 - (whichGreater%10) ;
		} else {
			max = (whichGreater / 10)+(10-(whichGreater % 10));
		}
		if(whichGreater < 10) {
			gap = 1;
		} else {
			gap = ((whichGreater + max)/10);
		}
		
		final NumberAxis xAxis = new NumberAxis(0,whichGreater+max,gap);
        final CategoryAxis yAxis = new CategoryAxis();
        final BarChart<Number,String> barChart = 
            new BarChart<Number,String>(xAxis,yAxis);
	        barChart.setTitle("Game Summary");
	     
	        xAxis.setLabel("Number Games");
	        xAxis.setAutoRanging(false);
	        xAxis.setMinorTickVisible(false);
	       // xAxis.setForceZeroInRange(false);
        XYChart.Series<Number,String > series1 = new XYChart.Series<>(); 
        series1.setName("Games Won"); 
        series1.getData().add(new XYChart.Data<>( p.getWin(),"Game Result"));    

        XYChart.Series<Number,String > series2 = new XYChart.Series<>(); 
        
        series2.setName("Games Lost"); 
        series2.getData().add(new XYChart.Data<>(p.getlost(),"Game Result")); 
        
       
       
        barChart.getData().addAll(series1,series2);
      
       
	   barChart.getStylesheets().add("GraphStyle.css");
	   barChart.setStyle("-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.4) , 10,0,3,5 ); ");
	   barChart.setPrefSize(500,270);
	  
       return barChart;
	}
	
}
