package View;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Utils.Fonts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GameOver implements Initializable{
	  @FXML
	    private AnchorPane pauseAnchor;

	    @FXML
	    private Label gameOver;

	    @FXML
	    private JFXButton newGame;

	    @FXML
	    private JFXButton menu;

	    @FXML
	    private JFXButton scores;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gameOver.setFont(Fonts.minecraft);
		
	}

}
