package Model;

import java.util.ArrayList;

public class Question extends SnakeFood{
	
	//defines if it is an easy, medium or hard level question
	private ColorLevel level;
	//how many points the player will lose if he answer wrong
	private int setBack;
	//all the 4 answers
	private ArrayList<String> answers = new ArrayList<String>(4);
	//the question the player needs to answer
	private String Question;
	//the team that wrote the question
	private String team;
	//the correct answer
	private String correct_ans;
	

	
	public Question(int x,int y,String question, ColorLevel level, ArrayList<String> answers, String correct_ans,String team) {
		super(x,y, level);
		Question = question;
		this.level = level;
		this.team = team;
		this.answers = answers;
		this.correct_ans = correct_ans;
		if(level == ColorLevel.EASY) {
			setBack = -10;
		}
		else if(level == ColorLevel.MODERATE) {

			setBack = -20;
		}
		else if(level == ColorLevel.HARD) {
			setBack = -30;
		}
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

	public String getQuestion() {
		return Question;
	}

	public void setQuestion(String question) {
		Question = question;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getCorrect_ans() {
		return correct_ans;
	}

	public void setCorrect_ans(String correct_ans) {
		this.correct_ans = correct_ans;
	}



	@Override
	public String toString() {
		return "Question [level=" + level + ", setBack=" + setBack + "]";
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
