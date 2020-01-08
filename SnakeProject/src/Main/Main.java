package Main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import Model.SysData;
import Utils.Fonts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{

	
	public static File f = new File("checkFile");

	public static void main(String args[]) {
		
		Fonts.loadMinecraftFont();
		if(NoOtherInstanceRunning(f))
		{	
			SysData.InitializeGame();
			launch(args);
			f.deleteOnExit();
		}

	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainView.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.DECORATED);
		stage.show();
	}

	public static boolean NoOtherInstanceRunning(File f) {       


		if (!f.exists()) {
			try {
				f.createNewFile();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		} else {
			System.out.println("Snake is already running, can't run again!" );
			return false;
		}


	}
	
	
	

}
