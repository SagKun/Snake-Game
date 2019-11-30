package Model;

import java.util.ArrayList;
import java.util.Random;


import View.MainView;
import javafx.animation.*;
import javafx.util.Duration;

public class Board {

	/**
	 * Number of GameObjects to store in X-axis
	 */
	private static final int BWIDTH = MainView.WIDTH/GameObject.SIZE;
	/**
	 * Number of GameObjects to store in Y-axis
	 */
	private static final int BHEIGHT = MainView.HEIGHT/GameObject.SIZE;
	/**
	 * List of Apple fruits
	 */
	private ArrayList<Fruit> aFruits,bFruits,pFruits ;
	
	/**
	 * Super fruit object
	 */
	private GoldenFruit sFruit;
	/**
	 * List of obstacles
	 */
	private ArrayList<Obstacle> obstacles; // List of obstacles
	/**
	 * Score value
	 */
	private int score, highscore, aFruitsEaten, bFruitsEaten, pFruitsEaten;
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
	 * State of the game
	 */
	private GameState state;
	/**
	 *  Object of ScoreView to exchange informations about actual score
	 */
	private ScoreView scoreView; 
	/**
	 * Boolean value that tells if it's time to set a new super fruit on the board
	 */
	private boolean isSuper;
	/**
	 * Player start life
	 */
	private int life; 
	
	/**
	 * Boolean value set true if snake is in super mode after eating the super fruit
	 */
	private boolean superState;
	
	/**
	 * Timers for super fruit and it's effect
	 */
	private Timeline timeSuper, timeSFruit;
	
	/**
	 * Default constructor of board class to initialize starting variables
	 */
	public Board() { 
		
		scoreView = new ScoreView();
		aFruits = new ArrayList<>();
		bFruits = new ArrayList<>();
		pFruits = new ArrayList<>();
		obstacles = new ArrayList<>();
		score = aFruitsEaten = bFruitsEaten = pFruitsEaten = 0;
		snake = new Snake();
		rand = new Random();
		head = snake.getHead();
		state = GameState.Started;
		sFruit = null;
		isSuper = false;
		superState = false;
		life = 3;
		
	}
	
	/**
	 * Method for updating fruits on board
	 */
	public void updateFruit() {
		
		int foodX = 0, foodY = 0; // foodX, foodY - coordinates for normal fruit
		int []place; // place on board, will hold X and Y
		
		if(aFruits.size() < 1) { // if there's no fruit			
			if(aFruitsEaten % 5 == 0 && aFruitsEaten != 0 ) { // adds super fruit
				place = placeFruit();
				foodX = place[0];
				foodY = place[1];
				//TODO ADD GoldApple
				addFruit(foodX, foodY, "Apple");
			}			
			else {
				place = placeFruit();
				foodX = place[0];
				foodY = place[1];
				addFruit(foodX, foodY, "Apple");
			}
		}
		if(bFruits.size() < 1) { // if there's no fruit
			if(bFruitsEaten % 5 == 0 && bFruitsEaten != 0) { // adds super fruit
				place = placeFruit();
				foodX = place[0];
				foodY = place[1];
				//TODO ADD GoldBanana
				addFruit(foodX, foodY, "Banana");
			}			
			else {
				place = placeFruit();
				foodX = place[0];
				foodY = place[1];
				addFruit(foodX, foodY, "Banana");
			}	
		}
		if(pFruits.size() < 1) { // if there's no fruit
			
			if(pFruitsEaten % 5 == 0 && pFruitsEaten != 0) { // adds super fruit
				place = placeFruit();
				foodX = place[0];
				foodY = place[1];
				//TODO ADD GoldPear
				addFruit(foodX, foodY, "Pear");
			}			
			else {
				place = placeFruit();
				foodX = place[0];
				foodY = place[1];
				addFruit(foodX, foodY, "Pear");
			}
		}
	}
	
