package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Model.Board;
import Model.FoodFactory;
import Model.FoodType;
import Model.GameState;
import Model.Question;
import Model.SnakeFood;
import Model.SysData;

public class TestBox {
	
	public static FoodType type = FoodType.Banana;

	//checks if the factory creats object successfully and the object values are correct
	@Test
	public void testSetObjects() {
		FoodFactory factory = new FoodFactory();
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
		assertTrue(result);
	}
	
	//checks if the database saves the highscores successfully
	@Test
	public void testShowSavedPlayers() {
		SysData.loadHighScores();
		boolean result = false;
		if(!SysData.highScores.isEmpty())
			result = true;
		assertTrue(result);
	}
	
	//checks what happens when the snake collide with the borders
	@Test
	public void testCheckCollisionWithBorders() {
		Board board = new Board();
		board.getSnake().getHead().setX(-1);
		board.getSnake().getHead().setY(5);
		GameState state = board.checkCollision();
		boolean result = false;
		System.out.println(state);
		if(state.equals(GameState.Finished))
			result = true;
		assertTrue(result);
		
	}
	
	// checks if the player get the rewards of eat a food object
	@Test
	public void testCheckEaten() {
		Board board = new Board();
		int prevScore = board.getScore();
		int prevSnakeLength = board.getSnake().getSize();
		FoodFactory factory = new FoodFactory();
		SnakeFood banana = factory.getFood(FoodType.Banana, 5, 5);
		board.getSnake().getHead().setX(5);
		board.getSnake().getHead().setY(5);
		board.getObjectList().add(banana);
		board.checkEaten();
		boolean result = false;
		if(board.getScore() == prevScore+banana.getPoints() && board.getSnake().getSize() == prevSnakeLength+banana.getExtraLength())
			result = true;
		assertTrue(result);
	}
	
	//checks if the system import JSON file successfully
	@Test
	public void checkJSON()	{
		ArrayList<Question> testQuestionDB = new ArrayList<Question>();
		SysData.readQuestions();
		testQuestionDB = SysData.questionsDB;
		boolean result = false;
		if(testQuestionDB.toString() != "[]")
			result = true;
		assertTrue(result);
	}
	
	

}
