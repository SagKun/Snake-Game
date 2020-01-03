package View;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Controller.WizardController;
import Model.FoodFactory;
import Model.Level;
import Model.Question;
import Model.SysData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
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
	private JFXButton resumeBtn;

	@FXML
	private JFXButton removeBtn;

	@FXML
	private JFXButton editBtn;

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
	void resumeToMenu(ActionEvent event) {

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
		// setting editable
		questionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		levelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		option1Column.setCellFactory(TextFieldTableCell.forTableColumn());
		option2Column.setCellFactory(TextFieldTableCell.forTableColumn());
		option3Column.setCellFactory(TextFieldTableCell.forTableColumn());
		correctColumn.setCellFactory(TextFieldTableCell.forTableColumn());

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

}
