package Model;

public class Mouse extends SnakeFood{
	
	//the amount of life that you get when you eat a mouse
	public static final int extraLife = 1;
	
	
	public Mouse(int x, int y, FruitType type, int points, int secondsBuffer, int extraLength) {
		super(x, y, type, points, secondsBuffer, extraLength);
	}


	public static int getExtralife() {
		return extraLife;
	}
	

}
