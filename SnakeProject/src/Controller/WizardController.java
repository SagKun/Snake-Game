package Controller;

import Model.Question;
import Model.SysData;

public class WizardController {

	public void editQuestion(Question originalQuestion,Question newQuestion)
	{
		System.out.println(originalQuestion);
		System.out.println(newQuestion);
		SysData.editQuestion(originalQuestion, newQuestion);
		System.out.println(originalQuestion.getQuestion()+ " was edited successfully.");
		SysData.writeToFile();
	}
	
	
	public void addQuestion(Question q)
	{
		System.out.println(q);
		SysData.addQuestion(q);
		System.out.println(q.getQuestion()+ " was added to the database.");
		SysData.writeToFile();
		
	}
	
	public void deleteQuestion(Question q)
	{
		System.out.println(q);
		SysData.removeQuestion(q);
		System.out.println(q.getQuestion()+ " was removed from the database.");		
		SysData.writeToFile();
	}
	
	
}
