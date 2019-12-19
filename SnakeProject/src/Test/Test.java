package Test;

import java.util.ArrayList;

import Model.Level;
import Model.FoodFactory;
import Model.FoodType;
import Model.Player;
import Model.Question;
import Model.Snake;
import Model.SnakeFood;
import Model.SysData;

public class Test {
	

	
	public static void WriteAndReadTest()
	{
		ArrayList<Question> testQuestionDB=new ArrayList<Question>();
		SysData.readQuestions();
		System.out.println("1");
		testQuestionDB=SysData.questionsDB;
		System.out.println("2");
		SysData.writeToFile();
		System.out.println("3");
		SysData.readQuestions();
		System.out.println("4");
		System.out.println(testQuestionDB);
		System.out.println(SysData.questionsDB);
		if((testQuestionDB.toString().equals(SysData.questionsDB.toString())) && testQuestionDB.toString()!="[]")
		{
			System.out.println("The test Passed sucessfully,Import and Export questions json file is working properly.");
		}
		else
		System.out.println("xxxxxxxxxxxxxxx The test have failed!! xxxxxxxxxxxxxxxxx");
		
	}
	
	public static void topTenTest()
	{
		
	}
}
