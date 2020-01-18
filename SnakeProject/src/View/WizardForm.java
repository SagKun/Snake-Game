package View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Controller.WizardController;
import Model.FoodFactory;
import Model.Level;
import Model.Question;
import Utils.Fonts;
import animatefx.animation.Pulse;
import animatefx.animation.Shake;
import animatefx.animation.ZoomOut;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class WizardForm implements Initializable{

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private TextArea questionField;

	@FXML
	private TextField authorField;

	@FXML
	private ComboBox<Level> levelCombo;

	@FXML
	private CheckBox firstChoose;

	@FXML
	private CheckBox secondChoose;

	@FXML
	private CheckBox thirdChoose;

	@FXML
	private CheckBox fourthChoose;

	@FXML
	private TextArea firstField;

	@FXML
	private TextArea secondField;

	@FXML
	private TextArea thirdField;

	@FXML
	private TextArea fourthField;

	@FXML
	private Label returnBtn;

	@FXML
	private Label submitBtn;
	@FXML
	private Label header;

	public static boolean isEdit;

	public static Question oldQuestion;

	private Question newQuestion;

	private WizardController controller = new WizardController();

	@FXML
	void returnToWizard(MouseEvent event) {
		
		new ZoomOut(returnBtn).setCycleCount(1).setSpeed(0.2).play();
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				AnchorPane pane;
				try {
					pane = FXMLLoader.load(getClass().getResource("WizardView.fxml"));
					pane.setPrefSize(anchorPane.getWidth(), anchorPane.getHeight());
					anchorPane.getChildren().removeAll(anchorPane.getChildren());
					anchorPane.getChildren().add(pane);
				}
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}) , new KeyFrame(Duration.seconds(1.5)));
		timeline.play();
		
	}

	@FXML
	void submitForm(MouseEvent event) {
		new Shake(submitBtn).setCycleCount(1).setSpeed(1).play();
		String question = questionField.getText();
		String author = authorField.getText();
		Level level = levelCombo.getSelectionModel().getSelectedItem();
		int correct=0 ;
		String firstAnswer = "";
		String secondAnswer = "";
		String thirdAnswer = "";
		String fourthAnswer="";
		boolean correctSelected = false;
		boolean blanc = false;
		firstAnswer = firstField.getText();
		secondAnswer = secondField.getText();
		thirdAnswer = thirdField.getText();
		fourthAnswer=fourthField.getText();
		if(firstChoose.isSelected())
		{
			correct = 0;
			correctSelected = true;
		}
		else if(secondChoose.isSelected()) {
			correct = 1;
			correctSelected = true;
		}
		else if(thirdChoose.isSelected()) {
			correct = 2;
			correctSelected = true;
		}
		else if(fourthChoose.isSelected()) {
			correct = 3;
			correctSelected = true;
		}
		else {
			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setHeaderText("Need To Select Correct Answer");
			al.setTitle("System Messege");
			al.setResizable(false);
			al.show();
		}
		if(correctSelected  && (question.equals("") || author.equals("")|| firstAnswer.equals("")
				|| secondAnswer.equals("") || thirdAnswer.equals(""))) {
			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setHeaderText("Need To Fill All Fields");
			al.setTitle("System Messege");
			al.setResizable(false);
			al.show();
			blanc = true;
		}
		if(correctSelected && !blanc) {

			FoodFactory factory = new FoodFactory();
			ArrayList<String> answers = new ArrayList<>();

			answers.add(firstAnswer);
			answers.add(secondAnswer);
			answers.add(thirdAnswer);
			answers.add(fourthAnswer);

			if(isEdit) {
				newQuestion = factory.getQuestion(level, 0, 0, question, answers, correct, author);
				controller.editQuestion(oldQuestion, newQuestion);
				Alert al = new Alert(Alert.AlertType.INFORMATION);
				al.setHeaderText("Question Has Been Edited");
				al.setTitle("System Messege");
				al.setResizable(false);
				al.show();
			}
			else {
				Question q = factory.getQuestion(level, 0, 0, question, answers, correct, author);
				controller.addQuestion(q);
				Alert al = new Alert(Alert.AlertType.INFORMATION);
				al.setHeaderText("Question Has Been Added");
				al.setTitle("System Messege");
				al.setResizable(false);
				al.show();
			}
			AnchorPane pane;
			try {
				pane = FXMLLoader.load(getClass().getResource("WizardView.fxml"));
				pane.setPrefSize(anchorPane.getWidth(), anchorPane.getHeight());
				anchorPane.getChildren().removeAll(anchorPane.getChildren());
				anchorPane.getChildren().add(pane);
			}
			catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		header.setFont(Fonts.minecraft50);
		returnBtn.setFont(Fonts.minecraft30);
		submitBtn.setFont(Fonts.minecraft30);
		new Pulse(returnBtn).setCycleCount(Timeline.INDEFINITE).setSpeed(0.5).play();
		new Pulse(submitBtn).setCycleCount(Timeline.INDEFINITE).setSpeed(0.5).play();
		
		ObservableList<Level> levels = FXCollections.observableArrayList();
		for(Level l : Level.values()) {
			levels.add(l);
		}
		levelCombo.setItems(levels);
		if(isEdit) {
			firstChoose.setSelected(false);
			secondChoose.setSelected(false);
			thirdChoose.setSelected(false);
			fourthChoose.setSelected(false);
			questionField.setText(oldQuestion.getQuestion());
			authorField.setText(oldQuestion.getAuthor());
			levelCombo.getSelectionModel().select(oldQuestion.getLevel());
			firstField.setText(String.valueOf(oldQuestion.getAnswers().get(0)));
			firstChoose.setSelected(true);
			secondField.setText(String.valueOf(oldQuestion.getAnswers().get(1)));
			thirdField.setText(String.valueOf(oldQuestion.getAnswers().get(2)));
			fourthField.setText(String.valueOf(oldQuestion.getAnswers().get(3)));
			switch(oldQuestion.getCorrect_ans())
			{
			case 0: firstChoose.setSelected(true);
					break;
			case 1: secondChoose.setSelected(true);
					break;
			case 2:thirdChoose.setSelected(true);
					break;
			case 3:fourthChoose.setSelected(true);
					break;
			}
		}
	}

	@FXML
	void firstAnswerPressed(ActionEvent event) {
		secondChoose.setSelected(false);
		thirdChoose.setSelected(false);
		fourthChoose.setSelected(false);
	}

	@FXML
	void secondAnswerPressed(ActionEvent event) {
		firstChoose.setSelected(false);
		thirdChoose.setSelected(false);
		fourthChoose.setSelected(false);
	}


	@FXML
	void thirdAnswerPressed(ActionEvent event) {
		firstChoose.setSelected(false);
		secondChoose.setSelected(false);
		fourthChoose.setSelected(false);
	}

	@FXML
	void fourAnswerPressed(ActionEvent event) {
		firstChoose.setSelected(false);
		secondChoose.setSelected(false);
		thirdChoose.setSelected(false);
	}

}
