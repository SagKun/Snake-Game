package View;

import java.util.Calendar;

import Model.Player;

// a class that describe a record in the high score table
public class PlayersForTable {
	
	private String nickForTable;
	private Integer scoreForTable;
	private String dateForTable;
	
	
	public PlayersForTable(Player p) {
		super();
		this.nickForTable = p.getNickname();
		this.scoreForTable = p.getScore();
		Calendar cal = Calendar.getInstance();
		this.dateForTable = cal.get(Calendar.DAY_OF_MONTH)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
	}
	
	public String getNickForTable() {
		return nickForTable;
	}
	public void setNickForTable(String nickForTable) {
		this.nickForTable = nickForTable;
	}

	public String getDateForTable() {
		return dateForTable;
	}
	public void setDateForTable(String dateForTable) {
		this.dateForTable = dateForTable;
	}
	
	

	public Integer getScoreForTable() {
		return scoreForTable;
	}

	public void setScoreForTable(Integer scoreForTable) {
		this.scoreForTable = scoreForTable;
	}

	@Override
	public String toString() {
		return "PlayersForTable [nickForTable=" + nickForTable + ", scoreForTable=" + scoreForTable + ", dateForTable="
				+ dateForTable + "]";
	}
	
	
}
