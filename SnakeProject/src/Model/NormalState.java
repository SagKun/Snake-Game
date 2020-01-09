package Model;

public class NormalState implements SnakeState {

	@Override
	public void setSnakeState(Snake snake) {
		
		snake.setState(this);
		snake.setSnakeSpeed(8);
	}

}
