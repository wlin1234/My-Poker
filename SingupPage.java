import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SingupPage {
	static boolean answer;
	public static void display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Sign Up");
		GridPane grid = new GridPane();
		grid.setLayoutX(60);
		grid.setLayoutY(50);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Sign Up");
		
		
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
		
		Label repw = new Label("Retype Password:");
		repw.setStyle("-fx-font-family: Tahoma; -fx-font-size: 18;-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.3) , 10,0,0,1 ); ");
		grid.add(repw, 0, 3);
		
		PasswordField retypepw = new PasswordField();
		retypepw.setStyle("-fx-font-size: 14;-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.9) , 10,0,3,6 ); ");
		grid.add(retypepw, 1, 3);
		
		Label playername = new Label("In-game name: ");
		playername.setStyle("-fx-font-family: Tahoma; -fx-font-size: 18;-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.3) , 10,0,0,1 ); ");
		grid.add(playername, 0, 4);
		
		TextField player = new TextField();
		player.setStyle("-fx-font-size: 14;-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.9) , 10,0,3,6 ); ");
		grid.add(player, 1, 4);

		
		String styles = "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.9) , 10,0,3,6 ); " + 
				"	 -fx-min-width: 70px;" + 
				"    -fx-min-height: 10px;" +  
				"    -fx-font-size: 14px;" + 
				"    -fx-background-radius: 5;";
		
		Button submit = new Button("Sign Up!");
		HBox hbBtn = new HBox(10);
		submit.setStyle(styles);
		
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		
		hbBtn.getChildren().add(submit);
		grid.add(hbBtn, 1, 5);
		String stylesforexit = "-fx-background-color: rgba(242, 38, 92);-fx-effect: dropshadow( gaussian , rgba(232, 2, 6,0.9) , 10,0,3,6 ); -fx-text-fill: white; " + 
				"	 -fx-min-width: 70px;" + 
				"    -fx-min-height: 10px;" +  
				"    -fx-font-size: 14px;" + 
				"    -fx-background-radius: 5;";
		
		//System.out.println(userTextField.getText());
		//System.out.println(pwBox.getText());
		Connection conn = DBConnection.getDBConnection();
		Label note = new Label();
		note.setStyle("-fx-text-fill: red; -fx-font-size: 15px;");
		note.setLayoutX(50);
		note.setLayoutY(330);
		note.setVisible(false);
		
		submit.setOnAction(event -> {
			
		
			try {
			String pass = pwBox.getText();
			String user = userTextField.getText();
			String query = "Select * from accts where username = \"" + user + "\"";
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(query);
			
			String ingameName = player.getText();
			String query2 = "Select * from accts where playername = \"" + ingameName + "\"";
			Statement statement2 = conn.createStatement();
			ResultSet result2 = statement2.executeQuery(query2);
			
			//if fields are not empty and pws match, **also check username doesnt exist in database
			if((userTextField.getText() == null || containsSpecialChars(userTextField.getText()) || spacein(userTextField.getText())|| userTextField.getText().trim().isEmpty()) 
				||
				(pwBox.getText() == null || containsSpecialChars(pwBox.getText()) || spacein(pwBox.getText()) || pwBox.getText().trim().isEmpty())
				||
				(retypepw.getText() == null || containsSpecialChars(retypepw.getText()) || spacein(retypepw.getText()) || retypepw.getText().trim().isEmpty())
				||
				(player.getText() == null || containsSpecialChars(player.getText()) || player.getText().trim().isEmpty() || player.getText().length() >= 14)) {
				note.setText("Please properly fill in all the text fields, \nalso make sure there's no space in your \nusername and passwords. Also avoid using special characters!");
				note.setVisible(true);
				
			} else {
				if(result.next()) {
					note.setText("Username already exists");
					note.setVisible(true);
				} else if(result2.next()) {
					note.setText("In game name already exists");
					note.setVisible(true);
				} else if(!pwBox.getText().equals(retypepw.getText())) {
					note.setText("Passwords do not match");
					note.setVisible(true);
				} else {
					//update database
					String inQuery = "insert into accts values(\"" + user + "\", \"" + pass + "\", \"" + ingameName + "\", 0,0,0,0,0,0,0,0,0,0,0);";
					Statement processstatement = conn.createStatement();
					processstatement.executeUpdate(inQuery);
					JOptionPane.showMessageDialog( null, "Sign up successfully!");
					window.close();
				}
				
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
		root.getChildren().addAll(grid,note);
		root.setBackground(new Background(background));
		Scene scene= new Scene(root, 480,400);
		scene.getStylesheets().add("Style.css");
		window.getIcons().add(new Image("/images/1sn.jpg"));
		window.setScene(scene);
		window.showAndWait();
		
	}
	public static boolean spacein(String s) {
		for (char c : s.toCharArray()) {
		    if (Character.isWhitespace(c)) {
		    	System.out.println(s);
		       return true;
		    }
		}
		return false;
	}
	public static boolean containsSpecialChars(String s) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
		 
	      
	      Matcher matcher = pattern.matcher(s);
	 
	      if (!matcher.matches()) {
	           return true;
	      } else {
	           return false;
	      }
	}
}
