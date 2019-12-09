package View;

import Controller.GameController;
import Model.*;

public class GameView {

	private GameController gameController;

	private Board board;

	private Snake snake;

	public static final int width = 500;
	public static final int height = 500;


	public GameView() {

		board = new Board();
		snake = board.getSnake();
	}


	public Board getBoard() {
		return board;
	}

	public Snake getSnake() {
		return snake;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
