package Model;

import java.util.ArrayList;

import sun.misc.Queue;

public class FoodFactory {
	private java.util.Queue<Question> easyQuestions;
	private java.util.Queue<Question> moderateQuestions;
	private java.util.Queue<Question> hardQuestions;
	
	
	public FoodFactory() {
		easyQuestions = SysData.questionsByLevelDB.get(Level.EASY);
		moderateQuestions = SysData.questionsByLevelDB.get(Level.MODERATE);
		hardQuestions = SysData.questionsByLevelDB.get(Level.HARD);
	}

	//returns a food based on the type you requested with his correct values to fields
	public SnakeFood getFood(FoodType type, int x, int y) {
		if(type.equals(FoodType.Banana))
			return new SnakeFood(x, y, type, 15, 10, 1, 0);
		else if(type.equals(FoodType.Apple))
			return new SnakeFood(x,y, type, 10, 5, 1, 0);
		else if(type.equals(FoodType.Pear))
			return new SnakeFood(x, y, type, 20, 0, 1, 0);
		else if(type.equals(FoodType.Mouse)) 
			return new Mouse(x,y,type, 30, 60, 2, 1);
		return null;
	}
	//returns a question based on the level you requested with his correct values to fields
	public Question getQuestion(Level level, int x, int y, String question, ArrayList<String> answers, int correct_ans,String team) {
		if(level.equals(Level.EASY))
			return new Question(x, y, question, 1, level, answers, correct_ans, team, -10);
		else if(level.equals(Level.MODERATE))
			return new Question(x, y, question, 2, level, answers, correct_ans, team, -20);	
		else if(level.equals(Level.HARD))
			return new Question(x, y, question, 3, level, answers, correct_ans, team, -30);
		return null;
	}
	//returns a question based on the level you requested with his correct values to fields
	public Question getQuestion(Level level) {
		if(level.equals(Level.EASY) && !easyQuestions.isEmpty())
			return easyQuestions.poll();
		else if(level.equals(Level.MODERATE) && !moderateQuestions.isEmpty())
			return moderateQuestions.poll();
		else if(level.equals(Level.HARD) && !hardQuestions.isEmpty())
			return hardQuestions.poll();
		return null;
	}
}
