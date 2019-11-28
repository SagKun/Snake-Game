package Model;

import java.io.Serializable;

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
	
	public Player(String nickname, int score) {
		super();
		this.nickname = nickname;
		this.score = score;
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

	//a method to compare between two players scores
	@Override
	public int compareTo(Player o) {
	return this.score.compareTo(o.score);
	}
	
	
	
	
}
