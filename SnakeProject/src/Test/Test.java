package Test;

import java.util.ArrayList;

import Model.Level;
import Model.FoodFactory;
import Model.FoodType;
import Model.Player;
import Model.Question;
import Model.Snake;
import Model.SnakeFood;
import Model.SysData;

public class Test {
	
	public static void showSaveData() {
		SysData.loadHighScores();
		System.out.println("Showing current high scores saved: ");
		System.out.println(SysData.highScores);
		System.out.println("Creating new player: ");
		Player p = new Player("saghi", 250);
		System.out.println("Created New Player : "+p.getNickname());
		System.out.println("Saving the new score: ");
		if(SysData.saveHighScores())
			System.out.println("Saved high score successfully");
		else
			System.out.println("Saving high score failed");
		System.out.println("Removing all data from highscores data structure");
		SysData.highScores.clear();
		SysData.loadHighScores();
		System.out.println("Loading high Scores");
		System.out.println("High scores are: "+SysData.highScores);
	}
	
	public static void setObjects() {
		FoodFactory factory = new FoodFactory();
		System.out.println("Setting Food: ");
		System.out.println("Creating Banana");
		SnakeFood banana = factory.getFood(FoodType.Banana, 0, 0);
		System.out.println(banana);
		System.out.println("Creating Pear");
		SnakeFood pear = factory.getFood(FoodType.Pear, 0, 0);
		System.out.println(pear);
		System.out.println("Creating Apple");
		SnakeFood apple = factory.getFood(FoodType.Apple, 0, 0);
		System.out.println(apple);
		System.out.println("Creating Mouse");
		SnakeFood mouse = factory.getFood(FoodType.Mouse, 0, 0);
		System.out.println(mouse);
		System.out.println("Setting Questions: ");
		System.out.println("Creating Easy Question");
		Question easy = factory.getQuestion(Level.EASY, 0, 0, null, null, null, null);
		System.out.println(easy);
		System.out.println("Creating Moderate Question");
		Question med = factory.getQuestion(Level.MODERATE, 0, 0, null, null, null, null);
		System.out.println(med);
		System.out.println("Creating Hard Question");
		Question hard = factory.getQuestion(Level.HARD, 0, 0, null, null, null, null);
		System.out.println(hard);
		System.out.println("Finished");
	}
	
	public static void setSnake() {
		Snake s = new Snake();
		System.out.println("Creating Snake:");
		System.out.println("The snake size is: "+s.getSize());
	}
	
	public static void WriteAndReadTest()
	{
		ArrayList<Question> testQuestionDB=new ArrayList<Question>();
		SysData.readQuestions();
		System.out.println("1");
		testQuestionDB=SysData.questionsDB;
		System.out.println("2");
		SysData.writeToFile();
		System.out.println("3");
		SysData.readQuestions();
		System.out.println("4");
		System.out.println(testQuestionDB);
		System.out.println(SysData.questionsDB);
		if((testQuestionDB.toString().equals(SysData.questionsDB.toString())) && testQuestionDB.toString()!="[]")
		{
			System.out.println("The test Passed sucessfully,Import and Export questions json file is working properly.");
		}
		else
		System.out.println("xxxxxxxxxxxxxxx The test have failed!! xxxxxxxxxxxxxxxxx");
		
	}
	
	public static void topTenTest()
	{
		
	}
}
