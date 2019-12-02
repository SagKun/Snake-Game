package Model;


import Model.FoodType;
import Model.GameObject;

import javafx.scene.paint.Color;

//
public class SnakeFood extends GameObject {
	/**
	 * Color of normal fruit
	 */
	// public static final Color FRUIT_COLOR; TBD maybe an image
	
	//the type of the fruit
	private FoodType type;
	//how many points the player would get for eating this food
	private int points;
	//how much seconds it will take another food of this type to apear in the board
	private int secondsBuffer;
	//how many units to length the snake will get after eating this food
	private int extraLength;
	//how many lifes to add to the player
	private int extraLife;
	
	
	
	public SnakeFood(int x, int y, FoodType type, int points, int secondsBuffer, int extraLength, int extraLife) {
		super(x, y);
		this.type = type;
		this.points = points;
		this.secondsBuffer = secondsBuffer;
		this.extraLength = extraLength;
		this.extraLife = extraLife;
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



	public void setPoints(int points) {
		this.points = points;
	}



	public int getSecondsBuffer() {
		return secondsBuffer;
	}



	public void setSecondsBuffer(int secondsBuffer) {
		this.secondsBuffer = secondsBuffer;
	}



	public int getExtraLength() {
		return extraLength;
	}



	public void setExtraLength(int extraLength) {
		this.extraLength = extraLength;
	}
	
	

	public int getExtraLife() {
		return extraLife;
	}



	public void setExtraLife(int extraLife) {
		this.extraLife = extraLife;
	}



	@Override
	public String toString() {
		return "SnakeFood [type=" + type + ", points=" + points + ", secondsBuffer=" + secondsBuffer + ", extraLength="
				+ extraLength + "]";
	}
	
	

}
