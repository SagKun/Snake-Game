package View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import Controller.HistoryController;
import Model.Player;
import Model.SysData;
import Utils.Fonts;
import animatefx.animation.Pulse;
import animatefx.animation.RubberBand;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HistoryView implements Initializable {


	@FXML
	private StackPane stackPane;
	@FXML
	private AnchorPane anchor;

	@FXML
	private TableView<PlayersForTable> table;

	@FXML
	private TableColumn<PlayersForTable, String> nickname;

	@FXML
	private TableColumn<PlayersForTable, Integer> score;

	@FXML
	private TableColumn<PlayersForTable, String> date;

	@FXML
	private Label reset;

	@FXML
	private Label back;

	private HistoryController controller = new HistoryController();

	private ObservableList<PlayersForTable> playersView = FXCollections.observableArrayList();

	private ArrayList<PlayersForTable> players = new ArrayList<>();

	//a method that initialize the table
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(SysData.highScores != null) {
			nickname.setCellValueFactory(new PropertyValueFactory<PlayersForTable, String>("nickForTable"));
			score.setCellValueFactory(new PropertyValueFactory<PlayersForTable, Integer>("scoreForTable"));
			date.setCellValueFactory(new PropertyValueFactory<PlayersForTable, String>("dateForTable"));
			for(Player p : SysData.highScores) {
				players.add(new PlayersForTable(p));
			}
			playersView.addAll(players);
			table.setItems(playersView);
			reset.setFont(Fonts.minecraft30);
			back.setFont(Fonts.minecraft30);
			new Pulse(reset).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
			new Pulse(back).setCycleCount(Timeline.INDEFINITE).setSpeed(1).play();
		}
	}
	// a method that reset the scores table
	@FXML
	void resetScores(MouseEvent event) {
		new RubberBand(reset).setCycleCount(1).setSpeed(0.2).play();
				Alert al = new Alert(Alert.AlertType.CONFIRMATION);
				al.setHeaderText("Are You Sure You Want To Reset The Scores?");
				al.setTitle("Reset Scores");
				al.setResizable(false);
				Optional<ButtonType> result = al.showAndWait();
				if(result.get() == ButtonType.OK) {
					playersView.removeAll(players);
					controller.resetHighScores();
				}
				
		

	}
	
	

		@FXML
		void returnToMenu(MouseEvent event) {
			Stage stage=(Stage) stackPane.getScene().getWindow();
			new ZoomOut(back).setCycleCount(1).setSpeed(0.2).play();
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

	}