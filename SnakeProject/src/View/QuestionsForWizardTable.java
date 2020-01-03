package View;

import java.util.ArrayList;

import Model.FoodFactory;
import Model.Level;
import Model.Question;

public class QuestionsForWizardTable {
	private String question;
	private String level;
	private String author;
	private String option1;
	private String option2;
	private String option3;
	private String correctAnswer;
	
	public QuestionsForWizardTable(Question q) {
		super();
		this.question = q.getQuestion();
		this.level = q.getLevel().toString();
		this.author = q.getAuthor();
		this.correctAnswer = q.getCorrect_ans();
		ArrayList<String> wrongAnswers = new ArrayList<>();
		for(int i = 0; i < 4; i++) {
			if(q.getAnswers().get(i) != correctAnswer) {
				wrongAnswers.add(q.getAnswers().get(i));
			}
		}
		option1 = wrongAnswers.get(0);
		option2 = wrongAnswers.get(1);
		option3 = wrongAnswers.get(2);
	}
	
	public Question createQuestion() {
		FoodFactory factory = new FoodFactory();
		ArrayList<String> answers = new ArrayList<>();
		answers.add(correctAnswer);
		answers.add(option1);
		answers.add(option2);
		answers.add(option3);
		Level l;
		if(level.equals("HARD"))
			l = Level.HARD;
		else if(level.equals("EASY"))
			l = Level.EASY;
		else
			l = Level.MODERATE;
		Question q = factory.getQuestion(l, 0, 0, question,
				answers, correctAnswer, author);
		return q;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String options3) {
		this.option3 = options3;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	
	
	
	
}
