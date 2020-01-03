package Model;


import Model.FoodType;
import Model.GameObject;

//
public class SnakeFood extends GameObject {
	/**
	 * Color of normal fruit
	 */
	// public static final Color FRUIT_COLOR; TBD maybe an image

	//the type of the fruit
	private FoodType type;
	//how many points the player would get for eating this food
	private final int points;
	//how much seconds it will take another food of this type to apear in the board
	private final int secondsBuffer;
	//how many units to length the snake will get after eating this food
	private final int extraLength;
	//how many lifes to add to the player
	private final int extraLife;



	public SnakeFood(int x, int y, FoodType type, int points, int secondsBuffer, int extraLength, int extraLife) {
		super(x, y);
		this.type = type;
		this.points = points;
		this.secondsBuffer = secondsBuffer;
		this.extraLength = extraLength;
		this.extraLife = extraLife;
	}
	
	

	public SnakeFood(int x,int y, int points)
	{
		super(x, y);
		secondsBuffer = 0;
		extraLength = 0;
		extraLife = 0;
		this.points = points;	
	}

	public FoodType getType() {
		return type;
	}



	public void setType(FoodType type) {
		this.type = type;
	}



	public int getPoints() {
		return points;
	}






	public int getSecondsBuffer() {
		return secondsBuffer;
	}






	public int getExtraLength() {
		return extraLength;
	}







	public int getExtraLife() {
		return extraLife;
	}







	@Override
	public String toString() {
		return "SnakeFood [type=" + type + ", points=" + points + ", secondsBuffer=" + secondsBuffer + ", extraLength="
				+ extraLength + "]";
	}



}
