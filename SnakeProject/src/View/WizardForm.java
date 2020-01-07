package View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Controller.WizardController;
import Model.FoodFactory;
import Model.Level;
import Model.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
	private Button returnBtn;

	@FXML
	private Button submitBtn;

	public static boolean isEdit;

	public static Question oldQuestion;

	private Question newQuestion;

	private WizardController controller = new WizardController();

	@FXML
	void returnToWizard(ActionEvent event) {
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

	@FXML
	void submitForm(ActionEvent event) {
		String question = questionField.getText();
		String author = authorField.getText();
		Level level = levelCombo.getSelectionModel().getSelectedItem();
		String correct = "";
		String firstAnswer = "";
		String secondAnswer = "";
		String thirdAnswer = "";
		boolean correctSelected = false;
		boolean blanc = false;
		if(firstChoose.isSelected())
		{
			correct = firstField.getText();
			firstAnswer = secondField.getText();
			secondAnswer = thirdField.getText();
			thirdAnswer = fourthField.getText();
			correctSelected = true;
		}
		else if(secondChoose.isSelected()) {
			correct = secondField.getText();
			firstAnswer = firstField.getText();
			secondAnswer = thirdField.getText();
			thirdAnswer = fourthField.getText();
			correctSelected = true;
		}
		else if(thirdChoose.isSelected()) {
			correct = thirdField.getText();
			firstAnswer = firstField.getText();
			secondAnswer = secondField.getText();
			thirdAnswer = fourthField.getText();
			correctSelected = true;
		}
		else if(fourthChoose.isSelected()) {
			correct = fourthField.getText();
			firstAnswer = firstField.getText();
			secondAnswer = secondField.getText();
			thirdAnswer = thirdField.getText();
			correctSelected = true;
		}
		else {
			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setHeaderText("Need To Select Correct Answer");
			al.setTitle("System Messege");
			al.setResizable(false);
			al.show();
		}
		if(correctSelected  && (question.equals("") || author.equals("") || correct.equals("")|| firstAnswer.equals("")
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
			answers.add(correct);
			answers.add(firstAnswer);
			answers.add(secondAnswer);
			answers.add(thirdAnswer);
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
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Level> levels = FXCollections.observableArrayList();
		for(Level l : Level.values()) {
			levels.add(l);
		}
		levelCombo.setItems(levels);
		if(isEdit) {
			questionField.setText(oldQuestion.getQuestion());
			authorField.setText(oldQuestion.getAuthor());
			levelCombo.getSelectionModel().select(oldQuestion.getLevel());
			firstField.setText(oldQuestion.getCorrect_ans());
			firstChoose.setSelected(true);
			ArrayList<String> ans = new ArrayList<>();
			for(String answer : oldQuestion.getAnswers()) {
				if(!answer.equals(oldQuestion.getCorrect_ans()))
					ans.add(answer);
			}
			secondField.setText(ans.get(0));
			thirdField.setText(ans.get(1));
			fourthField.setText(ans.get(2));
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
