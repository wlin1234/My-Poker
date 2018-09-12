import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class generatePie {
	@SuppressWarnings("unchecked")
	public static PieChart display(PlayerInfo p) {
		
		float rest = (float) (100.0-(((Math.round((p.getwinfirst()/(float)p.getWin())*10000.0))/100.0)+((Math.round(((float)p.getwinsecond()/p.getWin())*10000.0))/100.0) +((Math.round(((float)p.getwinmixed()/p.getWin())*10000.0))/100.0)));
		
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Win with [Ace-5]  "+ (Math.round(((float)p.getwinfirst()/p.getWin())*10000.0))/100.0 +"%" , (Math.round(((float)p.getwinfirst()/p.getWin())*10000.0))/100.0),
                new PieChart.Data("Win with [6-9]  "+ (Math.round(((float)p.getwinsecond()/p.getWin())*10000.0))/100.0 +"%" , (Math.round(((float)p.getwinsecond()/p.getWin())*10000.0))/100.0),
                new PieChart.Data("Win with Mix Ups "+ (Math.round(((float)p.getwinmixed()/p.getWin())*10000.0))/100.0 +"%" , (Math.round(((float)p.getwinmixed()/p.getWin())*10000.0))/100.0),
                new PieChart.Data("Win with [10-K]  "+ rest +"%" , rest));
                
		final PieChart chart = new PieChart(pieChartData);
		chart.setStyle("-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.4) , 10,0,3,5 ); ");
        chart.setLabelLineLength(10);
        chart.setTitle("Frequency among Different Types of Wins and Loses");
        chart.getStylesheets().add("GraphStyle.css");
        return chart;
	}
}
