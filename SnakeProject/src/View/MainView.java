package View;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Main.Main;
import Utils.Fonts;
import animatefx.animation.Pulse;
import animatefx.animation.ZoomOut;
import animatefx.animation.ZoomOutDown;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainView implements Initializable{
	
	boolean playPressed=false;
	boolean highscoresPressed=false;
	boolean wizardPressed=false;
	boolean exitPressed=false;
	 @FXML
	    private AnchorPane mainView;

	    @FXML
	    private Label play;

	    @FXML
	    private Label highscores;

	    @FXML
	    private Label questions;
	    
	    @FXML
	    private Label exit;
		private Stage stage;
		 
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			playPressed=false;
			highscoresPressed=false;
			wizardPressed=false;
			play.setFont(Fonts.minecraft50);
			highscores.setFont(Fonts.minecraft50);
			questions.setFont(Fonts.minecraft50);
			exit.setFont(Fonts.minecraft50);
			new Pulse(play).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
			new Pulse(highscores).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
			new Pulse(questions).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
			new Pulse(exit).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();


		}
		
		public void exit(MouseEvent event) {
			if(!exitPressed)	
			{
				exitPressed=true;
	    	new ZoomOut(exit).setCycleCount(1).setSpeed(0.2).play();
				Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						Main.f.delete();
						System.exit(0);
					}
				}) , new KeyFrame(Duration.seconds(1.5)));
				timeline.play();
			}
		}
		
	    public void play(MouseEvent event) {
	    	
			if(!playPressed)	
			{
			playPressed=true;
	    	new ZoomOut(play).setCycleCount(1).setSpeed(0.2).play();
				Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						try {
							FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
							StackPane pane  = loader.load();
							pane.setPrefSize(mainView.getWidth(), mainView.getHeight());
							mainView.getChildren().removeAll(mainView.getChildren());
							mainView.getChildren().add(pane);
							
							GameView view = (GameView)loader.getController();		
							view.setStage((Stage)pane.getScene().getWindow());
							view.resume();			
						}
						
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}) , new KeyFrame(Duration.seconds(1.5)));
				timeline.play();
			}
		}
	    
	    
	    public void loadHighscores(MouseEvent event) {
	    	if(!highscoresPressed)
	    	{
	    		highscoresPressed=true;
	    	new ZoomOut(highscores).setCycleCount(1).setSpeed(0.2).play();
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("HistoryView.fxml"));
						StackPane pane  = loader.load();
						pane.setPrefSize(mainView.getWidth(), mainView.getHeight());
						mainView.getChildren().removeAll(mainView.getChildren());
						mainView.getChildren().add(pane);	
					}
					
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}) , new KeyFrame(Duration.seconds(1.5)));
			timeline.play();
	    	}
	    }
	    
	    
	    public void loadQuestionWiz(MouseEvent event) {
	    	if(!wizardPressed)
	    	{
	    		wizardPressed=true;
	    	new ZoomOut(questions).setCycleCount(1).setSpeed(0.2).play();
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("WizardView.fxml"));
						AnchorPane pane  = loader.load();
						pane.setPrefSize(mainView.getWidth(), mainView.getHeight());
						mainView.getChildren().removeAll(mainView.getChildren());
						mainView.getChildren().add(pane);
							
					}
					
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}) , new KeyFrame(Duration.seconds(1.5)));
			timeline.play();
			
	    	}
	    }
	    
	    
	    
	    
	    

		public Stage getStage() {
			return stage;
		}

		public void setStage(Stage stage) {
			this.stage = stage;
		}
	
		
		
}
