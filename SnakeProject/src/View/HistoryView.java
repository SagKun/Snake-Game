package View;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Controller.HistoryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class HistoryView {
	
    @FXML
    private AnchorPane anchor;

    @FXML
    private TableView<?> table;

    @FXML
    private Button reset;

    @FXML
    private Button back;
    
    private HistoryController controller = new HistoryController();
    
    
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

    @FXML
    void resetScores(ActionEvent event) {
		Alert al = new Alert(Alert.AlertType.WARNING);
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