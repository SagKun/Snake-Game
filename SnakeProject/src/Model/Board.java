package Model;

import java.util.ArrayList;
import java.util.Random;

import Controller.GameController;
import View.*;
import javafx.animation.Timeline;

public class Board {

	private ArrayList<SnakeFood> ObjectList;
	
	private GameView mainView;

	/**
	 * 
	 */
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

	/**
	 * Timers for food and it's effect
	 */
	private Timeline timeSuper, timeSFruit;
	/**
	 * Default constructor of board class to initialize starting variables
	 */
	public Board() { 

		ObjectList = new ArrayList<SnakeFood>();
		snake = new Snake();
		rand = new Random();
		head = snake.getHead();
		score = 0;
		life = 3;
		System.out.println("Created Snake\nInitialized score to 0\nInitialized lives to 3");
	}

	/**
	 * Set the objects on the board at game start
	 */
	public void initializeObjects() {

		int objectX = 0, objectY = 0; // Coordinates for the object to be placed
		int []place; // place on board, will hold X and Y
		Boolean isFruit = true;

		for(FoodType f : FoodType.values()) {
			place = placeFruit();
			objectX = place[0];
			objectY = place[1];
			addObject(objectX, objectY, f.name(), isFruit);
		}

		for(Level c : Level.values()) {
			place = placeFruit();
			objectX = place[0];
			objectY = place[1];
			addObject(objectX, objectY, c.name(), !isFruit);
		}
	}

	/**
	 * Update the objects when new one needs to be added
	 * @param Type = fruit/mouse as SnakeFood or question as ColorLevel
	 */
	public void updateObjects(Object Type){

		int objectX = 0, objectY = 0; // Coordinates for the object to be placed
		int []place; // place on board, will hold X and Y
		Boolean isFruit = true;

		if(Type instanceof FoodType){
			place = placeFruit();
			objectX = place[0];
			objectY = place[1];
			addObject(objectX, objectY,((FoodType)Type).name(),isFruit);
		}
		else{
			place = placeFruit();
			objectX = place[0];
			objectY = place[1];
			addObject(objectX, objectY,((Level)Type).name(),!isFruit);
		}		
	}

	/**
	 * Finds a place to put a new object on the board in such a way it wont collide with anything
	 * that is already placed
	 * @return Returns point(X,Y) on board
	 */
	private int[] placeFruit() {

		int []point = new int[2];
		int helpX, helpY, foodX = 0, foodY = 0;
		boolean helpS, helpO;	// for Snake and Objects
		boolean collision = true;

		while(collision) {

			helpS = helpO = false;
			//For later use
			foodX = (rand.nextInt(mainView.width)*GameObject.SIZE)+GameObject.SIZE/2;
			foodY = (rand.nextInt(mainView.height)*GameObject.SIZE)+GameObject.SIZE/2;
			
			//TODO If pear place in random corner which isnt current pear place

			for(int i = 0; i < snake.getSize(); ++i){

				helpX = snake.getBodyPart(i).getX();
				helpY = snake.getBodyPart(i).getY();

				if(helpX == foodX && helpY == foodY)
					break;

				if(i == snake.getSize() - 1)
					helpS = true;
			}

			if(helpS){
				if(ObjectList.size() == 0)
					helpO = true;

				else {
					for(int i = 0; i < ObjectList.size(); ++i) {

						helpX = ObjectList.get(i).getX();
						helpY = ObjectList.get(i).getY();

						if(foodX == helpX && foodY == helpY) {
							break;
						}

						if(i == ObjectList.size() - 1) {
							helpO = true;
						}
					}
				}
				if(helpO) {	
					collision = false;
				}	
			}		
		}
		point[0] = foodX;
		point[1] = foodY;
		return point;	
	}
	/**
	 * Method to check if an collision occurred, either of the snake head with it's body or with an obstacle on the board
	 * @return Returns the finished state of game
	 */
	public GameState checkCollision() {

		int headX, headY, helpX, helpY;

		headX = head.getX();
		headY = head.getY();

		// checks if snake hit itself
		for(int i = 1; i < snake.getSize(); ++i) {
			helpX = snake.getBodyPart(i).getX();
			helpY = snake.getBodyPart(i).getY();
			if(helpX == headX && helpY == headY) {
				life--;
				if(life > 0) {
					semiReset();
					return GameState.Finished;
				}
				else {
					totalReset();
					return GameState.Finished;
				}
			}
		}

		// Checks if the snake has hit the board borders
		if (headX > mainView.getWidth() || headX < 0) {
			life--;
			if (life > 0)
				semiReset();
			else {
				totalReset();
				return GameState.Finished;
			}
		}

		else if (headY < 0 || headY > mainView.getHeight()) {
			life--;
			if (life > 0) {
				semiReset();
				return GameState.Finished;
			}
			else {
				totalReset();
				return GameState.Finished;
			}

		}
		return GameController.getState();
	}

