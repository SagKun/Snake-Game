package View;

import Controller.GameController;
import Model.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class GameView {

	private Board board;

	private Snake snake;
	
    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane background;

    @FXML
    private ImageView life1;

    @FXML
    private ImageView life2;

    @FXML
    private ImageView life3;



    @FXML
    public Label scoreField;

    @FXML
    private ImageView menuBtn;

    @FXML
    private Text lifeAmount;


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
