package View;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import Controller.HistoryController;
import Model.Player;
import Model.SysData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class HistoryView implements Initializable {

	private String pathFxml = "SnakeProject\\src\\View\\HistoryView.fxml";

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
	private Button reset;

	@FXML
	private Button back;

	private HistoryController controller = new HistoryController();

	private ObservableList<PlayersForTable> playersView = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(SysData.highScores != null) {
			nickname.setCellValueFactory(new PropertyValueFactory<PlayersForTable, String>("nickForTable"));
			score.setCellValueFactory(new PropertyValueFactory<PlayersForTable, Integer>("scoreForTable"));
			date.setCellValueFactory(new PropertyValueFactory<PlayersForTable, String>("dateForTable"));
			ArrayList<PlayersForTable> players = new ArrayList<>();
			for(Player p : SysData.highScores) {
				players.add(new PlayersForTable(p));
			}
			playersView.addAll(players);
			table.setItems(playersView);
		}
	}

	@FXML
	void resetScores(ActionEvent event) {
		Alert al = new Alert(Alert.AlertType.CONFIRMATION);
		al.setHeaderText("Are You Sure You Want To Reset The Scores?");
		al.setTitle("Reset Scores");
		al.setResizable(false);
		Optional<ButtonType> result = al.showAndWait();
		if(result.get() == ButtonType.OK) {
			controller.resetHighScores();
			initialize(null, null);
		}
	}

	@FXML
	void returnToMenu(ActionEvent event) {

	}

}