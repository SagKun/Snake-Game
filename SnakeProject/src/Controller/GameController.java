package Controller;

import java.net.URISyntaxException;

import Model.*;
import View.MainView;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class GameController {

	/**
	 * Actual state of the game
	 */
	protected static GameState state;
	/**
	 * Boolean variables describing user input
	 */
	private boolean up, down, right, left, pause, resume, start;
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
	 * Variable to control snake's speed
	 */
	private int speedConstraint;
	/**
	 * Variable to change snake's speed at point intervals
	 */
	private int speedPointsConstraint;
	private Snake snake;
	private BodyPart head;
	private MainView view;
	private Board board;
	/**
	 * MediaPlayer object, controls the music played in game
	 */
	private MediaPlayer audio;

	public GameController() {
		state = GameState.Started;
		up = down = right = left = pause = resume = start = false;
		view = new MainView();
		snake = view.getSnake();
		head = snake.getHead();
		board = view.getBoard();
		keyActive = true;
		try {
			audio = new Sound().getAudio();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		resume();
	}

	/**
	 * The gameloop, handles user input, updates and renders the game
	 */
	private void resume() {

		new AnimationTimer() {

			int i = 0;

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
					view.render();
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
				// when game finished
				if (state == GameState.Finished) {
					stop();
				}
				// when game is running, make movement
				if (state == GameState.Running) {
					if (i == speedConstraint) { // control the speed of snake
						move(dx, dy);
						keyActive = true; // unlock possibility to press another key after snake made it's move
						i = 0; // counter to slow down the snake
					}
					++i;
				}

				update(); // updating the game parameters, positions, etc.
				view.render(); // rendering the scene
				movement(view.getScene()); // handling user key input on actual scene
			}
		}.start(); // starting the timer

	}

	/**
	 * Restarting the game by setting basic parameters to their primary values
	 */
	private void restart() {
		state = GameState.Running;
		dx = dy = 0;
		up = down = left = right = false;
		speedConstraint = 3;
		speedPointsConstraint = 50;
	}

	/**
	 * The update method
	 */
	private void update() {

		board.updateFruit(); // updates the state of fruits
		// board.updateObstacles(); // updating the obstacles on board
		board.checkEaten(); // check if a fruit has been eaten
		board.updateScore(); // updating score(passing it to scoreView class to show it on screen)
		if (board.checkCollision() == GameState.Finished) { // check if a collision occurred
			state = GameState.Finished; //
		}
		setSound(); // updating the sound

		// setting snake speed due to gathered points
		if (speedConstraint > 2 && board.getScore() >= speedPointsConstraint)
			speedConstraint = 2; // snake will move faster
		if ((speedConstraint == 2) && (board.getScore() - speedPointsConstraint) >= 10) {
			speedPointsConstraint += 30; // next interval 30 points further
			speedConstraint = 3; // back to original speed
		}
	}

	/**
	 * Method to handle pressed keys on scene given as argument
	 * 
	 * @param scene on which events are performed
	 */
	private void movement(Scene scene) {

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent e) {

				switch (e.getCode()) {
				case UP:
					if (!down && keyActive && state == GameState.Running) {
						up = true;
						left = false;
						right = false;
						keyActive = false;
						System.out.println("the user press on UP key");
					}
					break;
				case DOWN:
					if (!up && keyActive && (left || right) && state == GameState.Running) {
						down = true;
						left = false;
						right = false;
						keyActive = false;
						System.out.println("the user press on DOWN key");
					}
					break;
				case LEFT:
					if (!right && keyActive && state == GameState.Running) {
						left = true;
						up = false;
						down = false;
						keyActive = false;
						System.out.println("the user press on LEFT key");
					}
					break;
				case RIGHT:
					if (!left && keyActive && state == GameState.Running) {
						right = true;
						up = false;
						down = false;
						keyActive = false;
						System.out.println("the user press on RIGHT key");
					}
					break;
				case SPACE: // pause or resume game
					if (state == GameState.Running || state == GameState.Paused) {
						if (pause == false) {
							pause = true;
							resume = false;
							System.out.println("the user press on SPACE key");
						} else {
							resume = true;
							pause = false;
							resume();
						}
					}
					break;
				case ENTER: { // start or restart the game
					if (state == GameState.Started)
						start = true;
					if (state == GameState.Finished) {
						start = true;
						resume();
					}
					System.out.println("the user press on ENTER key");
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
	private void move(int dx, int dy) {

		if (dx != 0 || dy != 0) { // if snake is meant to move

			// temporary variables to hold BodyParts
			BodyPart prev = new BodyPart(head.getX(), head.getY()), next = new BodyPart(head.getX(), head.getY());

			// move head in X-axis
			head.setX(head.getX() + (dx * GameObject.SIZE));
			// move head in Y-axis
			head.setY(head.getY() + (dy * GameObject.SIZE));

			// Right
			if (head.getX() > MainView.WIDTH) {
				state = GameState.Finished;
			}
			// Left
			else if (head.getX() < 0) {
				state = GameState.Finished;
			}
			// Up
			else if (head.getY() < 0) {
				state = GameState.Finished;
			}
			// Down
			else if (head.getY() > MainView.HEIGHT) {
				state = GameState.Finished;
			}

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
	 * Method responsible for music in game
	 */
	private void setSound() {

		if (state == GameState.Running)
			audio.play(); // play music or resume when it was paused, when game is running
		if (state == GameState.Paused)
			audio.pause(); // pause music when game is paused
		if (state == GameState.Finished)
			audio.stop(); // stop the music when game is finished
	}

	/**
	 * Static method for returning the actual state of game for the Model and View
	 * classes
	 * 
	 * @return Actual state of the game
	 */
	public static GameState getState() {
		return state;
	}

	/**
	 * Returns the stage, pass it to Main class
	 * 
	 * @return The game stage
	 */
	public Stage getStage() {
		return view.getStage();
	}
}