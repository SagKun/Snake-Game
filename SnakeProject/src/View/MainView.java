package View;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Utils.Fonts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class MainView implements Initializable{
	
	
	 @FXML
	    private AnchorPane mainView;

	    @FXML
	    private JFXButton play;

	    @FXML
	    private JFXButton highscores;

	    @FXML
	    private JFXButton questions;
	    
	    @FXML
	    private JFXButton exit;
		private Stage stage;

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			play.setFont(Fonts.minecraft);
			highscores.setFont(Fonts.minecraft);
			questions.setFont(Fonts.minecraft);
			exit.setFont(Fonts.minecraft);
			
		}
		
		
	    public void play(ActionEvent event) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
				StackPane pane  = loader.load();
				pane.setPrefSize(mainView.getWidth(), mainView.getHeight());
				mainView.getChildren().removeAll(mainView.getChildren());
				mainView.getChildren().add(pane);
				
				GameView view = (GameView)loader.getController();		
				view.setStage((Stage)pane.getScene().getWindow());
				view.resume();			
			}
			
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		public Stage getStage() {
			return stage;
		}

		public void setStage(Stage stage) {
			this.stage = stage;
		}
	
		public void addShadowOnHover()
		{
		//Adding the shadow when the mouse cursor is on
			DropShadow shadow = new DropShadow();
			play.setEffect(shadow);
		}
		
	
}
