package Model;

//This class will have data on the player nickname and score in a game
public class Player {
	//The nickname the player picked
	private String nickname;
	//the score that the player achieved in the game
	private int score;
	
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
	
	
}
