package View;

import Utils.Sound;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import Controller.GameController;
import Model.*;
import Utils.Fonts;
import animatefx.animation.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;



public class GameView implements Initializable {


	private Board board;

	/**
	 * Keep track of the snake and its head
	 */
	private Snake snake;	
	private BodyPart head;

	@FXML
	private ImageView mute;

	@FXML
	private ImageView burger;


	@FXML
	private StackPane stackPane;

	@FXML
	private Pane pane;

	@FXML
	private AnchorPane background;
	@FXML
	private AnchorPane anchorPane;

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
	private Label gameOver;
	@FXML
	private ImageView menuBtn;
	@FXML
	private Text lifeAmount;
	@FXML
	private Label pressToPlay;
	@FXML
	private Label pressToResume;
	@FXML
	private Label scoreLabel;
	@FXML
	private Label livesLabel;
	@FXML
	private ImageView arrows;
	@FXML
	private ImageView snakeCryGif;

	@FXML
	private AnchorPane popup;
	@FXML
	private BorderPane borderPane;


	@FXML
	private ImageView space;
	/**
	 * Actual state of the game
	 */
	protected static GameState state;
	protected static boolean answeredRight;
	protected static Question questionForScreen;


	/**
	 * Boolean variables describing user input
	 */
	protected boolean up, down, right,pause, left, resume, start,startup,keyActive,wasMuted;

	/**
	 * The movement in X and Y-axis
	 */
	private int dx, dy;
	/**
	 * Variable to control snake's speed and mouse speed
	 */
	private int speedConstraint,mouseSpeedConstraint;


	private Stage stage;
	private ImageView headImage;

	private Image muteImage;
	private Image unmuteImage;

	/**
	 * MediaPlayer object, controls the music played in game
	 */
	private MediaPlayer audio;
	private MediaPlayer gameOverVoice;
	private MediaPlayer eatSound;
	public static boolean initialize=true;

	/**
	 * The size of the game board size.
	 */
	public static final int WIDTH = 960;
	public static final int HEIGHT = 624;

	/**
	 * Variables to help with the movement of the mouse
	 */
	private int k = 0;
	private int lastIndex;

	private int stateCounter = 1;
	private GameController gameController;

	/**
	 * Constructor of the game view.
	 */
	public GameView() {

		board = Board.getInstance();
		gameController = GameController.getInstance();
		snake = board.getSnake();	
		state = GameState.Started;
		up = down = right = left = pause = resume = start = false;
		head = snake.getHead();
		keyActive = startup = true;
		audio = new Sound().getAudio();
		gameOverVoice = new Sound().getGameOverVoice();
		eatSound = new Sound().getEatSound();
	}

