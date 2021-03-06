package Model;

import java.util.ArrayList;

import View.GameView;


public class Snake {

	 //Snake's starting size
	private static final int SIZE = 4;
	 //Array that holds the entire body
	private BodyPart head;
	//Snake's size variable and starting position of head 
	private ArrayList<BodyPart> body;
	//The current size of the snake
	private int size;
	//this fields define the current location of the snake(with initilization of the starting point)
	private final int headX = GameView.WIDTH/2;
	private final int headY = GameView.HEIGHT/2;
	
	
	private SnakeState state;
	private int snakeSpeed;

	public Snake() {
		
		body = new ArrayList<>();
		head = new BodyPart(headX, headY);
		size = 0;
		state = null;
		setStart();
	}
	
	/**
	 * Method to set snake at starting position, at start or restart of the game
	 */
	public void setStart() {
		
		// set starting position
		if(size == 0) {
			
			body.add(head);
			++size;
			
			for(int i = 1; i < SIZE; ++i) {
				addBodyPart(headX, headY + (i * GameObject.SIZE));
			}
		}
		//if game restart
		else {
			
			body.clear();
			head.setX(headX);
			head.setY(headY);
			size = 0;
			setStart();
		}
	}	
	
	
	/**
	 * Return snake's actual size
	 * @return size
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Return particular body part
	 * @param i - position of part in body array
	 * @return the body part
	 */
	public BodyPart getBodyPart(int i) {
		return body.get(i);
	}
	
	/**
	 * Returns the head of the snake as BodyPart object
	 * @return snake's head
	 */
	public BodyPart getHead() {
		return this.head;
	}
	
	/**
	 * Adds new body part at given point
	 * @param x - position
	 * @param y - position
	 */
	public void addBodyPart(int x, int y) {
		body.add(new BodyPart(x,y));
		++size;
	}

	public int getSnakeSpeed() {
		return snakeSpeed;
	}

	public void setSnakeSpeed(int snakeSpeed) {
		this.snakeSpeed = snakeSpeed;
	}

	
	public void setState(SnakeState state) {
		this.state = state;
	}
	
	public SnakeState getState() {
		return this.state;
	}
	@Override
	public String toString() {
		return "Snake [head=" + head + ", size=" + size + "]";
	}
	
	
}
