package Controller;

import Model.Question;
import Model.SysData;

public class WizardController {

	public void editQuestion(Question originalQuestion,Question newQuestion)
	{
		
		SysData.editQuestion(originalQuestion, newQuestion);
		System.out.println(originalQuestion.getQuestion()+ " was edited successfully.");
		
	}
	
	
	public void addQuestion(Question q)
	{
		SysData.addQuestion(q);
		System.out.println(q.getQuestion()+ " was added to the database.");
		
	}
	
	public void deleteQuestion(Question q)
	{
		SysData.removeQuestion(q);
		System.out.println(q.getQuestion()+ " was removed from the database.");		
	}
	
	
}
