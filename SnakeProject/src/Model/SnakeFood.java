
import Model.FruitType;
import Model.GameObject;

import javafx.scene.paint.Color;

//
public class SnakeFood extends GameObject {
	/**
	 * Color of normal fruit
	 */
	// public static final Color FRUIT_COLOR; TBD maybe an image
	
	//the type of the fruit
	private FruitType type;
	//how many points the player would get for eating this food
	private int points;
	//how much seconds it will take another food of this type to apear in the board
	private int secondsBuffer;
	//how many units to length the snake will get after eating this food
	private int extraLength;
	
	
	
	public SnakeFood(int x, int y, FruitType type, int points, int secondsBuffer, int extraLength) {
		super(x, y);
		this.type = type;
		this.points = points;
		this.secondsBuffer = secondsBuffer;
		this.extraLength = extraLength;
	}



	public FruitType getType() {
		return type;
	}



	public void setType(FruitType type) {
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
	
	

}
