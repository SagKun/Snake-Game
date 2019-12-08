package View;

import Controller.MainController;
import Model.*;


public class MainView {

	private MainController mainController;
	
	public static final int width = 500;
	public static final int height = 500;
	
	private Board board;
	
	private Snake snake;
	
	
	public MainView() {
		board = new Board();
		snake = board.getSnake();
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public Board getBoard() {
		return board;
	}
	public Snake getSnake() {
		return snake;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	public void setSnake(Snake snake) {
		this.snake = snake;
	}
}
