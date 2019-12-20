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
		SysData.InitializeGame();
		System.out.println(SysData.questionsByLevelDB.get(Model.Level.EASY));
		System.out.println(SysData.questionsByLevelDB.get(Model.Level.MODERATE));
		System.out.println(SysData.questionsByLevelDB.get(Model.Level.HARD));
		launch(args);
		GameView g=new GameView();

	}
	
	@Override
	public void start(Stage stage) throws Exception {
		URL url = new File("src/View/GameView.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.DECORATED);
		stage.show();
		
	}
}
