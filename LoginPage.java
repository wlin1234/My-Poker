import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginPage {
	static boolean answer;
	static PlayerInfo p;
	public static PlayerInfo display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Login");
		GridPane grid = new GridPane();
		grid.setLayoutX(60);
		grid.setLayoutY(90);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Welcome");
		Connection conn = DBConnection.getDBConnection();
		
		scenetitle.setStyle("-fx-font-family: Tahoma; -fx-font-size: 30;-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.3) , 10,0,0,1 ); ");
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		userName.setStyle("-fx-font-family: Tahoma; -fx-font-size: 18;-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.3) , 10,0,0,1 ); ");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		userTextField.setStyle("-fx-font-size: 14;-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.9) , 10,0,3,6 ); ");
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		pw.setStyle("-fx-font-family: Tahoma; -fx-font-size: 18;-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.3) , 10,0,0,1 ); ");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		pwBox.setStyle("-fx-font-size: 14;-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.9) , 10,0,3,6 ); ");
		grid.add(pwBox, 1, 2);
		Button signup = new Button("Sign up");
		
		
		String styles = "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.9) , 10,0,3,6 ); " + 
				"	 -fx-min-width: 70px;" + 
				"    -fx-min-height: 10px;" +  
				"    -fx-font-size: 14px;" + 
				"    -fx-background-radius: 5;";
		Button signin = new Button("Sign in");
		userTextField.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
	        if (ev.getCode() == KeyCode.ENTER) {
	        		signin.fire();
		           ev.consume(); 
		        }
		    });
		pwBox.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
	        if (ev.getCode() == KeyCode.ENTER) {
	        		signin.fire();
		           ev.consume(); 
		        }
		    });
		HBox hbBtn = new HBox(10);
		signin.setStyle(styles);
		signup.setStyle(styles);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(signup);
		hbBtn.getChildren().add(signin);
		grid.add(hbBtn, 1, 4);
		String stylesforexit = "-fx-background-color: rgba(242, 38, 92);-fx-effect: dropshadow( gaussian , rgba(232, 2, 6,0.9) , 10,0,3,6 ); -fx-text-fill: white; " + 
				"	 -fx-min-width: 70px;" + 
				"    -fx-min-height: 10px;" +  
				"    -fx-font-size: 14px;" + 
				"    -fx-background-radius: 5;";
		
		Button exit = new Button("Exit");
		exit.setStyle(stylesforexit);
		exit.setLayoutX(380);
		exit.setLayoutY(350);
		//System.out.println(userTextField.getText());
		//System.out.println(pwBox.getText());
		Label note = new Label();
		note.setStyle("-fx-text-fill: red; -fx-font-size: 15px;");
		note.setLayoutX(50);
		note.setLayoutY(330);
		note.setVisible(false);
		
		signup.setOnAction(event -> {
			SingupPage.display();
		});
		exit.setOnAction(event -> {
			Platform.exit();
			System.exit(0);
		});
		signin.setOnAction(event -> {
			
			try {
				String user = userTextField.getText();
				String pass = pwBox.getText();
				String query = "select password from accts where username=\"" + user +"\"";
				Statement statement = conn.createStatement();
				ResultSet result = statement.executeQuery(query);
				//if username exists and returns password
				if(result.next()) {
					//check if passwords match
					if(result.getString(1).equals(pass)) {
						
						String allinfo = "Select * from accts where username = \""+user+"\";";
						statement = conn.createStatement();
						ResultSet results = statement.executeQuery(allinfo);
						results.next();
						//init p to all fields and return it;
						p = new PlayerInfo(results.getString(3), 
								Integer.parseInt(results.getString(4)),
								Integer.parseInt(results.getString(5)),
								Integer.parseInt(results.getString(6)),
								Integer.parseInt(results.getString(7)),
								Integer.parseInt(results.getString(8)),
								Integer.parseInt(results.getString(9)),
								Integer.parseInt(results.getString(10)),
								Integer.parseInt(results.getString(11)),
								Integer.parseInt(results.getString(12)),
								Integer.parseInt(results.getString(13)),
								Integer.parseInt(results.getString(14)));
						//answer = true;
						window.close();
					} else {
						note.setText("Invalid login, please try again.");
						note.setVisible(true);
					}
				} else {
					note.setText("Invalid login, please try again.");
					note.setVisible(true);
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		
		Pane root = new Pane();
		BackgroundImage background = new BackgroundImage(new Image("/images/clearwood.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
		root.getChildren().addAll(grid,exit,note);
		root.setBackground(new Background(background));
		Scene scene= new Scene(root, 480,400);
		scene.getStylesheets().add("Style.css");
		window.getIcons().add(new Image("/images/1sn.jpg"));
		window.setScene(scene);
		window.showAndWait();
		
		return p;
	}
}
