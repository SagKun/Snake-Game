package Controller;

import java.util.ArrayList;
import java.util.Collections;

import Model.Player;
import Model.Question;
import Model.SysData;

public class HistoryController {
	
	private ArrayList<Player> scores;

	//loading the scores and sort it in descending order(the best highscore is in 0)
	public HistoryController() {
		super();
		this.scores = SysData.highScores;
		Collections.sort(scores);
	}
	
	/*takes a score,checks if it is larger than the minimal score,if it is then the method finds the first player with a larger score and inserts the current player one index below it 
	 and shifts all the bigger indexes right,then it is sorted again and the 11th player is removed.
	lastly, the highscores database is updated(in sysdata)  */
	public void addScoreIfTopTen(Player p)
	{
		int index=10;
		if(p.getScore() < scores.get(scores.size()-1).getScore())
		{
			System.out.println("The score is not high enough to be save in Top Ten scores.");
		}
		else
		{
			
			for(int i=9;i>-1;i--)
			{
				if(scores.get(i).getScore()<=p.getScore() )
					index=i;
			}
			scores.add(index, p);
			System.out.println("the new highscore position is: "+ index+1);
			Collections.sort(scores);
			scores.remove(10);
			SysData.highScores=scores;
			SysData.saveHighScores();
		}
		
	}
	
	public void resetHighScores() {
		SysData.highScores = null;
		SysData.saveHighScores();
		System.out.println("The scores has been reset");
	}
	
	public ArrayList<Player> getHighScores(){
		SysData.loadHighScores();
		return SysData.highScores;
	}
	
	
	
	
	
}
