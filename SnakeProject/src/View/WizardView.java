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

public class WizardView implements Initializable{

	@FXML
	private TableView<Question> questionsTable;

	@FXML
	private TableColumn<Question, String> questionColumn;


	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Label resume;

	@FXML
	private Label removeBtn;

	@FXML
	private Label editBtn;

	@FXML
	private Label addBtn;
	@FXML
	private Label header;

	private ObservableList<Question> questionsList = FXCollections.observableArrayList();

	private WizardController controller = new WizardController();

	@FXML
	void addQuestion(MouseEvent event) {
		new Shake(addBtn).setCycleCount(1).setSpeed(1).play();
		AnchorPane pane;
		WizardForm.isEdit = false;
		try {
			pane = FXMLLoader.load(getClass().getResource("WizardForm.fxml"));
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
	void editQuestion(MouseEvent event) {
		new Shake(editBtn).setCycleCount(1).setSpeed(1).play();
		AnchorPane pane;
		WizardForm.isEdit = true;
		if(questionsTable.getSelectionModel().isEmpty()) {
			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setHeaderText("You Need To Pick An Answer!");
			al.setTitle("System Messege");
			al.setResizable(false);
			al.show();
		}
		WizardForm.oldQuestion = questionsTable.getSelectionModel().getSelectedItem();
		try {
			pane = FXMLLoader.load(getClass().getResource("WizardForm.fxml"));
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
	void removeQuestion(MouseEvent event) {
		new Shake(removeBtn).setCycleCount(1).setSpeed(1).play();
		if(questionsTable.getSelectionModel().isEmpty()) {
			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setHeaderText("You Need To Pick An Answer!");
			al.setTitle("System Messege");
			al.setResizable(false);
			al.show();
		}
		else {
			Alert al = new Alert(Alert.AlertType.CONFIRMATION);
			al.setHeaderText("Are You Sure You Want To Delete This Question?");
			al.setTitle("System Messege");
			al.setResizable(false);
			Optional<ButtonType> result = al.showAndWait();
			if(result.get() == ButtonType.OK) {
				int index = questionsTable.getSelectionModel().getSelectedIndex();
				Question q = questionsList.get(index);
				questionsList.remove(index);
				controller.deleteQuestion(q);
			}
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
		questionColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("question"));

		
		header.setFont(Fonts.minecraft50);
		new Pulse(resume).setCycleCount(Timeline.INDEFINITE).setSpeed(0.5).play();
		resume.setFont(Fonts.minecraft50);
		new Pulse(addBtn).setCycleCount(Timeline.INDEFINITE).setSpeed(0.5).play();
		addBtn.setFont(Fonts.minecraft30);
		new Pulse(editBtn).setCycleCount(Timeline.INDEFINITE).setSpeed(0.5).play();
		editBtn.setFont(Fonts.minecraft30);
		new Pulse(removeBtn).setCycleCount(Timeline.INDEFINITE).setSpeed(0.5).play();
		removeBtn.setFont(Fonts.minecraft30);
		questionsList.addAll(SysData.questionsDB);
		questionsTable.setItems(questionsList);
	}



}
