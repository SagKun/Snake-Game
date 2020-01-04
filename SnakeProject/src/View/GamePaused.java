package View;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Utils.Fonts;
import animatefx.animation.Pulse;
import animatefx.animation.ZoomOut;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class GamePaused implements Initializable {


	@FXML
	private AnchorPane pauseAnchor;

	@FXML
	private Label resumeLabel;

	@FXML
	private Label endLabel;

	@FXML
	private Label exitLabel;

	@FXML
	private Label gamePausedLabel;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	resumeLabel.setFont(Fonts.minecraft30);
		 endLabel.setFont(Fonts.minecraft30);
		 exitLabel.setFont(Fonts.minecraft30);
		 gamePausedLabel.setFont(Fonts.minecraft50);
		 new Pulse(resumeLabel).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
		 new Pulse(endLabel).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
		 new Pulse(exitLabel).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
		 new Pulse(gamePausedLabel).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
	}
    
    @FXML
    void backToMenuBtn(ActionEvent event) {

    }

    @FXML
    void instructionBtn(ActionEvent event) {

    }

    @FXML
    void newGameBtn(ActionEvent event) {

    }

    @FXML
    void resumeGame(MouseEvent event ) {
    	
    	new ZoomOut(resumeLabel).setCycleCount(1).setSpeed(0.7).play();
    	Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.7), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Robot robot = null;
				try {
					robot = new Robot();
				} catch (AWTException e) {
					e.printStackTrace();
				}
				
				robot.keyPress(KeyEvent.VK_SPACE);
				pauseAnchor.setVisible(false);
			}
		}) , new KeyFrame(Duration.seconds(1)));
	timeline.play();

    }

    @FXML
    void endGame(MouseEvent event ) {
    	
    	new ZoomOut(endLabel).setCycleCount(1).setSpeed(0.7).play();
    	Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.7), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
			Stage stage=(Stage) pauseAnchor.getScene().getWindow();
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainView.fxml"));
				AnchorPane pane  = loader.load();
				Scene scene = new Scene(pane);
				stage.setScene(scene);
				//stage.show();	
			}
			
			catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		}) , new KeyFrame(Duration.seconds(1)));
	timeline.play();

    }
    
    @FXML
    void exit(MouseEvent event ) {
    	
    	new ZoomOut(exitLabel).setCycleCount(1).setSpeed(0.7).play();
    	Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.7), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Platform.exit();
				System.exit(0);;
			}
		}) , new KeyFrame(Duration.seconds(1)));
	timeline.play();

    }
    
	

}
