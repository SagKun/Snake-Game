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



	public SnakeFood(int x, int y, FoodType type) {
		super(x, y);
		this.type = type;
		if(type == FoodType.Banana) {
			points = 15;
			secondsBuffer = 10;
			extraLength = 1;
			extraLife = 0;
		}
		else if(type == FoodType.Apple) {
			points = 10;
			secondsBuffer = 5;
			extraLength = 1;
			extraLife = 0;
		}
		else if(type == FoodType.Pear) {
			points = 20;
			secondsBuffer = 0;
			extraLength = 1;
			extraLife = 0;
		}
		else if(type == FoodType.Mouse) {
			points = 30;
			secondsBuffer = 60;
			extraLength = 2;
			extraLife = 1;
		}
		else {
			points = 0;
			secondsBuffer = 0;
			extraLength = 0;
			extraLife = 0;
		}
			
	}
	
	

	public SnakeFood(int x,int y, Level level)
	{
		super(x, y);
		secondsBuffer = 0;
		extraLength = 0;
		extraLife = 0;
		if(level == Level.EASY) {
			points = 1;
		}
		else if(level == Level.MODERATE) {
			points = 2;
		}
		else if(level == Level.HARD) {
			points = 3;
		}
		else
			points = 0;
			
		
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
