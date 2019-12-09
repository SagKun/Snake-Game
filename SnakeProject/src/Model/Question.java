package Model;

import java.util.ArrayList;

public class Question extends SnakeFood{
	
	//defines if it is an easy, medium or hard level question
	private Level level;
	//how many points the player will lose if he answer wrong
	private int reducePoints;
	//all the 4 answers
	private ArrayList<String> answers = new ArrayList<String>();
	//the question the player needs to answer
	private String Question;
	//the person that wrote the question
	private String author;
	//the correct answer
	private String correct_ans;
	

	
	public Question(int x,int y,String question, Level level, ArrayList<String> answers, String correct_ans,String team) {
		super(x,y, level);
		Question = question;
		this.level = level;
		this.author = team;
		this.answers = answers;
		this.correct_ans = correct_ans;
		this.reducePoints = calculateReductionSize(level);
	}
	
	public Question(int foodX, int foodY, Level valueOf) {
		// TODO Auto-generated constructor stub
		super(foodX,foodY,valueOf);	
	}

	private int calculateReductionSize(Level level) {
		if(level == Level.EASY) {
			return -10;
		}
		else if(level == Level.MODERATE) {

			return -20;
		}
		else {
			return -30;
		}
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getSetBack() {
		return reducePoints;
	}

	public void setSetBack(int setBack) {
		this.reducePoints = setBack;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}

	public String getQuestion() {
		return Question;
	}

	public void setQuestion(String question) {
		Question = question;
	}

	public String getTeam() {
		return author;
	}

	public void setTeam(String team) {
		this.author = team;
	}

	public String getCorrect_ans() {
		return correct_ans;
	}

	public void setCorrect_ans(String correct_ans) {
		this.correct_ans = correct_ans;
	}



	
	

	@Override
	public String toString() {
		return "Question [level=" + level + ", setBack=" + reducePoints + ", answers=" + answers + ", Question=" + Question
				+ ", team=" + author + ", correct_ans=" + correct_ans + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (Question == null) {
			if (other.Question != null)
				return false;
		} else if (!Question.equals(other.Question))
			return false;
		return true;
	}
	
	



	
	
	
	
	
	


}
