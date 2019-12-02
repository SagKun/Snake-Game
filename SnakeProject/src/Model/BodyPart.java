package Model;

import javafx.scene.paint.Color;

//this class represent a body part of the snake, every instance will be either part of his body or his head.
public class BodyPart extends GameObject {
	
	/**
	 * Color of the snake's body
	 */
	public static  Color BODY_COLOR; //TBD maybe an image
	/**
	 * Color of snake's head
	 */
	public static  Color HEAD_COLOR; //TBD maybe an image

	public BodyPart(int x, int y) {
		super(x, y);
		
	}
	 	
	
}
