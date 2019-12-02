package Model;

import java.io.Serializable;
import java.util.Calendar;

//This class will have data on the player nickname and score in a game
public class Player implements Comparable<Player>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//The nickname the player picked
	private String nickname;
	//the score that the player achieved in the game
	private Integer score;
	//The date which the player played this game
	private Calendar dateOfGame;
	
	public Player(String nickname, int score) {
		super();
		this.nickname = nickname;
		this.score = score;
		dateOfGame = Calendar.getInstance();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	

	public Calendar getDateOfGame() {
		return dateOfGame;
	}

	public void setDateOfGame(Calendar dateOfGame) {
		this.dateOfGame = dateOfGame;
	}

	//a method to compare between two players scores
	@Override
	public int compareTo(Player o) {
	return this.score.compareTo(o.score);
	}

	@Override
	public String toString() {
		return "Player [nickname=" + nickname + ", score=" + score + ", dateOfGame=" + dateOfGame + "]";
	}
	
	
	
	
}
