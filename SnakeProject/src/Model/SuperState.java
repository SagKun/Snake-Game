package Model;

public class SuperState implements SnakeState {

	@Override
	public void setSnakeState(Snake snake) {
		
		snake.setState(this);
		snake.setSnakeSpeed(3);
	}
}
