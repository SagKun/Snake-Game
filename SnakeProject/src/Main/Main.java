package Main;

import View.GameView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static GameView view;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/GameView.fxml"));

		Parent root = loader.load();
		Scene scene = new Scene(root);

		GameView view = (GameView)loader.getController();
				
		stage.setScene(scene);
		
		view.setStage(stage);
		view.resume();
		stage.show();       
	}


}
