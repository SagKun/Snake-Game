package View;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Controller.HistoryController;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Nickname implements Initializable{

    @FXML
    private TextField nickName;

    @FXML
    private Text score;

    @FXML
    private JFXButton returnMenu;

    @FXML
    private JFXButton newGame;

    @FXML
    private JFXButton submit;

    @FXML
    void newGameBtn(ActionEvent event) {
    	
    }

    @FXML
    void returnToMenuBtn(ActionEvent event) {

    }

    @FXML
    void submitBtn(ActionEvent event) {
    	if(nickName.getText().equals("")) {
    		Alert al = new Alert(Alert.AlertType.ERROR);
    		al.setHeaderText("Nickname Is Not Set");
    		al.setTitle("System Messege");
    		al.setResizable(false);
    	}
    	else
    	{
    		Player p = new Player(nickName.getText(), Integer.parseInt(score.getText()));
    		HistoryController history = new HistoryController();
    		history.addScoreIfTopTen(p);
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
