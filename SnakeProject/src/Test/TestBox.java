package Test;



import java.util.ArrayList;
import java.util.Random;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import Model.Board;
import Model.FoodFactory;
import Model.FoodType;
import Model.GameState;
import Model.Question;
import Model.SnakeFood;
import Model.SysData;

public class TestBox {
	
	public static FoodType type;
	public static int collisionX;
	public static int collisionY;
	public static int checkEatenX;
	public static int checkEatenY;
	
	// a method to prepare the data for tests
	@org.junit.jupiter.api.BeforeAll
	public static void prepare() {
		Random rand = new Random();
		int pick = rand.nextInt(4);
		switch(pick) {
		case 0:
			type = FoodType.Apple;
			break;
		case 1:
			type = FoodType.Banana;
			break;
		case 2:
			type = FoodType.Pear;
			break;
		case 3:
			type = FoodType.Mouse;
			break;
		default:
			type = FoodType.Apple;
		}
		collisionX = -1;
		collisionY = 5;
		checkEatenX = 300;
		checkEatenY = 100;
		
	}

	//checks if the factory creats object successfully and the object values are correct
	@Test
	public void testSetObjects() {
		FoodFactory factory = new FoodFactory();
		System.out.println(type);
		SnakeFood obj = factory.getFood(type, 0, 0);
		boolean result = false;
		if(type.equals(FoodType.Apple) && obj.getPoints() == 10 && obj.getSecondsBuffer() == 5 &&
				obj.getExtraLength() == 1 && obj.getExtraLife() == 0) {
				result = true;
			}
		else if(type.equals(FoodType.Banana) && obj.getPoints() == 15 && obj.getSecondsBuffer() == 10 &&
				obj.getExtraLength() == 1 && obj.getExtraLife() == 0) {
				result = true;
			}
		else if(type.equals(FoodType.Pear) && obj.getPoints() == 20 && obj.getSecondsBuffer() == 0 &&
				obj.getExtraLength() == 1 && obj.getExtraLife() == 0) {
				result = true;
			}
		else if(type.equals(FoodType.Mouse) && obj.getPoints() == 30 && obj.getSecondsBuffer() == 60 &&
				obj.getExtraLength() == 2 && obj.getExtraLife() == 1) {
				result = true;
			}
		Assert.assertTrue(result);
	}
	
	//checks if the database saves the highscores successfully
	@Test
	public void testShowSavedPlayers() {
		SysData.loadHighScores();
		Assert.assertFalse(SysData.highScores.isEmpty());
	}
	
	//checks what happens when the snake collide with the borders
	@Test
	public void testCheckCollisionWithBorders() {
		Board board = Board.getInstance();
		board.getSnake().getHead().setX(collisionX);
		board.getSnake().getHead().setY(collisionY);
		int prevLife = board.getLife();
		GameState state = board.checkCollision();
		boolean result = false;
		if(state.equals(GameState.Finished) && prevLife-1 == board.getLife())
			result = true;
		Assert.assertTrue(result);
		
	}
	
	// checks if the player get the rewards of eat a food object
	@Test
	public void testCheckEaten() {
		Board board = Board.getInstance();
		int prevScore = board.getScore();
		int prevSnakeLength = board.getSnake().getSize();
		board.getSnake().getHead().setX(checkEatenX);
		board.getSnake().getHead().setY(checkEatenY);
		board.initializeObjects();
		SnakeFood prevFood = board.getObjectList().get(0);
		board.getObjectList().get(0).setX(checkEatenX);
		board.getObjectList().get(0).setY(checkEatenY);
		board.checkEaten();
		boolean result = false;
		if(board.getScore() == prevScore+prevFood.getPoints() &&
				board.getSnake().getSize() == prevSnakeLength+prevFood.getExtraLength())
			result = true;
		Assert.assertTrue(result);
	}
	
	//checks if the system import JSON file successfully
	@Test
	public void checkJSON()	{
		SysData.InitializeGame();
		ArrayList<Question> testQuestionDB = new ArrayList<Question>();
		SysData.readQuestions();
		testQuestionDB = SysData.questionsDB;
		Assert.assertFalse(testQuestionDB.isEmpty());
	}
	

}
