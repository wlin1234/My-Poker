import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StatRecord {
	@SuppressWarnings("unchecked")
	public static void display(PlayerInfo p) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("STAT");
		float numGames = p.getlost()+p.getWin();
		
		double winProb =  (Math.round((p.getWin()/numGames)*10000.0))/100.0;
		double lostProb = 100.0-winProb;
		//////////////////////////////////////////////////////////////////////////////////
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Winning game "+winProb +"%" , winProb),
                new PieChart.Data("Losing game " + lostProb +"%" , lostProb));
                
		final PieChart chart = new PieChart(pieChartData);
		chart.setStyle("-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.4) , 10,0,3,5 ); ");
        chart.setLabelLineLength(10);
        chart.setTitle("Win Rate");
        //////////////////////////////////////////////////////////////////////////////////
        
        //////////////////////////////////////////////////////////////////////////////////
        CategoryAxis xAxis = new CategoryAxis();
        int getlosemix =  p.getlost()-(p.getlosefirst()+p.getlosesecond()+p.getlosethird());
        int large = p.getLargest();
        if(large < getlosemix) {
        	large = getlosemix;
        }
        
        
        int gap = 0;
		int max = 0;
		
		
		
		if(large < 50) {
			max = 10 - (large%10) ;
		} else {
			max = (large / 10)+(10-(large % 10));
		}
		
		if(large < 10) {
			gap = 1;
		} else {
			gap = ((large + max)/10);
		}
        
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(
           "Chosen mostly [Ace-5]", "Chosen mostly [6-9]", "Chosen mostly [10-K]", "Mix Ups"))); 
        xAxis.setLabel("category");  
        
        //Defining the y axis 
        NumberAxis yAxis = new NumberAxis(0,large+max, gap); 
        yAxis.setLabel("Number Games");
        yAxis.setAutoRanging(false);
        yAxis.setMinorTickVisible(false);
       // yAxis.setForceZeroInRange(false);
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);  
        barChart.setTitle("Frequency among Different Types of Wins and Loses\n\n\n\n"); 
        Label l = new Label("(Each type is determined by the number of cards within the rank you chose:\nthe number of cards in that rank is more than half of the total 5 cards)");
   
        l.setLayoutX(565);
        l.setLayoutY(70);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>(); 
        series1.setName("Games Won"); 
        //series1.setName("Win with mostly rank 1-5"); 
        series1.getData().add(new XYChart.Data<>("Chosen mostly [Ace-5]", p.getwinfirst()));   
        series1.getData().add(new XYChart.Data<>("Chosen mostly [6-9]", p.getwinsecond()));
        series1.getData().add(new XYChart.Data<>("Chosen mostly [10-K]", p.getwinthird()));
        series1.getData().add(new XYChart.Data<>("Mix Ups", p.getwinmixed()));
        
        XYChart.Series<String, Number> series2 = new XYChart.Series<>(); 
        series2.setName("Games Lost"); 
        
        //series2.setName("Win with mostly rank 6-9"); 
        series2.getData().add(new XYChart.Data<>("Chosen mostly [Ace-5]", p.getlosefirst()));  
        series2.getData().add(new XYChart.Data<>("Chosen mostly [6-9]", p.getlosesecond()));
        series2.getData().add(new XYChart.Data<>("Chosen mostly [10-K]", p.getlosethird()));
        series2.getData().add(new XYChart.Data<>("Mix Ups",getlosemix));
        l.setStyle("-fx-font-size: 11; -fx-text-fill:rgb(34, 67, 119);-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.4) , 10,0,3,5 ); ");
        barChart.setStyle("-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.4) , 10,0,3,5 ); ");
        barChart.getData().addAll(series1, series2);
        barChart.setLayoutX(480);
        barChart.setLayoutY(0);
       
        
        //////////////////////////////////////////////////////////////////////////////////
        PieChart secondPie = generatePie.display(p);
        secondPie.setLayoutX(480);
        secondPie.setLayoutY(500);
        BarChart<Number,String> chart2 = GenerateGrarph.display(p);
        
        chart2.setLayoutX(0);
        chart2.setLayoutY(600);
		Pane root = new Pane();
		BackgroundImage background = new BackgroundImage(new Image("/images/Wood_background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
		root.getChildren().addAll(chart,barChart,l,chart2,secondPie);
		root.setBackground(new Background(background));
		Scene scene= new Scene(root, 1000,1000);
		
		scene.getStylesheets().add("Style.css");
		window.getIcons().add(new Image("/images/1sn.jpg"));
		window.setScene(scene);
		window.showAndWait();
		
	}
	//public static  winrate
}