	/**
	 * Method to check if snake ate an object on the board
	 */
	public void checkEaten() {

		int headX, headY, objectX, objectY;
		headX = head.getX();
		headY = head.getY();

		// Iterate through all the objects that are currently exist	
		for(int i = 0; i < ObjectList.size(); ++i){			
			objectX = ObjectList.get(i).getX();
			objectY = ObjectList.get(i).getY();
			if(objectX == headX && objectY == headY) {	//if the snake actually "eated" an object	
				if (ObjectList.get(i) instanceof SnakeFood) { //if the snake ate a fruit/mouse
					FoodType type = ObjectList.get(i).getType();
					addLength(ObjectList.get(i).getExtraLength()); //adds body parts to snake
					score += ObjectList.get(i).getPoints();		//add points to the player
					ObjectList.remove(i);
					updateObjects(type);
				}

				else{ 															//if the snake ate a question
					Level level = ((Question)ObjectList.get(i)).getLevel();
					//TODO צריך לבצע קריאה לפונקציה שמחזירה בהתאם לתשובת המשתמש על השאלה
					//את הניקוד + אורך + חיים

					ObjectList.remove(i);
					updateObjects(level);
				}
			}		
		}
	}
	/**
	 * Method to generate a new fruit in the game(2 if it's time for the super-fruit)
	 * @param foodX X coordinate of normal fruit
	 * @param foodY	Y coordinate of normal fruit
	 */
	public void addObject(int foodX, int foodY, String type, boolean isFruit) {

		if (isFruit){
			ObjectList.add(new SnakeFood(foodX, foodY, FoodType.valueOf(type)));
			System.out.println("Created new object on board - "+FoodType.valueOf(type));
		}
		else{
			ObjectList.add(new Question(foodX, foodY, Level.valueOf(type)));
			System.out.println("Created new object on board - "+Level.valueOf(type) + "Question");

			//TODO להוסיף קונסטרקטור אצל שאלה
		}		

	}

	/**
	 * Add new part to snake's body after eating a food
	 */
	public void addLength(int num) {
		BodyPart b1 = snake.getBodyPart(snake.getSize()-1), b2 = snake.getBodyPart(snake.getSize()-2);
		if(b1.getX() > b2.getX()){
			for(int i=1; i <= num;i++)
				snake.addBodyPart(b1.getX()+i*GameObject.SIZE, b1.getY());
		}
		else if(b1.getX() < b2.getX())
		{
			for(int i=1; i <= num;i++)
				snake.addBodyPart(b1.getX()-i*GameObject.SIZE, b1.getY());
		}
		else if(b1.getY() > b2.getY())
		{
			for(int i=1; i <= num;i++)
				snake.addBodyPart(b1.getX(), b1.getY()+i*GameObject.SIZE);
		}
		else if(b1.getY() < b2.getY())
		{
			for(int i=1; i <= num;i++)
				snake.addBodyPart(b1.getX(), b1.getY()-i*GameObject.SIZE);
		}
	}

	/**
	 * Resets basic values of the game after lose
	 */
	private void semiReset() {
		//TODO
		snake.setStart();
	}
	/**
	 * Resets basic values of the game after game over
	 */
	private void totalReset() {
		//TODO
		snake.setStart();
		ObjectList.clear();
		score = 0;
		life = 3;
	}


	//******************************** GETTERS & SETTERS ******************************************

	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}

}