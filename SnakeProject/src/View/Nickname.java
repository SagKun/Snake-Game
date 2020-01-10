package View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Controller.HistoryController;
import Model.Board;
import Model.GameState;
import Model.Player;
import Utils.Fonts;
import animatefx.animation.Pulse;
import animatefx.animation.RubberBand;
import animatefx.animation.ZoomOut;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Nickname implements Initializable{

    @FXML
    private TextField nickName;

    @FXML
    private Text score;

    @FXML
    private Label returnMenu;
    @FXML
    private Label gameOver;


    @FXML
    private Label newGame;

    @FXML
    private Label submit;

    @FXML
    private Label setNick;

    

    @FXML
    private Label yourScoreIS;

    @FXML
    private AnchorPane anchor;

    @FXML
    void newGameBtn(MouseEvent event) {
    	new ZoomOut(newGame).setCycleCount(1).setSpeed(0.2).play();
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				try {
					StackPane popupInGameView=(StackPane) anchor.getParent().getParent();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
					StackPane pane  = loader.load();
					pane.setPrefSize(popupInGameView.getWidth(), popupInGameView.getHeight());
					popupInGameView.getChildren().removeAll(popupInGameView.getChildren());
					popupInGameView.getChildren().add(pane);
					
					GameView view = (GameView)loader.getController();	
					view.setStage((Stage)pane.getScene().getWindow());
					view.resetGame();
					view.resume();	
				
				}

				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}) , new KeyFrame(Duration.seconds(1.5)));
		timeline.play();   	
    }

    @FXML
    void returnToMenuBtn(MouseEvent event) {
    	Stage stage=(Stage) anchor.getScene().getWindow();
		new ZoomOut(returnMenu).setCycleCount(1).setSpeed(0.2).play();
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainView.fxml"));
					AnchorPane pane  = loader.load();
					Scene scene = new Scene(pane);
					stage.setScene(scene);
				}

				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}) , new KeyFrame(Duration.seconds(1.5)));
		timeline.play();
	}
    

    @FXML
    void submitBtn(MouseEvent event) {
    	new RubberBand(submit).setCycleCount(1).setSpeed(0.2).play();
    	if(nickName.getText().equals("")) {
    		Alert al = new Alert(Alert.AlertType.ERROR);
    		al.setHeaderText("Nickname Is Not Set");
    		al.setTitle("System Messege");
    		al.setResizable(false);
    		al.show();
    	}
    	else
    	{
    		
    		
    		
    		new ZoomOut(submit).setCycleCount(1).setSpeed(0.2).play();
    		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), new EventHandler<ActionEvent>() {
    			@Override
    			public void handle(ActionEvent actionEvent) {
    	    		Player p = new Player(nickName.getText(), Board.getInstance().getScore());
    	    		HistoryController history = new HistoryController();
    	    		history.addScoreIfTopTen(p);
    	    		Stage stage=(Stage) anchor.getScene().getWindow();
    				try {
    					FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/HistoryView.fxml"));
    					StackPane pane  = loader.load();
    					Scene scene = new Scene(pane);
    					stage.setScene(scene);
    				}

    				catch (IOException e) {
    					e.printStackTrace();
    				}
    			}
    		}) , new KeyFrame(Duration.seconds(1.5)));
    		timeline.play();
    		
    		
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		score.setFont(Fonts.minecraft30);
		newGame.setFont(Fonts.minecraft30);
		submit.setFont(Fonts.minecraft30);
		setNick.setFont(Fonts.minecraft30);
		yourScoreIS.setFont(Fonts.minecraft30);
		returnMenu.setFont(Fonts.minecraft30);
		gameOver.setFont(Fonts.minecraft50);
		new Pulse(score).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
		new Pulse(newGame).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
		new Pulse(submit).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
		new Pulse(returnMenu).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
		new Pulse(gameOver).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
		String s = "" + Board.getInstance().getScore();
		score.setText(s);
	}

	
	
	
	
	
}
