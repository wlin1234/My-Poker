import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

public class ConfirmationBox {

	static int answer;
	public static int display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Choose term");
		
		Label label = new Label("Who is starting first (Bot as Default)");
		Label empty = new Label("");
		Button yesButton = new Button("Me!");
		Button noButton = new Button("Bot!");
		yesButton.setOnAction(event -> {
			answer = 0;
			window.close();
		});
		noButton.setOnAction(event -> {
			answer = 1;
			window.close(); 
		});
		VBox layout = new VBox(10);
		HBox layout2 = new HBox(10);
		layout2.setSpacing(200);
		layout2.setMaxWidth(20);
		layout2.getChildren().addAll(yesButton,noButton);
		
		layout.setAlignment(Pos.CENTER);
		String stylelabel = "-fx-font-size: 22px;" + 
				"   -fx-font-family: Arial Black;" + 
				"   -fx-fill: #818181;" + 
				"   -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 );" 
				;
		label.setStyle(stylelabel);
		
		layout.getChildren().addAll(label,empty,layout2);
		layout.setLayoutX(25);
		layout.setLayoutY(150);
		Pane root = new Pane();
		BackgroundImage background = new BackgroundImage(new Image("/images/clearwood.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
		root.getChildren().addAll(layout);
		root.setBackground(new Background(background));
		Scene scene= new Scene(root, 480,400);
		
		scene.getStylesheets().add("Style.css");
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}
}
