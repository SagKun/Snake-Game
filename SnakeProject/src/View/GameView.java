package View;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import Model.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameView implements Initializable {

	private Board board;



	private Snake snake;
	@FXML
	private StackPane stackPane;
	
	@FXML
	private Pane pane;

	@FXML
	private AnchorPane background;

	@FXML
	private ImageView life1;

	@FXML
	private ImageView life2;

	@FXML
	private ImageView life3;

	@FXML
	public Label lifeLabel;

	@FXML
	private Label scoreField;

	@FXML
	private ImageView menuBtn;

	@FXML
	private Text lifeAmount;

	@FXML
	private ImageView pressToPlay;

	@FXML
	private ImageView arrows;


	/**
	 * Actual state of the game
	 */
	protected static GameState state;
	/**
	 * Boolean variables describing user input
	 */
	private boolean up, down, right, left, pause, resume, start,startup;
	/**
	 * Boolean block to prevent pressing keys too fast, so that the snake's head
	 * could turn around. For example, when snake was moving left, pressing the up
	 * and right key very fast could just change the head's direction to right,
	 * without changing the position in Y-axis, causing the head to hit the second
	 * part of it's body
	 */
	private boolean keyActive;
	/**
	 * The movement in X and Y-axis
	 */
	private int dx, dy;
	/**
	 * Variable to control snake's speed and mouse speed
	 */
	private int speedConstraint,mouseSpeedConstraint;

	private BodyPart head;

	private Stage stage;

	public static final int WIDTH = 960;
	public static final int HEIGHT = 624;

	public GameView() {
		board = new Board();
		snake = board.getSnake();	
		state = GameState.Started;
		up = down = right = left = pause = resume = start = false;
		head = snake.getHead();
		keyActive = startup = true;
	}


	public Board getBoard() {
		return board;
	}

	public Snake getSnake() {
		return snake;
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	  //  GaussianBlur blur = new GaussianBlur(55); 
	    //adj.setInput(blur);
		blur(pane);
		ImageView headImage =  new ImageView("View/icons/GameObjects/SnakeHead.png");
		headImage.setX(snake.getHead().getX());
		headImage.setY(snake.getHead().getY());
		pane.getChildren().add(headImage);


		int helpX, helpY, snakeY, snakeX;

		for(int i = 1; i < snake.getSize(); ++i) {
			snakeX = snake.getBodyPart(i).getX();
			snakeY = snake.getBodyPart(i).getY();
			ImageView bodyImage =  new ImageView("View/icons/GameObjects/SnakeBody.png");
			bodyImage.setX(snakeX);
			bodyImage.setY(snakeY);
			pane.getChildren().add(bodyImage);
		}


	}



	/**
	 * The gameloop, handles user input, updates and renders the game
	 */
	public void resume() {

		new AnimationTimer() {

			int i = 0, j = 0;

			@Override
			public void handle(long now) {

				// when moving up
				if (up && !down) {

					dy = -1;
					dx = 0;
				}
				// when moving down
				if (!up && down) {

					dy = 1;
					dx = 0;
				}
				// when moving left
				if (left && !right) {

					dy = 0;
					dx = -1;
				}
				// when moving right
				if (right && !left) {
					dy = 0;
					dx = 1;
				}
				// when game paused
				if (pause && !resume) {
					state = GameState.Paused;
					stop();
				}
				// when game resumed
				if (resume && !pause) {
					state = GameState.Running;
					resume = false;
				}
				// when game started or restarted
				if (start && (state == GameState.Finished || state == GameState.Started)) {
					restart();
					start = false;
				}
				if (state == GameState.Finished) {
					//TODO Case when game ended but more lives to play - semi reset
					restart();
					board.initializeObjects();
				}
				// when game is done
				if (state == GameState.GameOver) {
					stop();
				}
				// when game is running, make movement
				if (state == GameState.Running) {
					if (i == speedConstraint) { // control the speed of snake
						snakeMove(dx, dy);
						keyActive = true; // unlock possibility to press another key after snake made it's move
						i = 0; // counter to slow down the snake
					}
					++i;

					if (j == mouseSpeedConstraint) { // control the speed of snake
						//mouseMove();
						j = 0; // counter to slow down the mouse
					}
					++j;

				}

				update(); // updating the game parameters, positions, etc.
				render();
				pane.requestFocus();
				movement(stage.getScene());
			}
		}.start(); // starting the timer


	}


	protected void render() {

		if(state == GameState.Running) {

			pane.getChildren().clear();


			ImageView headImage =  new ImageView("View/icons/GameObjects/SnakeHead.png");
			headImage.setX(snake.getHead().getX());
			headImage.setY(snake.getHead().getY());
			pane.getChildren().add(headImage);


			int helpX, helpY, snakeY, snakeX;

			for(int i = 1; i < snake.getSize(); ++i) {
				snakeX = snake.getBodyPart(i).getX();
				snakeY = snake.getBodyPart(i).getY();
				ImageView bodyImage =  new ImageView("View/icons/GameObjects/SnakeBody.png");
				bodyImage.setX(snakeX);
				bodyImage.setY(snakeY);
				pane.getChildren().add(bodyImage);
			}

			for(int i = 0; i < board.getObjectList().size(); ++i) {
				helpX = board.getObjectList().get(i).getX();
				helpY = board.getObjectList().get(i).getY();
				String imagePath = "";
				if(board.getObjectList().get(i).getType() == FoodType.Apple ) {
					imagePath = "View/icons/GameObjects/apple24.png";
				}
				else if(board.getObjectList().get(i).getType() == FoodType.Banana ) {
					imagePath = "View/icons/GameObjects/banana24.png";
				}
				else if(board.getObjectList().get(i).getType() == FoodType.Pear ) {
					imagePath = "View/icons/GameObjects/pear24.png";
				}
				ImageView fruitIcon =  new ImageView(imagePath);
				fruitIcon.setX(helpX);
				fruitIcon.setY(helpY);
				pane.getChildren().add(fruitIcon);
			}

			ImageView mouseIcon =  new ImageView("View/icons/GameObjects/mouse.png");
			mouseIcon.setX(board.getMouse().getX());
			mouseIcon.setY(board.getMouse().getY());
			pane.getChildren().add(mouseIcon);
		}
	}


	/**
	 * The update method
	 */
	private void update() {
		if(startup) {
			board.initializeObjects();
			startup = false;
		}// updates the state of fruits
		board.checkEaten(); // check if a fruit has been eaten
		this.scoreField.setText(String.valueOf(board.getScore()));
		if (board.checkCollision() == GameState.Finished) { // check if a collision occurred but life > 0
			state = GameState.Finished; //
		}
		else if(board.checkCollision() == GameState.GameOver) //check if a collision occurred but life = 0
			state = GameState.GameOver;
	}

	/**
	 * Method to handle pressed keys on scene given as argument
	 * 
	 * @param scene on which events are performed
	 */
	private void movement(Scene scene) {

		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent e) {
				switch (e.getCode()) {
				case UP:
					if (!down && keyActive && state == GameState.Running) {
						up = true;
						left = false;
						right = false;
						keyActive = false;
					}
					break;
				case DOWN:
					if (!up && keyActive && (left || right) && state == GameState.Running) {
						down = true;
						left = false;
						right = false;
						keyActive = false;
					}
					break;
				case LEFT:
					if (!right && keyActive && state == GameState.Running) {
						left = true;
						up = false;
						down = false;
						keyActive = false;
					}
					break;
				case RIGHT:
					if (!left && keyActive && state == GameState.Running) {
						right = true;
						up = false;
						down = false;
						keyActive = false;
					}
					break;
				case SPACE: // pause or resume game
					if (state == GameState.Running || state == GameState.Paused) {
						if (pause == false) {
							pause = true;
							resume = false;	
							blur(stackPane);
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/GamePaused.fxml"));
							Parent root;
							try {
								Stage pauseStage=new Stage();
								root = loader.load();
								Scene scene = new Scene(root);
								pauseStage.setScene(scene);
								pauseStage.setResizable(false);
								pauseStage.initStyle(StageStyle.UNDECORATED);
								pauseStage.show();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							resume = true;
							pause = false;
							resume();
						}
					}
					break;
				case ENTER: { // start or restart the game
					if (state == GameState.Started)
					{
						pane.setEffect(null);
						arrows.setVisible(false);
						pressToPlay.setVisible(false);
						start = true;
					}
					if (state == GameState.Finished) {
						start = true;
						resume();
					}
				}
				break;
				case ESCAPE: // exit program
					System.exit(0);
					break;
				default:
					break;
				}
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
			}
		});
	}

	/**
	 * Method to handle snake's position and movement on board
	 * 
	 * @param dx - movement in X-axis, 1 for right, -1 for left
	 * @param dy - movement in Y-axis, 1 for down, -1 for up
	 */
	private void snakeMove(int dx, int dy) {

		if (dx != 0 || dy != 0) { // if snake is meant to move

			// temporary variables to hold BodyParts
			BodyPart prev = new BodyPart(head.getX(), head.getY()), next = new BodyPart(head.getX(), head.getY());

			// move head in X-axis
			head.setX(head.getX() + (dx * GameObject.SIZE));
			// move head in Y-axis
			head.setY(head.getY() + (dy * GameObject.SIZE));

			// moving the snake's body, each point gets the position of the one in front
			for (int i = 1; i < snake.getSize(); ++i) {

				next.setX(snake.getBodyPart(i).getX());
				next.setY(snake.getBodyPart(i).getY());

				snake.getBodyPart(i).setX(prev.getX());
				snake.getBodyPart(i).setY(prev.getY());
				prev.setX(next.getX());
				prev.setY(next.getY());
			}
		}
	}

	/**
	 * Method to handle mouse's position and movement on board
	 * 
	 */
	private void mouseMove() {
		Random rand = new Random();
		List<String> movingOptions = Arrays.asList("UP","DOWN","LEFT","RIGHT");
		Boolean canMove = false;

		int mouseX = board.getMouse().getX();
		int mouseY = board.getMouse().getY();
		int nextX,nextY;

		while(!canMove) {
			int index = rand.nextInt(movingOptions.size());
			switch(movingOptions.get(index)){

			case "UP":
				nextX = mouseX;
				nextY = mouseY - GameObject.SIZE;
				if(board.mouseCollision(nextX, nextY)) {
					board.getMouse().setX(nextX);
					board.getMouse().setY(nextY);
				}
				break;

			case "DOWN":
				nextX = 0;
				nextY = mouseY + GameObject.SIZE;
				if(board.mouseCollision(nextX, nextY)) {
					board.getMouse().setX(nextX);
					board.getMouse().setY(nextY);
				}
				break;

			case "LEFT":
				nextX = mouseX - GameObject.SIZE;
				nextY = mouseY;
				if(board.mouseCollision(nextX, nextY)) {
					board.getMouse().setX(nextX);
					board.getMouse().setY(nextY);
				}
				break;

			case "RIGHT":
				nextX = mouseX + GameObject.SIZE;
				nextY = mouseY;
				if(board.mouseCollision(nextX, nextY)) {
					board.getMouse().setX(nextX);
					board.getMouse().setY(nextY);
				}
				break;
			}
		}
	}


	/**
	 * Restarting the game by setting basic parameters to their primary values
	 */
	private void restart() {
		state = GameState.Running;
		dx = dy = 0;
		up = down = left = right = false;
		speedConstraint = 8;
		mouseSpeedConstraint = 6;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}


	public static GameState getState() {
		// TODO Auto-generated method stub
		return state;
	}

	public void blur(Region reg) {
	    ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(55); // 55 is just to show edge effect more clearly.
	    adj.setInput(blur);
	    reg.setEffect(adj);
	}
}
