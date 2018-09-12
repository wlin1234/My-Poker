import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene; 
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
public class PokerGame extends Application{
	static HashMap<String, ImageView> pics = new HashMap<>(); 
	//re
	static HashMap<Integer, HBox> putOrders = new HashMap<>(); 
	//re
	static HashMap<Integer, Cards> chosenImage = new HashMap<>(); 
	//re
	static ArrayList<Cards> chosen = new ArrayList<>();
	//re
	static int Round = 1;
	static int ch = 0;
	static int scenes = 1;
	static TextArea chat = new TextArea();
	//re
	static boolean userPicked = false;
	//re
	static boolean botPicked = false;
	//re
	static Cards userCard;
	//re
	static Cards botCard;
	//re
	static String currUser;
	//re
	static int pickedCard = -1;
	//re
	static int terms = 1;
	//re
	static boolean printed = false;
	static int playerScore = 0;
	static int[] cats = new int[3];
	static int[] losecats = new int[3];
	static PlayerInfo playeracct;
	PauseTransition pause = new PauseTransition(Duration.millis(1500));
	
	public static void main(String[] args)  {/**
		Poker table = new Poker();
		try {
			table.pick("spade", 1);
			table.pick("spade", 1);
		} catch (AlreadyOutException e) {
			
			e.printStackTrace();
		}
		System.out.println(table.availableCards());
		System.out.println(table.getProbability());
		*/
		launch(args);
		DBConnection.closeConnection();
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		playeracct = LoginPage.display();
		
		while(playeracct==null) {
			playeracct = LoginPage.display();
		}
		
		playerScore = playeracct.getscore();
		Label pname = new Label(playeracct.getName());
		pname.setStyle("-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.9) , 10,0,4,7 );-fx-text-fill:rgb(206, 204, 196);");
		Button rule = new Button("Game Rule");
		rule.setStyle("-fx-background-color: rgba(89, 130, 94,0.6);"
				+ "-fx-text-fill: white;");
		rule.setLayoutX(30);
		rule.setLayoutY(500);
		
		Button Stats = new Button("Stats");
		Stats.setStyle("-fx-background-color: rgba(89, 130, 94,0.4);"
				+ "-fx-text-fill: white;");
		Stats.setLayoutX(170);
		Stats.setLayoutY(500);
		
		Stats.setOnAction(event -> {
			StatRecord.display(playeracct);
		});
		Label scores = new Label("Score: " + playerScore);
		scores.setStyle("-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.9) , 10,0,4,7 ); ");
		 Button NextGame = new Button("Next game");
		 NextGame.setStyle("-fx-background-color: rgba(113, 139, 140,0.5);-fx-text-fill:white;");
		 NextGame.setLayoutX(30);
		 NextGame.setLayoutY(420);
		 NextGame.setDisable(true);
		 
		 TextArea GameRule = new TextArea();
		 GameRule.setLayoutX(30);
		 GameRule.setLayoutY(550);
		 GameRule.setPrefSize(370, 420);
		 GameRule.autosize();
		 GameRule.setWrapText(true);
		 GameRule.setEditable(false);
		 GameRule.setStyle("-fx-background-color: rgba(89, 130, 94,0.4);-fx-text-fill: rgb(201, 230, 239);  -fx-line-spacing: 10; -fx-font-style: italic; ;  -fx-font-size: 16;-fx-font-family: gaussian;");
		// GameRule.setStyle("-fx-text-fill: white;");
		 GameRule.setVisible(false);
		 GameRule.appendText("1. Choose 5 unreapted cards and click \"start\" begin the game.\n");
		 GameRule.appendText("2. The chosen cards will then be shuffled and flipped.\n");
		 GameRule.appendText("3. A pop up will prompt who should be picking first. (The default selection is BOT)\n");
		 GameRule.appendText("4. Choose your card!\n===============================");
		 GameRule.appendText("The game result will then be determined based on the chosen cards' suit and rank as follows\n");
		 GameRule.appendText("\n \tSpade: \t\t4 * Card Rank\n \tHeart: \t\t3 * Card Rank\n \tDiamond: \t2 * Card Rank\n \tClub: \t\t1 * Card Rank\n===============================");
		 
		 rule.setOnAction(event -> {
			if(!GameRule.isVisible()) {
				GameRule.setVisible(true);
			} else {
				GameRule.setVisible(false);
			}
		 });
		int num_text = 5;
		ArrayList<VBox> cardimage = new ArrayList<>();
		// chatboxes
		Button send = new Button("Send");
		send.getStyleClass().add("my-special-button");
		
		
		chat.setPrefSize(535, 350);
		chat.autosize();
		chat.setWrapText(true);
		chat.setEditable(false);;
		chat.setStyle("-fx-text-fill: white;");
		
		chat.textProperty().addListener(new ChangeListener<Object>() {
		    @Override
		    public void changed(ObservableValue<?> observable, Object oldValue,
		            Object newValue) {
		        chat.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
		        //use Double.MIN_VALUE to scroll to the top
		    }
		});
		
		SystemMessage("Welcome "+playeracct.getName()+"! Please pick 5 cards from the choice boxes above, you then will pick one card to compete against our BOT.");
		
		//userline indicates user input
		TextArea userline = new TextArea();
		userline.setPrefSize(420, 20);
		userline.setPromptText("Add your own annotation!");
		userline.autosize();
		userline.setStyle("-fx-text-fill:#4deabb;");
		userline.setWrapText(true);
		userline.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
	        if (ev.getCode() == KeyCode.ENTER) {
		           send.fire();
		           ev.consume(); 
		        }
		    });
		//the button for send that uploads into the chatbox
		send.setOnAction(event -> {
			String User = playeracct.getName()+": "; 
			String readytoAppend = userline.getText().replace("\n", "");
			readytoAppend = readytoAppend.trim();
			User = User + readytoAppend;
			if(!readytoAppend.isEmpty()) { 
				chat.appendText(User+"\n");	
			}
			userline.clear();
			});
		send.setStyle("-fx-background-color: rgba(53,89,119,0.3);"
				+"-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.4) ,0,0,0,0 ); "
				+ "-fx-text-fill: white;");
		
		///choicebox for ranks
		ArrayList<ChoiceBox<String>> cardNum = new ArrayList<>();
		for(int i = 0; i < num_text; i++) {
			cardNum.add(new ChoiceBox<String>());
			Tooltip t = new Tooltip("Enter Rank");
			//cardNum.get(i).set
			
			cardNum.get(i).setTooltip(t);
			cardNum.get(i).getItems().addAll("1","2","3","4","5","6","7","8","9","10","J","Q","K");
		}
		chat.appendText("");
		
		
		 ArrayList<ChoiceBox<String>> cb = new ArrayList<>();
		 for(int i = 0; i < 5; i++) {
			 cb.add(new ChoiceBox<>());
			 Tooltip t = new Tooltip("Enter suit type");
			 
			 cb.get(i).setTooltip(t);
			 cb.get(i).getItems().addAll("Spade","Heart","Diamond","Club");
			 VBox v = new VBox(0);
			 v.setPadding(new Insets(10));
			 v.setSpacing(20);
			 v.getChildren().add(cb.get(i));
			 cardimage.add(v);
		 }
		 //import all images into a hashtable
		String [] s = new String[] {"c","d","h","s"};
		
			for(int j = 0; j < s.length; j++) {
				for(int i = 1; i <= 13; i++) {
					Image img = new Image("/images/"+Integer.toString(i)+s[j]+".png");
					ImageView imageView = new ImageView(img);
					imageView.setFitHeight(120);
					imageView.setFitWidth(90);
					
					pics.put(Integer.toString(i)+s[j], imageView);
				}
			}
		//poker image 55x30
			Image backimg = new Image("/images/back.png");
			
		//horizontal box for card numbers choiceboxes	
		 HBox cardInLine1= new HBox(5);
		 cardInLine1.setPadding(new Insets(10));
		 cardInLine1.getChildren().addAll(cardNum);
		 cardInLine1.setSpacing(25);
		 
		 HBox cardInLine= new HBox(5);
		 cardInLine.setPadding(new Insets(10));
		 cardInLine.getChildren().addAll(cardInLine1);
		 cardInLine.setSpacing(20);
		 
		//horizontal box for card suits choiceboxes
		 HBox cardSuit= new HBox(5);
		 cardSuit.setPadding(new Insets(10));
		 cardSuit.getChildren().addAll(cardimage);
		
		 
		 //vertical boxes to store all hboxes
		 VBox cardChoices = new VBox(15);
		 cardChoices.setPadding(new Insets(5));
		 
		 //horizontal box to store all images.

		 //when first is chosen
		 cb.get(0).setOnAction(event -> {
			 if(!cb.get(0).getSelectionModel().isEmpty()) {
				 CallAction(cardimage.get(0),0,cb,cardNum,1);
				 }
			
		 });
		 cb.get(1).setOnAction(event -> {
			 if(!cb.get(1).getSelectionModel().isEmpty()) {
				 CallAction(cardimage.get(1),1,cb,cardNum,1);
				 }
			
		 });
		 cb.get(2).setOnAction(event -> {
			 if(!cb.get(2).getSelectionModel().isEmpty()) {
				 CallAction(cardimage.get(2),2,cb,cardNum,1);
				 }
			
		 });
		 cb.get(3).setOnAction(event -> {
			 if(!cb.get(3).getSelectionModel().isEmpty()) {
				 CallAction(cardimage.get(3),3,cb,cardNum,1);
				 }
			
		 });
		 cb.get(4).setOnAction(event -> {
			 if(!cb.get(4).getSelectionModel().isEmpty()) {
				 CallAction(cardimage.get(4),4,cb,cardNum,1);
				 }
			 
		 });
		 
		 cardNum.get(0).setOnAction(event -> {
			 if(!cardNum.get(0).getSelectionModel().isEmpty()) {
			 CallAction(cardimage.get(0),0,cb,cardNum,2);
			 }
		 });
		 cardNum.get(1).setOnAction(event -> {
			 if(!cardNum.get(1).getSelectionModel().isEmpty()) {
				 CallAction(cardimage.get(1),1,cb,cardNum,2);
				 }
			 
		 });
		 cardNum.get(2).setOnAction(event -> {
			 if(!cardNum.get(2).getSelectionModel().isEmpty()) {
				 CallAction(cardimage.get(2),2,cb,cardNum,2);
				 }
			
		 });
		 cardNum.get(3).setOnAction(event -> {
			 if(!cardNum.get(3).getSelectionModel().isEmpty()) {
				 CallAction(cardimage.get(3),3,cb,cardNum,2);
				 }
			 
		 });
		 cardNum.get(4).setOnAction(event -> {
			 if(!cardNum.get(4).getSelectionModel().isEmpty()) {
				 CallAction(cardimage.get(4),4,cb,cardNum,2);
				 }
			
		 });

		 Button StartApp = new Button("Start");
		 StartApp.setStyle("-fx-background-color: rgba(255,255,255,0.7);"
					+ "-fx-text-fill: black;");
		 ArrayList<Button> picks = new ArrayList<>();
		 for(int i = 0; i < 5; i++) {
			 picks.add(new Button("Pick!"));
		 }
		 
		 StartApp.setOnAction(event -> {
			 /**if(scenes == 1) {
				 cardChoices.getChildren().clear();
				 cardChoices.getChildren().add(test1);
			 }**/
			 
			 if(chosenImage.size() >= 5) { 
				 terms = ConfirmationBox.display();
				 copyList();
				 Stack<HBox> cardback = new Stack<>();
				 for(int i = 0; i < chosenImage.size(); i++) {
					 HBox newcard = new HBox(10);
					 newcard.setSpacing(10);
					 String style_inner = "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.9) , 10,0,4,6 );";
					 newcard.setMaxWidth(20);
					 ImageView back = new ImageView(backimg);
					 back.setFitHeight(120);
					 back.setFitWidth(90);
					 newcard.setStyle(style_inner);
					 newcard.getChildren().add(back);
					 cardback.push(newcard);
				 }
				 for(int i = 0, c = 0; i < 5; i++) {
					 cb.get(i).setDisable(true);
					 cardNum.get(i).setDisable(true);
					 StartApp.setDisable(true);
					if(cardimage.get(i).getChildren().size() > 1) {
						cardimage.get(i).getChildren().remove(1);
						cardimage.get(c).getChildren().add(cardback.pop());
						cardimage.get(c).getChildren().add(picks.get(i));
						c++;
					}
				 }
				 if(terms%2 == 0) {
					 currUser = "You";
					 SystemMessage("Now choose a card to compete!");
					 terms++;
				 } else {
					 currUser = "Bot";
					 invokeBot(picks);
					 terms++;
				 }
			 } else {
				 SystemMessage("Please pick 5 cards to start the game !");
			 }
		 });
		 String style_inner = "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.9) , 10,0,4,6 );";
		 currUser = "You";
		 picks.get(0).setOnAction(event -> {
			 if(ch < chosenImage.size() && (!userPicked || currUser != "You")) {
				 if(currUser == "You") {
					 userCard = chosen.get(0);
					 pickedCard = 0;
					 userPicked = true;
					 SystemMessage(currUser + " picked " + userCard.toString());
				 }
				 cardimage.get(0).getChildren().remove(2);
				 cardimage.get(0).getChildren().remove(1);
				 HBox card = new HBox(10);
				 card.setSpacing(10);
				 card.setStyle(style_inner);
				 card.getChildren().add(chosen.get(0).getImage());
				 cardimage.get(0).getChildren().add(card);
				 ch++;
				 if(!botPicked) {
					botPicked = true;
					invokeBot(picks);
				 }
				 currUser = "You";
			 }
			 if(userPicked && botPicked) {
				 for(int i = 0; i < picks.size(); i++) {
					 picks.get(i).setDisable(true);
				 }
				 judge(scores,userCard,botCard);
				 NextGame.setDisable(false);
			 }
		 });
		 picks.get(1).setOnAction(event -> {
			 if(ch < chosenImage.size()&& (!userPicked || currUser != "You")) {
				 if(currUser == "You") {
					 userCard = chosen.get(1);
					 pickedCard = 1;
					 userPicked = true;
					 SystemMessage(currUser + " picked " + userCard.toString());
				 }
				 cardimage.get(1).getChildren().remove(2);
				 cardimage.get(1).getChildren().remove(1);
				 HBox card = new HBox(10);
				 card.setSpacing(10);
				 card.setStyle(style_inner);
				 card.getChildren().add(chosen.get(1).getImage());
				 cardimage.get(1).getChildren().add(card);
				 ch++;
				 if(!botPicked) {
					botPicked = true;
					invokeBot(picks);
				}
				 currUser = "You";
			 }
			 if(userPicked && botPicked) {
				 for(int i = 0; i < picks.size(); i++) {
					 picks.get(i).setDisable(true);
				 }
				 judge(scores,userCard,botCard);
				 NextGame.setDisable(false);
			 }
		 });
		 picks.get(2).setOnAction(event -> {
			 if(ch < chosenImage.size() && (!userPicked || currUser != "You")) {
				 if(currUser == "You") {
					 userCard = chosen.get(2);
					 pickedCard = 2;
					 userPicked = true;
					 SystemMessage(currUser + " picked " + userCard.toString());
				 }
				 cardimage.get(2).getChildren().remove(2);
				 cardimage.get(2).getChildren().remove(1);
				 HBox card = new HBox(10);
				 card.setSpacing(10);
				 card.setStyle(style_inner);
				 card.getChildren().add(chosen.get(2).getImage());
				 cardimage.get(2).getChildren().add(card); 
				 ch++;
				 if(!botPicked) {
					 botPicked = true;
					 invokeBot(picks);
				 }
				 currUser = "You";
			 }
			 if(userPicked && botPicked) {
				 for(int i = 0; i < picks.size(); i++) {
					 picks.get(i).setDisable(true);
				 }
				 judge(scores,userCard,botCard);
				 NextGame.setDisable(false);
			 }
		 });
		 picks.get(3).setOnAction(event -> {
			 if(ch < chosenImage.size()&& (!userPicked || currUser != "You")) {
				 if(currUser == "You") {
					 userCard = chosen.get(3);
					 pickedCard = 3;
					 userPicked = true;
					 SystemMessage(currUser + " picked " + userCard.toString());
				 }
				 cardimage.get(3).getChildren().remove(2);
				 cardimage.get(3).getChildren().remove(1);
				 HBox card = new HBox(10);
				 card.setSpacing(10);
				 card.setStyle(style_inner);
				 card.getChildren().add(chosen.get(3).getImage());
				 cardimage.get(3).getChildren().add(card);	 
				 ch++;
				 if(!botPicked) {
					 botPicked = true;
					 invokeBot(picks);
				 }
				 currUser = "You";
			 }
			 if(userPicked && botPicked) {
				 for(int i = 0; i < picks.size(); i++) {
					 picks.get(i).setDisable(true);
				 }
				 judge(scores,userCard,botCard);
				 NextGame.setDisable(false);
			 }
		 });
		 //the fifth button to turn the fifth card around
		 picks.get(4).setOnAction(event -> {
			 
			 if(ch < chosenImage.size() && (!userPicked || currUser != "You")) {
				 if(currUser == "You") {
					 userCard = chosen.get(4);
					 pickedCard = 4;
					 userPicked = true;
					 SystemMessage(currUser + " picked " + userCard.toString());
				 }
				 cardimage.get(4).getChildren().remove(2);
				 cardimage.get(4).getChildren().remove(1);
				 HBox card = new HBox(10);
				 card.setSpacing(10);
				 card.setStyle(style_inner);
				 card.getChildren().add(chosen.get(4).getImage());
				 cardimage.get(4).getChildren().add(card);
				 ch++;
				 if(!botPicked && currUser == "You") {
					 botPicked = true;
					 invokeBot(picks);
				 }
				 currUser = "You";
			 }
			 if(userPicked && botPicked) {
				 for(int i = 0; i < picks.size(); i++) {
					 picks.get(i).setDisable(true);
				 }
				 judge(scores,userCard,botCard);
				 NextGame.setDisable(false);
			 }
		 });
		 NextGame.setOnAction(event ->{
			 Round++;
			 chat.appendText("========================================="
			 		+ "================================================"
			 		+ "===============\n");
			 SystemMessage("Round " + Round + "\n");
			 for(int i = 0; i < 5; i++) {
				 picks.get(i).setVisible(true);
				 picks.get(i).setDisable(false);
				 cardNum.get(i).setDisable(false);
				 cardNum.get(i).getSelectionModel().clearSelection();
				 cb.get(i).setDisable(false);
				 cb.get(i).getSelectionModel().clearSelection();
				 if(cardimage.get(i).getChildren().size() == 3) {
					 cardimage.get(i).getChildren().remove(2);
					 cardimage.get(i).getChildren().remove(1);
				 } else if(cardimage.get(i).getChildren().size() == 2) {
					 cardimage.get(i).getChildren().remove(1);
				 }
			 }
			 StartApp.setDisable(false);
			 putOrders.clear();;
			 chosenImage.clear();;
			 chosen.clear();;
			 ch = 0;
			 userPicked = false;
			 botPicked = false;
			 userCard = null;
			 botCard = null;
			 currUser = "";
			 pickedCard = -1;
			 terms = 1;
			 printed = false;
			 cats[0] = 0;
			 cats[1] = 0;
		     cats[2] = 0;
		     losecats[0] = 0;
		     losecats[1] = 0;
		     losecats[2] = 0;
			 NextGame.setDisable(true);
		 });
		chat.setLayoutX(440);
		chat.setLayoutY(550);
		userline.setLayoutX(440);
		userline.setLayoutY(900);
		send.setLayoutX(860);
		send.setLayoutY(900);
		 cardChoices.getChildren().addAll(cardInLine, cardSuit);
		 /** THIS is the steps to change scenes
		 Group root = new Group();
		 Button right = new Button("scene1");
		 Button left = new Button("scene2");
		 Button left2 = new Button("Go to Scene1");
		 right.setOnAction(event -> {
			 root.getChildren().clear();
			 root.getChildren().add(cardChoices);
		 });
		 left.setOnAction(event -> {
			 root.getChildren().clear();
			 root.getChildren().addAll(new Label("TESTSSS"), left2);
		 });
		 left2.setOnAction(event -> {
			 root.getChildren().clear();
			 root.getChildren().add(cardChoices);
		 });
		 HBox n = new HBox();
		 n.getChildren().add(left);
		 n.getChildren().add(right);
		 root.getChildren().addAll(n);
		*/
		 
		
		 
		 scores.setLayoutX(820);
		 scores.setLayoutY(25);
		 scores.setFont(new Font("Arial", 30));
		 scores.setTextFill(Color.WHITE);
		 StartApp.setLayoutX(820);
		 StartApp.setLayoutY(120);
		 Pane root = new Pane();
		 pname.setLayoutX(820);
		 pname.setLayoutY(73);
		 pname.setFont(new Font("Arial", 20));
		 
		 Button viewHistory = new Button("View History");
		 viewHistory.setLayoutX(350);
		 viewHistory.setLayoutY(350);
		 viewHistory.setOnAction(event -> {
			 History.display(chat, userline, send);
		 });
		 Button logout = new Button("Logout");
			logout.setOnAction(event -> {
				stage.close();
				  Platform.runLater( () -> {
					try {
						NextGame.fire();
						
						chat.clear();
						playeracct = null;
						LoginPage.p = null;
						Round = 1;
						ch = 0;
						this.start( new Stage() );
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} );
			});
		logout.setLayoutX(820);
		logout.setLayoutY(180);
		 
		 root.getChildren().addAll(scores,StartApp,cardChoices,chat,userline,send, NextGame, rule, GameRule,pname,Stats,logout);
		 
		  
		 BackgroundImage background = new BackgroundImage(new Image("/images/wood.jpg"),
                 BackgroundRepeat.REPEAT,
                 BackgroundRepeat.REPEAT,
                 BackgroundPosition.DEFAULT,
                 BackgroundSize.DEFAULT);
		 root.setBackground(new Background(background));
		 Scene scene = new Scene(root, 1000,1000);
		 
		 
		 stage.getIcons().add(new Image("/images/1s.png"));
	        stage.setTitle("Poker Simulator");
	        
	      
	      //  Scene scene = new Scene(cardChoices, 1000, 1000);
	       
	        scene.getStylesheets().add("Style.css");
	        
	        stage.setScene(scene);
	       
	        stage.show();
	}
	private static void judge(Label s, Cards player, Cards bot) {
		if(!printed) {
			if(player.getPoints() > bot.getPoints()) {
				int re = winpoints(s, player.getPoints()-bot.getPoints(), player.getSuit().equals(bot.getSuit()));
				SystemMessage("Your "+ player.toString() + " ("+player.getPoints()+" points) > bot's " + bot.toString() + " ("+bot.getPoints()+" points). You won "+ re + " points!\n");
				printed = true;
				playeracct.setScore(playerScore);
			} else if (player.getPoints() < bot.getPoints()){
				int re = losspoints(s, bot.getPoints()-player.getPoints(), player.getSuit().equals(bot.getSuit()));
				printed = true;
				SystemMessage("Bot's "+ bot.toString() + " ("+bot.getPoints()+" points) > your " + player.toString() + " ("+player.getPoints()+" points). You lost " + -1*(re) + " points!\n");
				playeracct.setScore(playerScore);
			} else {
				printed = true;
				int re = 0;
				if(player.getNum() > bot.getNum()) {
					re = winpoints(s, player.getNum() - bot.getNum(), player.getSuit().equals(bot.getSuit()));
					SystemMessage("Your "+ player.toString() + " ("+player.getPoints()+" points) is equal to bot's " + bot.toString() + " ("+bot.getPoints()+" points), but your rank is higher. You won "+ re + " points!\n");
					playeracct.setScore(playerScore);
				} else {
					re = losspoints(s, bot.getNum()-player.getNum(), player.getSuit().equals(bot.getSuit()));
					SystemMessage("Bot's "+ bot.toString() + " ("+bot.getPoints()+" points) is equal to your " + player.toString() + " ("+player.getPoints()+" points),but your rank is lower. You lost " + -1*(re) + " points!\n");
					playeracct.setScore(playerScore);
				}
			}
			playeracct.updateEverything();
		}
	}
	private static int winpoints(Label score, int diff, boolean isSame) {
		int append = 0;
		chat.appendText("\n Game Result ===============\n");
		for(int i = 0; i < chosen.size(); i++) {
			
			if(chosen.get(i).getNum() >= 1 && chosen.get(i).getNum() <= 5) {
				cats[0]++;
			} else if (chosen.get(i).getNum() >= 6 && chosen.get(i).getNum() <= 9) {
				cats[1]++;
			} else if(chosen.get(i).getNum() >= 10 && chosen.get(i).getNum() <= 13) {
				cats[2]++;
			}
			
			if(!chosen.get(i).equals(userCard) && !chosen.get(i).equals(botCard)) {
				chat.appendText(chosen.get(i).printCosts());
				append += chosen.get(i).getPoints();
			}
		}
		int WhichCatOfWin = GreatestProb();
		chat.appendText(userCard.calcDiff(botCard));
		if(isSame) {
			chat.appendText(String.format("|%14s: %7s 2x.|\n", "Same Suit", " "));
		}
		chat.appendText("|---------------------------|\n");
		if(isSame) {
			chat.appendText(String.format("|%14s: %3d points.|\n","Total",((append+diff)*2)));
		} else {
			chat.appendText(String.format("|%14s: %3d points.|\n","Total",((append+diff))));
		}
		chat.appendText(" ===========================\n\n");
		if(isSame) {		
			playeracct.increwin(WhichCatOfWin, true);
			playerScore = playerScore+(append+diff)*2;
			score.setText("Score: " + playerScore);
		} else {
			playeracct.increwin(WhichCatOfWin, false);
			playerScore = playerScore+(append+diff);
			score.setText("Score: " + playerScore);
		}
		append = append+diff;
		if(isSame) {
			append*=2;
		}
		
		return append;
	}
	private static int losspoints(Label score, int diff, boolean isSame) {
		
		int append = 0;
		diff = (diff*(-1));
		chat.appendText("\n Game Result ===============\n");
		for(int i = 0; i < chosen.size(); i++) {
			
			if(chosen.get(i).getNum() >= 1 && chosen.get(i).getNum() <= 5) {
				losecats[0]++;
			} else if (chosen.get(i).getNum() >= 6 && chosen.get(i).getNum() <= 9) {
				losecats[1]++;
			} else if(chosen.get(i).getNum() >= 10 && chosen.get(i).getNum() <= 13) {
				losecats[2]++;
			}
			
			if(!chosen.get(i).equals(userCard) && !chosen.get(i).equals(botCard)) {
				chat.appendText(chosen.get(i).printCosts());
				append -= chosen.get(i).getPoints();
			}
		}
		//String.format(format, args)
		int WhichCatLose = GreatestloseProb();
		chat.appendText(userCard.calcDiff(botCard));	
		if(isSame) {
			chat.appendText(String.format("|%14s: %7s 2x.|\n", "Same Suit", " "));
		}
		chat.appendText("|---------------------------|\n");
		if(isSame) {
			chat.appendText(String.format("|%14s: %3d points.|\n","Total",((append+diff)*(-1)*2)));
		} else {
			chat.appendText(String.format("|%14s: %3d points.|\n","Total",((append+diff)*(-1))));
		}
		chat.appendText(" ===========================\n\n");
		if(isSame) {
			playeracct.increlost(WhichCatLose, true);
			playerScore = playerScore+(append+diff)*2;
			score.setText("Score: " + playerScore);
		} else {
			playeracct.increlost(WhichCatLose, false);
			playerScore = playerScore+(append+diff);
			score.setText("Score: " + playerScore);
		}
		append = append+diff;
		if(isSame) {
			append*=2;
		}
		
		return append;
	}
	
	private static void invokeBot(ArrayList<Button> picks) {
		 currUser = "Bot";
		 Random rand = new Random();
		 
		 int  n = rand.nextInt(50)%chosenImage.size();
		 while(n == pickedCard)  {
			 n = rand.nextInt(50)%chosenImage.size();
		 }
		 botPicked = true;
		 botCard = chosen.get(n);
		 SystemMessage(currUser + " picked " + botCard.toString());
		 picks.get(n).fire();
		 picks.get(n).setDisable(true);
	}
	private static void copyList() {
		String append = "[System]: You have picked ";
		for(Cards v: chosenImage.values()) {
			append += v.toString() + ", ";
			chosen.add(v);
		}
		chat.appendText(append.substring(0, append.length()-2) + ".\n");
		Collections.shuffle(chosen);
	}
	
	private static HBox addBorder(String style, ImageView image) {
		 HBox newcard = new HBox(10);
		 newcard.setSpacing(10);
		 String style_inner = "-fx-border-color: black;"
		 			+ "-fx-border-width: 2;"
		 			;
		 newcard.setMaxWidth(20);
		 image.setFitHeight(120);
		 image.setFitWidth(90);
		 newcard.setStyle(style_inner);
		 newcard.getChildren().add(image);
		 return newcard;
	}
	private static String convert(String type) {
		if(type == "Spade") {
			return "s";
		} else if(type == "Club") {
			return "c";
		} else if(type == "Heart") {
			return "h";
		} else {
			return "d";
		}
	}
	//returns 1 2 3 4
	private static int GreatestProb() {
		for(int i = 0; i < 3; i++) {
			if(cats[i] >= 3) {
				return i+1;
			}
		}
		return 4;
	}
	private static int GreatestloseProb() {
		for(int i = 0; i < 3; i++) {
			if(losecats[i] >= 3) {
				System.out.println(i+1);
				return i+1;
			}
		}
		return 4;
	}
	private static String convertNum(String num) {
		if(num == "J") {
			return "11";
		} else if(num == "Q") {
			return "12";
		} else if(num == "K") {
			return "13";
		} else {
			return num;
		}
	}
	private static void SystemMessage(String message) {
		chat.appendText("[System]: "+message+"\n");
	}
	private static void CallAction(VBox cardimage, int index, ArrayList<ChoiceBox<String>> cb, ArrayList<ChoiceBox<String>> cardNum, int isSuit) {
		if(isSuit == 1) {
			 if(cardNum.get(index).getValue() != null) {
				 HBox card = new HBox(10);
				 card.setSpacing(10);
				 String cnum = cardNum.get(index).getValue();
				 String type = cb.get(index).getValue();
				 type = convert(type);
				 cnum = convertNum(cnum);
				 ImageView image = pics.get(cnum+type);
				 boolean containsCard = false;
				 for(int i = 0; i < 5; i++) {
					 if(putOrders.containsKey(i)) {
						 containsCard = containsCard || putOrders.get(i).getChildren().contains(image);
					 }
				 }
				 String style_inner = null;
				 Cards newc = new Cards(type,cnum,image);
				 if(!containsCard) {
					 
					 chosenImage.put(index, newc);
					 card.getChildren().addAll(image);
					 style_inner = "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.9) , 10,0,4,6 );";
					 
				 } else {
					 SystemMessage("Keep in mind do not pick repeated card!");
					 return;
				 }
				 card.setMaxWidth(20);
				 card.setStyle(style_inner);
				 if(putOrders.containsKey(index)) {
					 chosenImage.remove(index);
					 cardimage.getChildren().remove(putOrders.get(index));
				 }
				 putOrders.put(index, card);
				 chosenImage.put(index, newc);
				 cardimage.getChildren().add(card);
			 }
		} else {
			 if(cb.get(index).getValue() != null) {
				 HBox card = new HBox(index);
				 card.setSpacing(10);
				 String cnum = cardNum.get(index).getValue();
				 String type = cb.get(index).getValue();
				 type = convert(type);
				 cnum = convertNum(cnum);
				 ImageView image = pics.get(cnum+type);
				 boolean containsCard = false;
				 for(int i = 0; i < 5; i++) {
					 if(putOrders.containsKey(i)) {
						 containsCard = containsCard || putOrders.get(i).getChildren().contains(image);
					 }
					 
				 }
				 String style_inner = null;
				 Cards newc = new Cards(type,cnum,image);
				 if(!containsCard) {
					 chosenImage.put(index, newc);
					 card.getChildren().addAll(image);
					 style_inner = "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.9) , 10,0,4,6 );"
							 	
					 			;
				 } else { 
					 SystemMessage("Keep in mind do not pick repeated card!");
					 return;
				 }
				 
				 card.setMaxWidth(20);
				 card.setStyle(style_inner);
				 if(putOrders.containsKey(index)) {
					 chosenImage.remove(index);
					 cardimage.getChildren().remove(putOrders.get(index));
				 }
				 chosenImage.put(index, newc);
				 putOrders.put(index, card);
				 cardimage.getChildren().add(card);
			 }
		}
	}
}
