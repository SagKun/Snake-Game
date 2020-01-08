package View;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Question;
import Utils.Fonts;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
	@FXML
	private Label one;
	@FXML
	private Label two;
	@FXML
	private Label three;
	@FXML
	private Label four;


	Question questionObject;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {


		question.setFont(Fonts.minecraft30);
		ans1.setFont(Fonts.minecraft15);
		ans2.setFont(Fonts.minecraft15);
		ans3.setFont(Fonts.minecraft15);
		ans4.setFont(Fonts.minecraft15);
		one.setFont(Fonts.minecraft15);
		two.setFont(Fonts.minecraft15);
		three.setFont(Fonts.minecraft15);
		four.setFont(Fonts.minecraft15);
		
		
		new Pulse(ans1).setCycleCount(Timeline.INDEFINITE).setSpeed(0.3).play();
		new Pulse(ans2).setCycleCount(Timeline.INDEFINITE).setSpeed(0.3).play();
		new Pulse(ans3).setCycleCount(Timeline.INDEFINITE).setSpeed(0.3).play();
		new Pulse(ans4).setCycleCount(Timeline.INDEFINITE).setSpeed(0.3).play();
		new Pulse(one).setCycleCount(Timeline.INDEFINITE).setSpeed(0.3).play();
		new Pulse(two).setCycleCount(Timeline.INDEFINITE).setSpeed(0.3).play();
		new Pulse(three).setCycleCount(Timeline.INDEFINITE).setSpeed(0.3).play();
		new Pulse(four).setCycleCount(Timeline.INDEFINITE).setSpeed(0.3).play();
		
		
	
	}




	@FXML
	void resumeGame(MouseEvent event ) {
		Node answer = (Node) event.getSource();
		Label answerLabel=(Label)answer;
		new ZoomOut(answer).setCycleCount(1).setSpeed(0.7).play();
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.7), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if((answerLabel.getText().equals(questionObject.getCorrect_ans())))
				{
					GameView.answeredRight=true;
				}
				else
				{
					GameView.answeredRight=false;
				}
				Robot robot = null;
				try {
					robot = new Robot();
				} catch (AWTException e) {
					e.printStackTrace();
				}

				robot.keyPress(KeyEvent.VK_SPACE);
				anchorPane.setVisible(false);
			}
		}) , new KeyFrame(Duration.seconds(1)));
		timeline.play();

	}


	void setQuestion(Question q)
	{
		questionObject=q;
	}


}
