package Model;

import java.util.ArrayList;

public class Question extends SnakeFood{
	
	//defines if it is an easy, medium or hard level question
	private ColorLevel level;
	//how many points the player will lose if he answer wrong
	private int setBack;
	//all the 4 answers
	private ArrayList<String> answers = new ArrayList<String>(4);
	//the right answer
	private String rightAnswar;
	
	
	
	public Question(int x, int y, FoodType type, int points, int secondsBuffer, int extraLength,int extraLife, ColorLevel level,
			int setBack, ArrayList<String> answers, String rightAnswar) {
		super(x, y, type, points, secondsBuffer, extraLength, extraLife);
		this.level = level;
		this.setBack = setBack;
		this.answers = answers;
		this.rightAnswar = rightAnswar;
	}



	public ColorLevel getLevel() {
		return level;
	}



	public void setLevel(ColorLevel level) {
		this.level = level;
	}



	public int getSetBack() {
		return setBack;
	}



	public void setSetBack(int setBack) {
		this.setBack = setBack;
	}



	public ArrayList<String> getAnswers() {
		return answers;
	}



	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}



	public String getRightAnswar() {
		return rightAnswar;
	}



	public void setRightAnswar(String rightAnswar) {
		this.rightAnswar = rightAnswar;
	}



	@Override
	public String toString() {
		return "Question [level=" + level + ", setBack=" + setBack + "]";
	}
	
	
	
	
	
	


}
