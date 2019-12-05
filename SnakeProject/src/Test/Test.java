package Test;

import java.util.ArrayList;

import Model.ColorLevel;
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
		System.out.println("Setting Food: ");
		System.out.println("Creating Banana");
		SnakeFood banana = new SnakeFood(0, 0, FoodType.Banana);
		System.out.println(banana);
		System.out.println("Creating Pear");
		SnakeFood pear = new SnakeFood(0, 0, FoodType.Pear);
		System.out.println(pear);
		System.out.println("Creating Apple");
		SnakeFood apple = new SnakeFood(0, 0, FoodType.Apple);
		System.out.println(apple);
		System.out.println("Creating Mouse");
		SnakeFood mouse = new SnakeFood(0, 0, FoodType.Mouse);
		System.out.println(mouse);
		System.out.println("Setting Questions: ");
		System.out.println("Creating Easy Question");
		Question easy = new Question(0, 0, null, ColorLevel.EASY, null, null, null);
		System.out.println(easy);
		System.out.println("Creating Moderate Question");
		Question med = new Question(0, 0, null, ColorLevel.MODERATE, null, null, null);
		System.out.println(med);
		System.out.println("Creating Hard Question");
		Question hard = new Question(0, 0, null, ColorLevel.HARD, null, null, null);
		System.out.println(hard);
		System.out.println("Finished");
	}
	
	public static void setSnake() {
		Snake s = new Snake();
		System.out.println("Creating Snake:");
		System.out.println("The snake size is: "+s.getSize());
	}
}
