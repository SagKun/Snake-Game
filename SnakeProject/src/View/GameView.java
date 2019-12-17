package View;

import Controller.GameController;
import Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GameView {

	private Board board;

	private Snake snake;
	
    @FXML
    private GridPane matrix;

    @FXML
    private ImageView life2;

    @FXML
    private ImageView life3;

    @FXML
    private Label life1;

    @FXML
    private TextField scoreField;

    @FXML
    private TextField extraLifeField;

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
