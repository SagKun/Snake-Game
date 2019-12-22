package View;



import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class MainView {
	
	
	 @FXML
	    private AnchorPane mainView;

	    @FXML
	    private Button play;

	    @FXML
	    private Button highscores;

	    @FXML
	    private Button questions;

	    @FXML
	    private Button exit;
		private Stage stage;

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
	

}
