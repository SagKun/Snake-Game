package View;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

import Controller.GameController;
import Model.Question;
import Utils.Fonts;
import Utils.Sound;
import animatefx.animation.Pulse;
import animatefx.animation.ZoomOut;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class QuestionView implements Initializable{

	@FXML
	private AnchorPane anchorPane;

	

	@FXML
	private Label question;

	@FXML
	private Label ans1;

	@FXML
	private Label ans2;

	@FXML
	private Label ans3;

	@FXML
	private Label ans4;
	
	private GameController gameController;
	
	private Label answerFeedBack;

	Question questionObject;
	
	private MediaPlayer clapSound;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.questionObject = GameView.questionForScreen;
		gameController = GameController.getInstance();
		question.setFont(Fonts.minecraft30);
		ans1.setFont(Fonts.minecraft15);
		ans2.setFont(Fonts.minecraft15);
		ans3.setFont(Fonts.minecraft15);
		ans4.setFont(Fonts.minecraft15);
		answerFeedBack=new Label();
		answerFeedBack.setFont(Fonts.minecraft30);
		question.setWrapText(true);
		ans1.setWrapText(true);
		ans2.setWrapText(true);
		ans3.setWrapText(true);
		ans4.setWrapText(true);
		anchorPane.getChildren().add(answerFeedBack);
		clapSound = new Sound().getClapSound();
		
		
		question.setText(questionObject.getQuestion());
		 ans1.setText(questionObject.getAnswerToGui(0));
		 ans2.setText(questionObject.getAnswerToGui(1));		
		 ans3.setText(questionObject.getAnswerToGui(2));
		 ans4.setText(questionObject.getAnswerToGui(3));
		 
		
		new Pulse(ans1).setCycleCount(Timeline.INDEFINITE).setSpeed(0.3).play();
		new Pulse(ans2).setCycleCount(Timeline.INDEFINITE).setSpeed(0.3).play();
		new Pulse(ans3).setCycleCount(Timeline.INDEFINITE).setSpeed(0.3).play();
		new Pulse(ans4).setCycleCount(Timeline.INDEFINITE).setSpeed(0.3).play();
		
		
		
	
	}




	@FXML
	void resumeGame(MouseEvent event ) {
		Node answer = (Node) event.getSource();
		Label answerLabel=(Label)answer;
		if(gameController.checkUserAnswer(questionObject, (Integer.parseInt(answerLabel.getText().substring(0, 1)))-1))
		{
			
			clapSound.play();
			answerFeedBack.setStyle("-fx-text-fill: green;");
			answerFeedBack.setText("CORRECT ANSWER!");
			answerFeedBack.setLayoutX(answerLabel.getLayoutX()+100);
			answerFeedBack.setLayoutY(answerLabel.getLayoutY()+20);
		}
		else
		{
			answerFeedBack.setStyle("-fx-text-fill: red;");
			answerFeedBack.setText("WRONG ANSWER!");
			answerFeedBack.setLayoutX(answerLabel.getLayoutX()+100);
			answerFeedBack.setLayoutY(answerLabel.getLayoutY()+20);

		}
		new ZoomOut(answer).setCycleCount(1).setSpeed(0.7).play();
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				answerFeedBack.setVisible(true);
				Robot robot = null;
				try {
					robot = new Robot();
				} catch (AWTException e) {
					e.printStackTrace();
				}

				robot.keyPress(KeyEvent.VK_SPACE);
				anchorPane.setVisible(false);
			}
		}) , new KeyFrame(Duration.seconds(2)));
		timeline.play();

	}


	




	


}
