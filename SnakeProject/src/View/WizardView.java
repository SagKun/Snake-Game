package View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.table.TableCellEditor;

import com.jfoenix.controls.JFXButton;

import Controller.WizardController;
import Model.FoodFactory;
import Model.Level;
import Model.Question;
import Model.SysData;
import Utils.Fonts;
import animatefx.animation.Pulse;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp.Factory;

public class WizardView implements Initializable{

	@FXML
	private TableView<QuestionsForWizardTable> questionsTable;

	@FXML
	private TableColumn<QuestionsForWizardTable, String> questionColumn;

	@FXML
	private TableColumn<QuestionsForWizardTable, String> levelColumn;

	@FXML
	private TableColumn<QuestionsForWizardTable, String> authorColumn;

	@FXML
	private TableColumn<QuestionsForWizardTable, String> option1Column;

	@FXML
	private TableColumn<QuestionsForWizardTable, String> option2Column;

	@FXML
	private TableColumn<QuestionsForWizardTable, String> option3Column;

	@FXML
	private TableColumn<QuestionsForWizardTable, String> correctColumn;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Label resume;

	@FXML
	private JFXButton removeBtn;

	@FXML
	private JFXButton addBtn;

	@FXML
	private TextField questionField;

	@FXML
	private TextField authorField;

	@FXML
	private ComboBox<Level> levelCombo;

	@FXML
	private TextField option1Field;

	@FXML
	private TextField option3Field;

	@FXML
	private TextField option2Field;

	@FXML
	private TextField correctField;

	private ObservableList<QuestionsForWizardTable> questionsList = FXCollections.observableArrayList();

	private WizardController controller = new WizardController();

	private QuestionsForWizardTable oldQuestion;

	private QuestionsForWizardTable newQuestion;

	@FXML
	void addQuestion(ActionEvent event) {
		if(questionField.getText().equals("") || authorField.getText().equals("") ||
				levelCombo.getSelectionModel().getSelectedItem().toString().equals("") || correctField.getText().equals("")
				|| option1Field.getText().equals("") || option2Field.getText().equals("") || option3Field.equals("")) {
			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setHeaderText("Fill In All Fields");
			al.setTitle("System Messege");
			al.setResizable(false);
			al.show();
		}
		else {
			String questionFromUser = questionField.getText();
			String authorFromUser= authorField.getText();
			String pickedFromCombo = levelCombo.getSelectionModel().getSelectedItem().toString();
			Level level;
			if(pickedFromCombo.equals("HARD"))
				level = Level.HARD;
			else if(pickedFromCombo.equals("EASY"))
				level = Level.EASY;
			else
				level = Level.MODERATE;
			String correctFromUser = correctField.getText();
			String option1FromUser = option1Field.getText();
			String option2FromUser = option2Field.getText();
			String option3FromUser = option3Field.getText();
			ArrayList<String> answers = new ArrayList<>();
			answers.add(correctFromUser);
			answers.add(option3FromUser);
			answers.add(option2FromUser);
			answers.add(option1FromUser);
			FoodFactory factory = new FoodFactory();
			Question q = factory.getQuestion(level, 0, 0, questionFromUser, answers, correctFromUser, authorFromUser);
			controller.addQuestion(q);
			QuestionsForWizardTable qForTable = new QuestionsForWizardTable(q);
			questionsList.add(qForTable);
			Alert al = new Alert(Alert.AlertType.INFORMATION);
			al.setHeaderText("Question Added Successfully");
			al.setTitle("System Messege");
			al.setResizable(false);
			al.show();
			clear();
		}
	}

	@FXML
	void confirmEdit(ActionEvent event) {
		QuestionsForWizardTable oldQuestion;
		QuestionsForWizardTable newQuestion;
		for(Object obj : questionsList.toArray()) {
			QuestionsForWizardTable question = (QuestionsForWizardTable)obj;
			if(question.getEdit().isPressed())
			{
				oldQuestion = question;
			}

		}
	}

	@FXML
	void removeQuestion(ActionEvent event) {
		Alert al = new Alert(Alert.AlertType.CONFIRMATION);
		al.setHeaderText("Are You Sure You Want To Delete This Question?");
		al.setTitle("System Messege");
		al.setResizable(false);
		Optional<ButtonType> result = al.showAndWait();
		if(result.get() == ButtonType.OK) {
			QuestionsForWizardTable selectedItem = questionsTable.getSelectionModel().getSelectedItem();
			questionsTable.getItems().remove(selectedItem);
			Question q = selectedItem.createQuestion();
			controller.deleteQuestion(q);
		}
	}

