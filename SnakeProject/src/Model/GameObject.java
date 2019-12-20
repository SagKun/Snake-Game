package Model;

/*this class defines a object in the board of the game
 every gameObject will have x and y parameters that will define is location in the board.
*/
public abstract class GameObject {
	public static final int SIZE = 24;
	protected int x, y;
	
	public GameObject(int x, int y){
		
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
}
