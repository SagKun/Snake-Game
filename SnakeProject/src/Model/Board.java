package Model;

import java.util.ArrayList;
import java.util.Random;


public class Board {

	private static Board boardInstance = null;

	private FoodFactory factory;

	private ArrayList<SnakeFood> ObjectList;

	private Mouse mouse;

	private int score, life;
	/**
	 * Snake object
	 */
	private Snake snake; 
	/**
	 * Snake's head
	 */
	private BodyPart head; 
	/**
	 * Random number for generating points to place objects on them
	 */
	Random rand; 

	public static Board getInstance() {
		if(boardInstance == null)
			boardInstance = new Board();
		return boardInstance;
	}

	/**
	 * Default constructor of board class to initialize starting variables
	 */
	private Board() { 

		ObjectList = new ArrayList<SnakeFood>();
		factory = new FoodFactory();
		snake = new Snake();
		head = snake.getHead();
		score = 0;
		life = 3;
	}
	
	//******************************** GETTERS & SETTERS ******************************************

	public FoodFactory getFactory() {
		return factory;
	}

	public ArrayList<SnakeFood> getObjectList() {
		return ObjectList;
	}

	public Mouse getMouse() {
		return mouse;
	}

	public int getScore() {
		return score;
	}

	public int getLife() {
		return life;
	}

	public Snake getSnake() {
		return snake;
	}

	public BodyPart getHead() {
		return head;
	}

	public void setFactory(FoodFactory factory) {
		this.factory = factory;
	}

	public void setObjectList(ArrayList<SnakeFood> objectList) {
		ObjectList = objectList;
	}

	public void setMouse(Mouse mouse) {
		this.mouse = mouse;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	public void setHead(BodyPart head) {
		this.head = head;
	}


}