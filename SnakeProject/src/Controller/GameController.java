package Controller;

import java.util.ArrayList;
import java.util.Random;

import Model.*;
import View.GameView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.util.Pair;


public class GameController {

	private static GameController controllerInstance = null;

	/**
	 * Random number for generating points to place objects on them
	 */
	Random rand; 

	private Board board;


	public static GameController getInstance() {
		if(controllerInstance == null)
			controllerInstance = new GameController();
		return controllerInstance;
	}

	public GameController() {
		rand = new Random();
		this.board = Board.getInstance();
	}

	/**
	 * Set the objects on the board at game start
	 */
	public void initializeObjects() {

		int objectX = 0, objectY = 0; // Coordinates for the object to be placed
		int []place; // place on board, will hold X and Y
		Boolean isFruit = true;

		for(FoodType f : FoodType.values()) {
			if(f == FoodType.Pear)
				place = placePear();
			else
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
	 * Finds a place to put a new object on the board in such a way it wont collide with anything
	 * that is already placed
	 * @return Returns point(X,Y) on board
	 */
	private int[] placeFruit() {

		int []point = new int[2];
		int helpX, helpY, foodX = 0, foodY = 0;
		boolean helpS, helpO;	// for Snake and Objects
		boolean collision = true;
		Snake snake = this.board.getSnake();
		ArrayList<SnakeFood> ObjectList = this.board.getObjectList();

		while(collision) {

			helpS = helpO = false;
			//For later use
			foodX = ((rand.nextInt(GameView.WIDTH/GameObject.SIZE - 1)+1)*GameObject.SIZE) - GameObject.SIZE;
			foodY = ((rand.nextInt(GameView.HEIGHT/GameObject.SIZE - 1)+1)*GameObject.SIZE) - GameObject.SIZE;

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



	private int[] placePear() {
		int place[] = new int[2];

		ArrayList<Pair<Integer,Integer>> corners = new ArrayList<Pair<Integer,Integer>>();
		//Top left
		corners.add(new Pair<Integer, Integer>(0,0));
		//Top right
		corners.add(new Pair<Integer, Integer>(GameView.WIDTH - GameObject.SIZE,0));
		//Bottom left
		corners.add(new Pair<Integer, Integer>(0,GameView.HEIGHT - GameObject.SIZE));
		//Bottom right
		corners.add(new Pair<Integer, Integer>(GameView.WIDTH - GameObject.SIZE,GameView.HEIGHT - GameObject.SIZE));

		Random rand = new Random();
		Pair<Integer, Integer> corner = corners.get(rand.nextInt(corners.size()));

		place[0] = (int) corner.getKey();
		place[1] = (int) corner.getValue();
		return place;
	}


	/**
	 * Method to generate a new object in the game
	 * @param foodX X coordinate of normal fruit
	 * @param foodY	Y coordinate of normal fruit
	 */
	public void addObject(int foodX, int foodY, String type, boolean isFruit) {

		FoodFactory factory = this.board.getFactory();

		if (isFruit){
			if(FoodType.valueOf (type) == FoodType.Mouse) {
				this.board.setMouse((Mouse)factory.getFood(FoodType.Mouse, foodX, foodY));
			}
			else {
				for(SnakeFood sf : this.board.getObjectList()) {
					if(sf.getType() == FoodType.valueOf(type))
						return;
				}
				this.board.getObjectList().add(factory.getFood(FoodType.valueOf (type), foodX, foodY));
			}
		}
		else {
			for(SnakeFood sf : this.board.getObjectList()) {
				if(sf instanceof Question && ((Question) sf).getLevel() == Level.valueOf(type))
					return;
			}
			Question question = factory.getQuestion(Level.valueOf (type));
			if(question != null) {
				question.setX(foodX);
				question.setY(foodY);
				this.board.getObjectList().add(question);
			}
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
			if(Type == FoodType.Pear)
				place = placePear();
			else
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
	 * Method to check if an collision occurred, either of the snake head with it's body or with an obstacle on the board
	 * @return Returns the finished state of game
	 */
	public GameState checkCollision() {

		int headX, headY, helpX, helpY;
		BodyPart head = this.board.getHead();
		Snake snake = this.board.getSnake();

		headX = head.getX();
		headY = head.getY();

		// checks if snake hit itself
		for(int i = 1; i < snake.getSize(); ++i) {
			helpX = snake.getBodyPart(i).getX();
			helpY = snake.getBodyPart(i).getY();
			if(helpX == headX && helpY == headY) {
				this.board.setLife(this.board.getLife() - 1);
				if (this.board.getLife() > 0) {
					semiReset();
					return GameState.Finished;
				}
				else {
					return GameState.GameOver;
				}
			}
		}

		// Checks if the snake has hit the board borders
		if (headX >= GameView.WIDTH || headX < 0) {
			this.board.setLife(this.board.getLife() - 1);
			if (this.board.getLife() > 0) {
				semiReset();
				return GameState.Finished;
			}
			else {
				return GameState.GameOver;
			}
		}

		else if (headY < 0 || headY >= GameView.HEIGHT) {
			this.board.setLife(this.board.getLife() - 1);
			if (this.board.getLife() > 0) {
				semiReset();
				return GameState.Finished;
			}
			else {
				return GameState.GameOver;
			}
		}
		return GameView.getState();
	}


	/**
	 * Method to check if snake ate an object on the board
	 */
	public void checkEaten() {

		int headX, headY, objectX, objectY;
		BodyPart head = this.board.getHead();		

		headX = head.getX();
		headY = head.getY();
		if(this.board.getMouse() != null)
			if(headX == this.board.getMouse().getX() && headY == this.board.getMouse().getY()) {
				this.board.setScore(this.board.getScore() + this.board.getMouse().getPoints());
				this.board.setLife(this.board.getLife() + this.board.getMouse().getExtraLife());
				addLength(this.board.getMouse().getExtraLength());
				delay(FoodType.Mouse,this.board.getMouse().getSecondsBuffer());
				this.board.setMouse(null);
			}

		ArrayList<SnakeFood> ObjectList = this.board.getObjectList();

		// Iterate through all the objects that are currently exist	
		for(int i = 0; i < ObjectList.size(); ++i){			
			objectX = ObjectList.get(i).getX();
			objectY = ObjectList.get(i).getY();
			if(objectX == headX && objectY == headY) {	//if the snake actually "eated" an object	
				if (ObjectList.get(i) instanceof SnakeFood && !(ObjectList.get(i) instanceof Question)) { //if the snake ate a fruit/mouse
					FoodType type = ObjectList.get(i).getType();
					addLength(ObjectList.get(i).getExtraLength()); //adds body parts to snake
					this.board.setScore(this.board.getScore() + ObjectList.get(i).getPoints());//add points to the player
					int time = ObjectList.get(i).getSecondsBuffer();
					ObjectList.remove(i);

					if(type == FoodType.Apple || type == FoodType.Banana) {
						delay(type,time);
					}
					else {
						updateObjects(type);
					}
				}
			}		
		}
	}


	public Question checkQuestionEaten() {

		int headX, headY, objectX, objectY;
		BodyPart head = this.board.getHead();		

		headX = head.getX();
		headY = head.getY();
		ArrayList<SnakeFood> ObjectList = this.board.getObjectList();

		for(int i = 0; i < ObjectList.size(); ++i){			
			objectX = ObjectList.get(i).getX();
			objectY = ObjectList.get(i).getY();
			if(objectX == headX && objectY == headY) {	//if the snake actually "eated" an object	
				if (ObjectList.get(i) instanceof Question) {//if the snake ate a question
					return (Question) ObjectList.get(i);
				}
			}
		}
		return null;
	}


	public void checkUserAnswer(Question question,String answer) {
		//If user answered right
		if(answer.equals(question.getCorrect_ans())) {
			this.board.setScore(this.board.getScore() + question.getLevel().getPoints());
		}
		//If user answered wrong
		else {
			//If after losing points the player has a score that is lower than 0, hes points will be set to 0
			if(this.board.getScore() - question.getSetBack() < 0)
				this.board.setScore(0);
			else
				this.board.setScore((this.board.getScore() - question.getSetBack()));
		}
		this.board.getObjectList().remove(question);
		updateObjects(question.getLevel());
	}

	/**
	 * Add new part to snake's body after eating a food
	 */
	public void addLength(int num) {
		Snake snake = this.board.getSnake();

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


	private void delay(FoodType type,int time) {

		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(time), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				updateObjects(type);
			}
		}));
		timeline.setCycleCount(1);
		timeline.play();
	}



	/**
	 * Check if mouse has hit an object / snake / borders
	 * @param mouseX - mouse X Coordinate 
	 * @param mouseY - mouse Y Coordinate
	 * @return
	 */
	public Boolean mouseCollision(int mouseX,int mouseY) {
		int helpX, helpY;
		boolean helpS = false;	// for Snake and Objects

		Snake snake = this.board.getSnake();
		ArrayList<SnakeFood> ObjectList = this.board.getObjectList();

		for(int i = 0; i < snake.getSize(); ++i){

			helpX = snake.getBodyPart(i).getX();
			helpY = snake.getBodyPart(i).getY();

			if(helpX == mouseX && helpY == mouseY)
				return false;

			if(i == snake.getSize() - 1)
				helpS = true;
		}

		if(helpS){
			if(ObjectList.size() == 0)
				return true;
			else {
				for(int i = 0; i < ObjectList.size(); ++i) {
					if(ObjectList.get(i).getType() == FoodType.Mouse)
						continue;
					helpX = ObjectList.get(i).getX();
					helpY = ObjectList.get(i).getY();

					if(mouseX == helpX && mouseY == helpY) {
						return false;
					}
				}
			}		
		}
		// Checks if the mouse has hit the board borders
		if (mouseX >= GameView.WIDTH || mouseX < 0) 
			return false;

		else if (mouseY < 0 || mouseY >= GameView.HEIGHT) 
			return false;

		return true;	
	}


	public void fullReset() {
		this.board.getSnake().setStart();
		this.board.getObjectList().clear();
		this.board.setScore(0);
		this.board.setLife(3);
	}
	
	private void semiReset() {
		this.board.getSnake().setStart();
		this.board.getObjectList().clear();
	}
}