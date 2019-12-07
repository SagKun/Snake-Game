package Model;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class SysData{

	public static ArrayList<Question> questionsDB=new ArrayList<Question>();
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

	//reads the question.json file
	/*important! this should be called only in system startup
	 * (this method clears the importedQuestions list and initializes it again with the values from the json file)
	*/
	@SuppressWarnings("unchecked")
	public static void readQuestions() {
		questionsDB.clear();
		try (Reader reader = new FileReader("src\\Data\\Questions.json")) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			JSONArray jsonArray = (JSONArray) jsonObject.get("questions");
			jsonArray.forEach(question -> parseQuestion( (JSONObject) question ));
			System.out.println("All questions were read from file");
			reader.close();
			}
			

		 catch (IOException e) {
			e.printStackTrace();
		 } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//parses each json object from the file to a question object
	
	public static void parseQuestion(JSONObject jsonQuestion) {
		ArrayList<String> answers=new ArrayList<String>();
		String question = (String) jsonQuestion.get("question"); 
		JSONArray jsonArray = (JSONArray) jsonQuestion.get("answers");
		for (Object rawAnswer : jsonArray) {
			answers.add((String) rawAnswer);
		}
		
		String correct_ans  = (String) jsonQuestion.get("correct_ans");
		String level = (String) jsonQuestion.get("level");
		String team = (String) jsonQuestion.get("team");
	Question q= new Question(0,0,question,ColorLevel.getLevel(Integer.parseInt(level)),answers,correct_ans,team);
	questionsDB.add(q);
	System.out.println(q.getQuestion()+ " was imported successfully.");
	}
	
	
	//saves the question object to json file
	//***********currently the file name is different from the original file until this will be tested properly*********
	@SuppressWarnings("unchecked")
	public static void writeToFile()
	{
		JSONObject fullObject = new JSONObject();
		JSONArray list = new JSONArray();
		for (Question q : questionsDB) {
			JSONObject question = new JSONObject();
			question.put("question",q.getQuestion());
			JSONArray answers = new JSONArray();
			for (String ans: q.getAnswers()) {
				answers.add(ans);
			}
			question.put("answers",answers);
			question.put("correct_ans",q.getCorrect_ans());
			question.put("level",Integer.toString(ColorLevel.getValue(q.getLevel())));
			question.put("team",q.getTeam());
			list.add(question);
		}
		fullObject.put("questions", list);
		
		try (FileWriter file = new FileWriter("src\\Data\\Questions.json")) {
			file.write(fullObject.toJSONString());
			System.out.println("all data was written to file successfully.");
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void  addQuestion(Question q)
	{
		questionsDB.add(q);
	}
	
	public static void  removeQuestion(Question q)
	{
		questionsDB.remove(q);
	}
	
	public static void editQuestion(Question ogQuestion,Question newQuestion)
	{
		int index=questionsDB.indexOf(ogQuestion);
		questionsDB.remove(ogQuestion);
		questionsDB.add(index, newQuestion);
		
	}
	

}