	@FXML
	void resumeToMenu(MouseEvent event) {

		Stage stage=(Stage) anchorPane.getScene().getWindow();
		new ZoomOut(resume).setCycleCount(1).setSpeed(0.2).play();
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// setting the cells to the data
		questionColumn.setCellValueFactory(new PropertyValueFactory<QuestionsForWizardTable, String>("question"));
		levelColumn.setCellValueFactory(new PropertyValueFactory<QuestionsForWizardTable, String>("level"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<QuestionsForWizardTable, String>("author"));
		option1Column.setCellValueFactory(new PropertyValueFactory<QuestionsForWizardTable, String>("option1"));
		option2Column.setCellValueFactory(new PropertyValueFactory<QuestionsForWizardTable, String>("option2"));
		option3Column.setCellValueFactory(new PropertyValueFactory<QuestionsForWizardTable, String>("option3"));
		correctColumn.setCellValueFactory(new PropertyValueFactory<QuestionsForWizardTable, String>("correctAnswer"));
		// setting editable and setting the set on edit actions
		questionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		questionColumn.setOnEditStart(e -> {
			int old = questionsTable.getSelectionModel().getFocusedIndex();
			oldQuestion = new QuestionsForWizardTable(SysData.questionsDB.get(old));
		}
				);
		questionColumn.setOnEditCommit(e -> {
			newQuestion = questionsTable.getSelectionModel().getSelectedItem();
			newQuestion.setQuestion(e.getNewValue());
			controller.editQuestion(oldQuestion.createQuestion(), newQuestion.createQuestion());
		}
				);
		levelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		levelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		levelColumn.setOnEditStart(e -> {
			int old = questionsTable.getSelectionModel().getFocusedIndex();
			oldQuestion = new QuestionsForWizardTable(SysData.questionsDB.get(old));
		}
				);
		levelColumn.setOnEditCommit(e -> {
			newQuestion = questionsTable.getSelectionModel().getSelectedItem();
			newQuestion.setLevel(e.getNewValue());
			controller.editQuestion(oldQuestion.createQuestion(), newQuestion.createQuestion());
		}
				);
		authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		authorColumn.setOnEditStart(e -> {
			int old = questionsTable.getSelectionModel().getFocusedIndex();
			oldQuestion = new QuestionsForWizardTable(SysData.questionsDB.get(old));
		}
				);
		authorColumn.setOnEditCommit(e -> {
			newQuestion = questionsTable.getSelectionModel().getSelectedItem();
			newQuestion.setAuthor(e.getNewValue());
			controller.editQuestion(oldQuestion.createQuestion(), newQuestion.createQuestion());
		}
				);
		option1Column.setCellFactory(TextFieldTableCell.forTableColumn());
		option1Column.setOnEditStart(e -> {
			int old = questionsTable.getSelectionModel().getFocusedIndex();
			oldQuestion = new QuestionsForWizardTable(SysData.questionsDB.get(old));
		}
				);
		option1Column.setOnEditCommit(e -> {
			newQuestion = questionsTable.getSelectionModel().getSelectedItem();
			newQuestion.setOption1(e.getNewValue());
			controller.editQuestion(oldQuestion.createQuestion(), newQuestion.createQuestion());
		}
				);
		option2Column.setCellFactory(TextFieldTableCell.forTableColumn());
		option2Column.setOnEditStart(e -> {
			int old = questionsTable.getSelectionModel().getFocusedIndex();
			oldQuestion = new QuestionsForWizardTable(SysData.questionsDB.get(old));
		}
				);
		option2Column.setOnEditCommit(e -> {
			newQuestion = questionsTable.getSelectionModel().getSelectedItem();
			newQuestion.setOption2(e.getNewValue());
			controller.editQuestion(oldQuestion.createQuestion(), newQuestion.createQuestion());
		}
				);
		option3Column.setCellFactory(TextFieldTableCell.forTableColumn());
		option3Column.setOnEditStart(e -> {
			int old = questionsTable.getSelectionModel().getFocusedIndex();
			oldQuestion = new QuestionsForWizardTable(SysData.questionsDB.get(old));
		}
				);
		option3Column.setOnEditCommit(e -> {
			newQuestion = questionsTable.getSelectionModel().getSelectedItem();
			newQuestion.setOption3(e.getNewValue());
			controller.editQuestion(oldQuestion.createQuestion(), newQuestion.createQuestion());
		}
				);
		correctColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		correctColumn.setOnEditStart(e -> {
			int old = questionsTable.getSelectionModel().getFocusedIndex();
			oldQuestion = new QuestionsForWizardTable(SysData.questionsDB.get(old));
		}
				);
		correctColumn.setOnEditCommit(e -> {
			newQuestion = questionsTable.getSelectionModel().getSelectedItem();
			newQuestion.setCorrectAnswer(e.getNewValue());
			controller.editQuestion(oldQuestion.createQuestion(), newQuestion.createQuestion());
		}
				);
		
		
		new Pulse(resume).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
		resume.setFont(Fonts.minecraft50);
		ArrayList<Level> levels = new ArrayList<>();
		for(Level l : Level.values()) {
			levels.add(l);
		}
		ObservableList<Level> levelsView = FXCollections.observableArrayList();
		levelsView.addAll(levels);
		levelCombo.setItems(levelsView);
		SysData data = new SysData();
		ArrayList<QuestionsForWizardTable> questionsForTable = new ArrayList<>();
		for(Question q : data.questionsDB) {
			questionsForTable.add(new QuestionsForWizardTable(q));
		}
		questionsList.addAll(questionsForTable);
		questionsTable.setItems(questionsList);
		questionsTable.setEditable(true);

	}

	public void clear() {
		questionField.clear();
		authorField.clear();
		correctField.clear();
		option1Field.clear();
		option2Field.clear();
		option3Field.clear();
	}

	public void editQuestionEvent(CellEditEvent<QuestionsForWizardTable, String> cell) {
		System.out.println("Edit");
		oldQuestion = questionsTable.getSelectionModel().getSelectedItem();
		newQuestion = questionsTable.getSelectionModel().getSelectedItem();
		newQuestion.setQuestion(cell.getNewValue().toString());
		controller.editQuestion(oldQuestion.createQuestion(), newQuestion.createQuestion());
		System.out.println(oldQuestion.createQuestion());
		System.out.println(newQuestion.createQuestion());
	}



}
