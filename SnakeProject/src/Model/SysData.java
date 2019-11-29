package Model;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;



public class SysData{

	private ArrayList<Question> importedQuestions;
	//The data structure that saves the high scores
	public static ArrayList<Player> highScores = new ArrayList<Player>();
	//The name of the file we write the high scores to
	public static final String fileName = "highScores.ser";
	
	//a method to save the high scores to a file
	public static boolean saveHighScores() {
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(highScores);
			objectOut.close();
			fileOut.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	//a method to load the high scores from a file
	public static void loadHighScores() {
		highScores = null;
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream obIn = new ObjectInputStream(fileIn);
			highScores = (ArrayList<Player>)obIn.readObject();
			obIn.close();
			fileIn.close();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			try {
				FileOutputStream fileOut = new FileOutputStream(fileName);
				ObjectOutputStream obOut = new ObjectOutputStream(fileOut);
				obOut.writeObject(highScores);
				obOut.close();
				fileOut.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

}