	/**
	 * Initializing the elements on the screen.
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		//the first section of this method sets the custom font and animations to the relevant nodes in the screen.
		if(initialize)
		{
			wasMuted=true;
			muteImage=new Image("/View/icons/mute-32.png");
			unmuteImage=new Image("/View/icons/volume.png");
			initialize=false;
			scoreField.setFont(Fonts.minecraft50);
			scoreLabel.setFont(Fonts.minecraft50);
			livesLabel.setFont(Fonts.minecraft50);
			pressToPlay.setFont(Fonts.minecraft30);
			pressToResume.setFont(Fonts.minecraft30);
			lifeAmount.setFont(Fonts.minecraft30);
			new Pulse(scoreField).setCycleCount(Timeline.INDEFINITE).setSpeed(0.5).play();
			new Pulse(mute).setCycleCount(Timeline.INDEFINITE).setSpeed(0.5).play();
			new Pulse(burger).setCycleCount(Timeline.INDEFINITE).setSpeed(0.5).play();
			new Pulse(scoreLabel).setCycleCount(Timeline.INDEFINITE).setSpeed(0.5).play();
			new Pulse(livesLabel).setCycleCount(Timeline.INDEFINITE).setSpeed(0.5).play();
			new Pulse(pressToPlay).setCycleCount(Timeline.INDEFINITE).setSpeed(1.5).play();
			new Pulse(pressToResume).setCycleCount(Timeline.INDEFINITE).setSpeed(1.5).play();
			new Pulse(arrows).setCycleCount(Timeline.INDEFINITE).setSpeed(1.5).play();
			new Pulse(space).setCycleCount(Timeline.INDEFINITE).setSpeed(1.5).play();
			new Pulse(pressToResume).setCycleCount(Timeline.INDEFINITE).setSpeed(1.5).play();
			new Pulse(scoreField).setCycleCount(15).setCycleCount(4).setSpeed(0.5).play();
		}
		// Setting the snake at the center of the board
		headImage =  new ImageView("View/icons/GameObjects/snakeUp.png");
		headImage.setX(snake.getHead().getX());
		headImage.setY(snake.getHead().getY());
		pane.getChildren().add(headImage);

		int snakeY, snakeX;

		for(int i = 1; i < snake.getSize(); ++i) {
			snakeX = snake.getBodyPart(i).getX();
			snakeY = snake.getBodyPart(i).getY();
			ImageView bodyImage =  new ImageView("View/icons/GameObjects/bodyLarge.png");
			bodyImage.setX(snakeX);
			bodyImage.setY(snakeY);
			pane.getChildren().add(bodyImage);
		}

	}



	/**
	 * The gameloop, handles snake movement, updates and renders the game
	 */
	public void resume() {

		new AnimationTimer() {

			int i = 0, j = 0;

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
					Sound.startTime = audio.getStartTime().toSeconds();
					audio.setStartTime(Duration.seconds(audio.getCurrentTime().toSeconds()));
					audio.stop();
					state = GameState.Paused;
					stop();
				}
				// when game resumed
				if (resume && !pause) {
					state = GameState.Running;
					if(wasMuted)
						audio.play();
					resume = false;
				}
				// when game started or restarted
				if (start && (state == GameState.Finished || state == GameState.Started)) {
					System.out.println("game started");
					up = down = left = right = false;
					restart();
					start = false;
				}
				//when the user has lost a life but game isn't over.
				if (state == GameState.Finished) {

					updateLife();
					up = down = left = right = false;
					restart();
					gameController.initializeObjects();

				}
				// when game is over
				if (state == GameState.GameOver) {
					initialize=true;
					audio.stop();
					gameOverVoice.play();
					stop();
				}
				// when game is running, make movement
				if (state == GameState.Running) {
					if(wasMuted)
						audio.play();
					//Control the snake movement
					if (i == board.getSnake().getSnakeSpeed()) { // control the speed of snake
						snakeMove(dx, dy);
						keyActive = true; // unlock possibility to press another key after snake made it's move
						i = 0; // counter to slow down the snake
					}
					++i;
					//Control the mouse movement
					//Make the mouse move each direction 5 times unless its being blocked.
					if (j >= mouseSpeedConstraint && board.getMouse() != null) {// control the speed of snake
						if(k % 5 == 0) {
							lastIndex = mouseMove(5);
						}
						else
							lastIndex = mouseMove(lastIndex);
						k++;
						j = 0; // counter to slow down the mouse
					}
					++j;
				}

				update(); // updating the game parameters, positions, etc.
				render(); // Rendering the game graphics
				pane.requestFocus();
				movement(); //Check what direction to move the snake to
			}
		}.start(); // starting the timer
	}

	/**
	 * This function sets and refresh the game graphics: snake graphics, fruit graphics, question and mouse.
	 */
	protected void render() {

		if(state == GameState.Running) {


			pane.getChildren().clear(); //Clear the screen and then place the objects at their updated place


			//Render the snake graphics
			pane.getChildren().remove(headImage);

			//this section positions the snake head to fit the vector of the snake.
			if(up) {
				headImage =  new ImageView("View/icons/GameObjects/snakeUp.png");
				headImage.setX(snake.getHead().getX());
				headImage.setY(snake.getHead().getY());
				pane.getChildren().add(headImage);
			}
			else if(down)
			{

				headImage =  new ImageView("View/icons/GameObjects/snakeDown.png");
				headImage.setX(snake.getHead().getX());
				headImage.setY(snake.getHead().getY());
				pane.getChildren().add(headImage);

			}
			else if(right)
			{

				headImage =  new ImageView("View/icons/GameObjects/snakeRight.png");
				headImage.setX(snake.getHead().getX());
				headImage.setY(snake.getHead().getY());
				pane.getChildren().add(headImage);

			}
			else if(left)
			{
				headImage =  new ImageView("View/icons/GameObjects/snakeLeft.png");
				headImage.setX(snake.getHead().getX());
				headImage.setY(snake.getHead().getY());
				pane.getChildren().add(headImage);
			}
			else
			{	
				headImage =  new ImageView("View/icons/GameObjects/snakeUp.png");
				headImage.setX(snake.getHead().getX());
				headImage.setY(snake.getHead().getY());
				pane.getChildren().add(headImage);

			}

			int helpX, helpY, snakeY, snakeX;

			for(int i = 1; i < snake.getSize(); ++i) {
				snakeX = snake.getBodyPart(i).getX();
				snakeY = snake.getBodyPart(i).getY();
				ImageView bodyImage =  new ImageView("View/icons/GameObjects/bodyLarge.png");
				bodyImage.setX(snakeX);
				bodyImage.setY(snakeY);
				pane.getChildren().add(bodyImage);
			}

			// Render the fruits graphics
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
				else if(board.getObjectList().get(i) instanceof Question) {
					Question question = (Question) board.getObjectList().get(i);

					if(question.getLevel() == Level.EASY)
						imagePath = "View/icons/GameObjects/question_white.png";
					else if(question.getLevel() == Level.MODERATE)
						imagePath = "View/icons/GameObjects/question_yellow.png";
					else if(question.getLevel() == Level.HARD)
						imagePath = "View/icons/GameObjects/question_red.png";
				}
				if(!imagePath.contentEquals(""))
				{
					ImageView fruitIcon =  new ImageView(imagePath);
					fruitIcon.setX(helpX);
					fruitIcon.setY(helpY);
					pane.getChildren().add(fruitIcon);
				}
			}

			//TODO render the question graphics

			//Render the mouse graphics
			if(board.getMouse() != null) {
				ImageView mouseIcon =  new ImageView("View/icons/GameObjects/mouse.png");
				mouseIcon.setX(board.getMouse().getX());
				mouseIcon.setY(board.getMouse().getY());
				pane.getChildren().add(mouseIcon);
			}
		}
	}


	/**
	 * The update method: if the game has just started it will initialize the objects,score,life.
	 * this function calls to check if objects have been eaten or snake has crashed into a wall or
	 * itself.
	 */
	private void update() {

		int currentScore=Integer.parseInt(scoreField.getText());
		if(startup) {
			gameController.initializeObjects();
			startup = false;
		}

		if(gameController.checkEaten()) { // check if a fruit has been eaten
			eatSound.play();
			eatSound.seek(Duration.ZERO);
		}
		Question question = gameController.checkQuestionEaten();
		if(question != null) {
			//TODO Change this code depends on player answer
			eatSound.play();
			eatSound.seek(Duration.ZERO);
			questionForScreen=question;
			loadQuestion();


			//gameController.checkUserAnswer(question, question.getCorrect_ans());
		}
		//Set the game score
		this.scoreField.setText(String.valueOf(board.getScore()));

		//Set the snake in super state if the score is a multiply of 100
		if(this.board.getScore() > 100 * stateCounter) {
			stateCounter++;
			this.gameController.setSuperState();
			audio.setRate(1.25);
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					gameController.setNormalState();
					audio.setRate(1.0);
				}
			}));
			timeline.setCycleCount(1);
			timeline.play();
		}

		int newScore=Integer.parseInt(scoreField.getText());		
		if(currentScore < newScore && state.equals(GameState.Running)) //if the score changes,this section makes an animation for the score gained,that comes out of the snake head position when it was eaten.
		{
			Label floatingScore=new Label();
			floatingScore.setFont(Fonts.minecraft30);
			anchorPane.getChildren().add(floatingScore);
			floatingScore.setStyle("-fx-text-fill: green;");
			floatingScore.setText("+"+String.valueOf(newScore-currentScore));
			floatingScore.setLayoutX(snake.getHead().getX());
			floatingScore.setLayoutX(snake.getHead().getX());
			new ZoomInUp(floatingScore).setCycleCount(1).setSpeed(0.5).playOnFinished(new ZoomOutUp(floatingScore).setCycleCount(1).setSpeed(0.5)).play();
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {

					floatingScore.setVisible(false);		
				}
			}) , new KeyFrame(Duration.seconds(4)));
			timeline.play();
		}

		else if(currentScore > newScore && state.equals(GameState.Running)) //if the score changes,this section makes an animation for the score gained,that comes out of the snake head position when it was eaten.
		{

			Label floatingScore=new Label();
			floatingScore.setStyle("-fx-text-fill: red;");
			floatingScore.setFont(Fonts.minecraft30);
			anchorPane.getChildren().add(floatingScore);
			floatingScore.setText(String.valueOf(newScore-currentScore));
			floatingScore.setLayoutX(snake.getHead().getX());
			floatingScore.setLayoutX(snake.getHead().getX());
			new ZoomInUp(floatingScore).setCycleCount(1).setSpeed(0.5).playOnFinished(new ZoomOutUp(floatingScore).setCycleCount(1).setSpeed(0.5)).play();
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {

					floatingScore.setVisible(false);		
				}
			}) , new KeyFrame(Duration.seconds(4)));
			timeline.play();
		}

		updateLife(); //Update life

		if (gameController.checkCollision() == GameState.Finished) { // check if a collision occurred but life > 0
			state = GameState.Finished; //
		}
		else if(gameController.checkCollision() == GameState.GameOver) //check if a collision occurred but life = 0
			state = GameState.GameOver;
	}

	/**
	 * Method to handle pressed keys on scene given as argument
	 * 
	 * @param scene on which events are performed
	 */
	private void movement() {

		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent e) {
				switch (e.getCode()) {
				case UP:
					if (state == GameState.Started)
					{
						//	pane.getChildren().remove();
						arrows.setVisible(true);
						pressToPlay.setVisible(true);
						pressToResume.setVisible(true);
						space.setVisible(true);
						pane.setEffect(null);
						arrows.setVisible(false);
						pressToPlay.setVisible(false);
						start = false;
						restart();
					}
					if (!down && keyActive && state == GameState.Running) {
						up = true;
						left = false;
						right = false;
						keyActive = false;
					}
					break;
				case DOWN:
					if (state == GameState.Started)
					{
						pane.setEffect(null);
						arrows.setVisible(false);
						pressToPlay.setVisible(false);
						pressToResume.setVisible(false);
						space.setVisible(false);
						start = false;
						restart();
					}
					if (!up && keyActive && (left || right) && state == GameState.Running) {
						down = true;
						left = false;
						right = false;
						keyActive = false;
					}
					break;
				case LEFT:
					if (state == GameState.Started)
					{
						pane.setEffect(null);
						arrows.setVisible(false);
						pressToPlay.setVisible(false);
						start = false;
						restart();
					}
					if (!right && keyActive && state == GameState.Running) {
						left = true;
						up = false;
						down = false;
						keyActive = false;
					}
					break;
				case RIGHT:
					if (state == GameState.Started)
					{
						pane.setEffect(null);
						arrows.setVisible(false);
						pressToPlay.setVisible(false);
						start = false;
						restart();
					}
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
							loadPause();
						}
						else {
							popup.setVisible(false);
							resume = true;
							pause = false;
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

		pane.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
			}
		});
	}

	/**
	 * Method to handle snake's position and movement on board
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
	 * the number 5 is to go to that last direction that the mouse has been going.
	 */
	private int mouseMove(int direction) {
		Random rand = new Random();
		List<String> movingOptions = Arrays.asList("UP","DOWN","LEFT","RIGHT");
		Boolean canMove = false;

		int mouseX = board.getMouse().getX();
		int mouseY = board.getMouse().getY();
		int nextX,nextY,index;

		//A loop to find a new direction as long as the mouse is stuck
		while(!canMove) {
			if(direction == 5)
				index = rand.nextInt(movingOptions.size());
			else
				index = direction;

			switch(movingOptions.get(index)){
			case "UP":
				nextX = mouseX;
				nextY = mouseY - GameObject.SIZE;
				if(gameController.mouseCollision(nextX, nextY)) {
					board.getMouse().setX(nextX);
					board.getMouse().setY(nextY);
					return 0;
				}
				else 
					direction = 5;
				break;

			case "DOWN":
				nextX = mouseX;
				nextY = mouseY + GameObject.SIZE;
				if(gameController.mouseCollision(nextX, nextY)) {
					board.getMouse().setX(nextX);
					board.getMouse().setY(nextY);
					return 1;
				}
				else 
					direction = 5;
				break;

			case "LEFT":
				nextX = mouseX - GameObject.SIZE;
				nextY = mouseY;
				if(gameController.mouseCollision(nextX, nextY)) {
					board.getMouse().setX(nextX);
					board.getMouse().setY(nextY);
					return 2;
				}
				else 
					direction = 5;
				break;

			case "RIGHT":
				nextX = mouseX + GameObject.SIZE;
				nextY = mouseY;
				if(gameController.mouseCollision(nextX, nextY)) {
					board.getMouse().setX(nextX);
					board.getMouse().setY(nextY);
					return 3;
				}
				else 
					direction = 5;
				break;
			}
		}
		return direction;
	}


	/**
	 * Restarting the game by setting basic parameters to their primary values
	 */
	protected void restart() {
		state = GameState.Running;
		dx = dy = k = 0;
		speedConstraint = 8;
		mouseSpeedConstraint = 12;
		initialize=true;
	}


	public void resetGame() {
		state = GameState.Running;
		up = down = left = right = false;
		dx = dy = k = 0;
		speedConstraint = 8;
		mouseSpeedConstraint = 12;
		this.gameController.fullReset();
		initialize=true;
	}

	//this methods recieves a node and blur it
	public void blur(Region reg) {
		ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
		GaussianBlur blur = new GaussianBlur(55); // 55 is just to show edge effect more clearly.
		adj.setInput(blur);
		reg.setEffect(adj);
	}


	// update the gui of the life of a player
	public void updateLife() {
		String life;
		switch(board.getLife()) {
		case -1:
			life1.setVisible(false);
			life2.setVisible(false);
			life3.setVisible(false);
			life = "";
			lifeAmount.setText(life);
			gameOver.setVisible(true);
			snakeCryGif.setVisible(true);
			gameOver.setFont(Fonts.minecraft50);

			new Hinge(gameOver).setCycleCount(1).setSpeed(0.5).play();                                
			loadGameoverDelay();

			break;
		case 1:
			life1.setVisible(true);
			life2.setVisible(false);
			life3.setVisible(false);
			life = "";
			lifeAmount.setText(life);
			break;
		case 2:
			life1.setVisible(true);
			life2.setVisible(true);
			life3.setVisible(false);
			life = "";
			lifeAmount.setText(life);
			break;
		case 3:
			life1.setVisible(true);
			life2.setVisible(true);
			life3.setVisible(true);
			life = "";
			lifeAmount.setText(life);
			break;
		default:
			life1.setVisible(true);
			life2.setVisible(false);
			life3.setVisible(false);
			life = "x" + board.getLife();
			lifeAmount.setText(life);
			break;
		}
	}

	//help method to load the pause screen
	public void loadPause()
	{
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/GamePaused.fxml"));
			AnchorPane popupPane;
			popupPane = loader.load();
			popupPane.setPrefSize(popup.getWidth(), popup.getHeight());
			popup.getChildren().removeAll(popup.getChildren());
			popup.getChildren().add(popupPane);
			popup.setVisible(true);

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	//help method to load the question screen
	public void loadQuestion()
	{
		try {


			pause = true;
			resume = false;	
			answeredRight=false;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/QuestionView.fxml"));
			AnchorPane popupPane;
			popupPane = loader.load();
			popupPane.setPrefSize(popup.getWidth(), popup.getHeight());
			popup.getChildren().removeAll(popup.getChildren());
			popup.getChildren().add(popupPane);
			popup.setVisible(true);




		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	//delay method for gameOver screen display
	public void loadGameoverDelay()
	{
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				loadGameOver();;
			}
		}) , new KeyFrame(Duration.seconds(5)));
		timeline.play();
	}

	//help method for loading game over screen.
	public void loadGameOver()
	{
		try {
			initialize=true;
			snakeCryGif.setVisible(false);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Nickname.fxml"));
			AnchorPane popupPane;
			popupPane = loader.load();
			popupPane.setPrefSize(popup.getWidth(), popup.getHeight());
			popup.getChildren().removeAll(popup.getChildren());
			popup.getChildren().add(popupPane);
			popup.setVisible(true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}


	public void openPauseMenu(MouseEvent event) {
		if (state == GameState.Running || state == GameState.Paused) {
			if (pause == false) {
				pause = true;
				resume = false;	
				loadPause();

			} else {
				popup.setVisible(false);
				resume = true;
				pause = false;
				resume();
			}
		}
	}


	public void mute(MouseEvent event) {

		if(wasMuted)
		{
			Sound.startTime = audio.getStartTime().toSeconds();
			audio.setStartTime(Duration.seconds(audio.getCurrentTime().toSeconds()));
			audio.stop();
			mute.setImage(muteImage);
			mute.setVisible(true);
			System.out.println("muted");
			wasMuted=false;
		}
		else
		{
			audio.play();
			mute.setImage(unmuteImage);
			mute.setVisible(true);
			System.out.println("unmuted");
			wasMuted=true;
		}
	}



	/**************************** GETTERS & SETTERS *************************************/

	public Board getBoard() {
		return board;
	}

	public Snake getSnake() {
		return snake;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public static GameState getState() {
		return state;
	}

}