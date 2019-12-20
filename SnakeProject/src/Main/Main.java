package Main;

import java.io.File;
import java.net.URL;


import Model.SysData;
import View.GameView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{

	
	
	public static void main(String args[]) {
		//SysData.InitializeGame();
		launch(args);

	}
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/GameView.fxml"));

		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		GameView view = (GameView)loader.getController();		
		view.setStage(stage);
		view.resume();
		stage.setResizable(false);
		stage.initStyle(StageStyle.DECORATED);
		stage.show();
	}


}