	/**
	 * Method to place a fruit on the board
	 * @return Returns point(X,Y) on board
	 */
	private int[] placeFruit() {
		
		int []point = new int[2];
		
		int helpX, helpY, foodX = 0, foodY = 0;
		boolean helpS, helpO;	// for Snake and Obstacles
		boolean collision = true;

		while(collision) {
				
			helpS = helpO = false;
			foodX = (rand.nextInt(BWIDTH)*GameObject.SIZE)+GameObject.SIZE/2;
			foodY = (rand.nextInt(BHEIGHT)*GameObject.SIZE)+GameObject.SIZE/2;
				
			for(int i = 0; i < snake.getSize(); ++i) {
					
				helpX = snake.getBodyPart(i).getX();
				helpY = snake.getBodyPart(i).getY();
	
				if(helpX == foodX && helpY == foodY) {
					break;
				}
						
				if(i == snake.getSize() - 1) {
					helpS = true;
				}
			}
					
			if(helpS) {
				
				if(obstacles.size() == 0) {
					helpO = true;
				}
				else {
					
					for(int i = 0; i < obstacles.size(); ++i) {
					
						helpX = obstacles.get(i).getX();
						helpY = obstacles.get(i).getY();
			
						if(foodX == helpX && foodY == helpY) {
							break;
						}
								
						if(i == obstacles.size() - 1) {
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
	 * Method to check if an collision occurred, either of the snake head with it's body or with an any borders
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
				if (life > 0) { 
					semiReset();
					return GameState.Finished;
						}
				else {
					highscore = score;
					totalReset();
					return GameState.Finished;	
					}
				
			}
		}
		
		// Checks if the snake has hit the board borders
				if(headX > MainView.WIDTH || headX < 0 ) {
					life--;
					if (life > 0)
						semiReset();
					else {
						highscore = score;
						totalReset();
						return GameState.Finished;	
						}					
				}
				else if (headY < 0 || headY > MainView.HEIGHT ) {
					life--;
					if (life > 0)
						semiReset();
					else {
						highscore = score;
						totalReset();
						return GameState.Finished;
					}
					
				}	
						
				return Controller.getState();
			
	}
	
	/**
	 * Method to check if snake ate a fruit
	 */
	public void checkEaten() {
		
		int headX, headY, foodX, foodY;
		headX = head.getX();
		headY = head.getY();
		// checks if it's the super fruit
		if(isSuper) {
		
			if(sFruit.getX() == headX && sFruit.getY() == headY) {
				removeSuperFruit();
				//++fruitsEaten;
				score+=3;
				superState = true;
				timeSFruit.stop();
				if(timeSuper != null) {
					timeSuper.stop();
				}
				timeSuper = new Timeline(new KeyFrame(Duration.millis(GoldenFruit.SUPER_STATE_TIME), lambda->superState=false));
				timeSuper.play();
				return;
			}
		}
		// check for a aFruit on board 	
	for(int i = 0; i < aFruits.size(); ++i){
					
		foodX = aFruits.get(i).getX();
		foodY = aFruits.get(i).getY();
					
		if(foodX == headX && foodY == headY) {
			System.out.println("Apple Is Eaten");		
			removeFruit(i,"Apple");
			addLength(); // adds body part to snake
			++aFruitsEaten;
			System.out.println("****");
			System.out.println("number of Apple " + aFruitsEaten);
			System.out.println("****");
				
			if(superState) 
				score+=2;
			else
				++score;
				
			//addObstacle = false;
					}
				}
		// check for a bFruit on board 	
		for(int i = 0; i < bFruits.size(); ++i){
					
			foodX = bFruits.get(i).getX();
			foodY = bFruits.get(i).getY();
					
			if(foodX == headX && foodY == headY) {
			System.out.println("Banana Is Eaten");			
			removeFruit(i,"Banana");
			addLength(); // adds body part to snake
			++bFruitsEaten;
			System.out.println("****");
			System.out.println("number of Banana " + bFruitsEaten);
			System.out.println("****");
				
			if(superState) 
				score+=2;
			else
				++score;
				
			//addObstacle = false;
					}
				}

		// check for a pFruit on board 	
		for(int i = 0; i < pFruits.size(); ++i){
					
			foodX = pFruits.get(i).getX();
			foodY = pFruits.get(i).getY();
					
			if(foodX == headX && foodY == headY) {
				System.out.println("Pear Is Eaten");		
				removeFruit(i,"Pear");
				addLength(); // adds body part to snake
				++pFruitsEaten;
				System.out.println("****");
				System.out.println("number of Pear " + pFruitsEaten);
				System.out.println("****");
				
				if(superState) 
					score+=2;
				else
					++score;
				
				//addObstacle = false;
					}
				}
		}
		
	/**
	 * Method to generate a new fruit in the game(2 if it's time for the super-fruit)
	 * @param foodX X coordinate of normal fruit
	 * @param foodY	Y coordinate of normal fruit
	 * @param sFoodX X coordinate of super fruit(-1 by default)
	 * @param sFoodY Y coordinate of super fruit(-1 by default)
	 */
	public void addFruit(int foodX, int foodY, String type) {
		
		switch (type) {
		case "Apple":
			System.out.println("New Apple add to the aFruits list");
			aFruits.add(new Fruit(foodX, foodY)); // add new fruit to fruit array		
			break;
		case "Banana":
			System.out.println("New Banana add to the bFruits list");
			bFruits.add(new Fruit(foodX, foodY)); // add new fruit to fruit array
			break;
		case "Pear":
			System.out.println("New Pear add to the pFruits list");
			pFruits.add(new Fruit(foodX, foodY)); // add new fruit to fruit array	
			break;
		case "Gold":
			//TODO Do something special
			break;
		default:
			System.out.println(":)");
			break;
		}
		
	}
	
	/**
	 * Method to remove a normal fruit from the board, after being eaten by snake
	 * @param i Position of the fruit in array list
	 */
	public void removeFruit(int i, String type) {
		
		switch (type) {
		case "Apple":
				aFruits.remove(i);
			break;
		case "Banana":
				bFruits.remove(i);			
			break;
		case "Pear":
				pFruits.remove(i);
			break;
		
		default:
			System.out.println(":)");
			break;
		}
		
	}
	
	/**
	 * Method to remove super fruit(make it a null value) 
	 */
	public void removeSuperFruit() {
		isSuper = false;
		sFruit = null;
	}
	
	/**
	 * Method for calling another method from ScoreView to add a point to the score
	 */
	public void updateScore() {
		scoreView.addScore(score);
	}
	
	/**
	 * Add new part to snake's body after eating a fruit
	 */
	public void addLength() {
		BodyPart b1 = snake.getBodyPart(snake.getSize()-1), b2 = snake.getBodyPart(snake.getSize()-2);
		if(b1.getX() > b2.getX())
			snake.addBodyPart(b1.getX()+GameObject.SIZE, b1.getY());
		else if(b1.getX() < b2.getX())
			snake.addBodyPart(b1.getX()-GameObject.SIZE, b1.getY());
		else if(b1.getY() >= b2.getY())
			snake.addBodyPart(b1.getX(), b1.getY()+GameObject.SIZE);
		else if(b1.getY() >= b2.getY())
			snake.addBodyPart(b1.getX(), b1.getY()-GameObject.SIZE);
	}
	
	
	/**
	 * Resets basic values of the game after lose
	 */
	public void semiReset() {
		snake.setStart();
		aFruits.clear();
		bFruits.clear();
		pFruits.clear();
		aFruitsEaten = 0;
		superState = false;
	}
	
	/**
	 * Resets basic values of the game after game over
	 */
	public void totalReset() {
		snake.setStart();		
		aFruits.clear();
		bFruits.clear();
		pFruits.clear();
		score = aFruitsEaten = 0;
		superState = false;
		removeSuperFruit();
		life = 3;
	}
		
	public ArrayList<Fruit> getaFruits(){
		return aFruits;
	}
	public ArrayList<Fruit> getbFruits(){
		return bFruits;
	}
	public ArrayList<Fruit> getpFruits(){
		return pFruits;
	}
	
	/**
	 * Returns the super fruit
	 * @return Super fruit object
	 */
	public GoldenFruit getSuperFruit() {
		return sFruit;
	}
	
	/**
	 * Returns obstacles
	 * @return Array with obstacles
	 */
	public ArrayList<Obstacle> getObstacles(){
		return obstacles;
	}
	
	/**
	 * Returns the snake
	 * @return Snake object
	 */
	public Snake getSnake() {
		return snake;
	}
	
	/**
	 * Returns the object representing ScoreView in Board class
	 * @return The ScoreView object
	 */
	public ScoreView getScoreView() {
		return scoreView;
	}
	
	/**
	 * Returns the actual score
	 * @return Value of score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Returns the highscore when game finished
	 * @return Value of final score
	 */
	public int getHighScore() {
		return highscore;
	}
	
	/**
	 * Returns the actual state of the game
	 * @return Value of GameState
	 */
	public GameState getState() {
		return state;
	}
	
	/**
	 * Returns true if snake is in super state or false if not
	 * @return Boolean true or false
	 */
	public boolean getSuperState() {
		return superState;
	}
}