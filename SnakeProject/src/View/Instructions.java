package View;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.FoodType;
import Model.Level;
import Utils.Fonts;
import animatefx.animation.Pulse;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Instructions implements Initializable  {


	@FXML
	private Label instructions;

	@FXML
	private Label returnBtn;
	@FXML
	private AnchorPane pauseAnchor;

	@FXML
	private TableView<FoodTableForInstructions> foodTable;

	@FXML
	private TableColumn<FoodTableForInstructions, ImageView> food;

	@FXML
	private TableColumn<FoodTableForInstructions, Integer> foodPoints;

	@FXML
	private TableColumn<FoodTableForInstructions, Integer>  lengthAdd;

	@FXML
	private TableColumn<FoodTableForInstructions, Integer>  lifeAdd;

	@FXML
	private TableView<QuestionsTableInstructions> questionTable;

	@FXML
	private TableColumn<QuestionsTableInstructions, ImageView> question;

	@FXML
	private TableColumn<QuestionsTableInstructions, String> level;

	@FXML
	private TableColumn<QuestionsTableInstructions, Integer> questionPoints;

	@FXML
	private TableColumn<QuestionsTableInstructions, Integer> penalty;

	

	private ObservableList<FoodTableForInstructions> foodList = FXCollections.observableArrayList();

	private ObservableList<QuestionsTableInstructions> qList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		returnBtn.setFont(Fonts.minecraft30);
		instructions.setFont(Fonts.minecraft50);
		 new Pulse(returnBtn).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
		 new Pulse(instructions).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
		food.setCellValueFactory(new PropertyValueFactory<FoodTableForInstructions, ImageView>("image"));
		foodPoints.setCellValueFactory(new PropertyValueFactory<FoodTableForInstructions, Integer>("foodPoints"));
		lengthAdd.setCellValueFactory(new PropertyValueFactory<FoodTableForInstructions, Integer>("extraLength"));
		lifeAdd.setCellValueFactory(new PropertyValueFactory<FoodTableForInstructions, Integer>("extraLife"));
		ArrayList<FoodTableForInstructions> foods = new ArrayList<>();
		for(FoodType f : FoodType.values()) {
			foods.add(new FoodTableForInstructions(f));
		}
		foodList.addAll(foods);
		foodTable.setItems(foodList);
		question.setCellValueFactory(new PropertyValueFactory<QuestionsTableInstructions, ImageView>("image"));
		level.setCellValueFactory(new PropertyValueFactory<QuestionsTableInstructions, String>("level"));
		questionPoints.setCellValueFactory(new PropertyValueFactory<QuestionsTableInstructions, Integer>("points"));
		penalty.setCellValueFactory(new PropertyValueFactory<QuestionsTableInstructions, Integer>("penalty"));
		ArrayList<QuestionsTableInstructions> questions = new ArrayList<>();
		for(Level l : Level.values()) {
			questions.add(new QuestionsTableInstructions(l));
		}
		qList.addAll(questions);
		questionTable.setItems(qList);
	}

	@FXML
	void returnToMenu(MouseEvent event) {

	}

}
