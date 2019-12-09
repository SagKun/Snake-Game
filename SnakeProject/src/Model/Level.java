package Model;



public enum Level {
	EASY(1),
	MODERATE(2),
	HARD(3);
	
	private int points;
	
	Level(int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return this.points;
	}

	static public int getValue(Level cl) {
		switch(cl) {
		case EASY:
			return 1;
		case MODERATE:
			return 2;
		case HARD:
			return 3;
		default: 
			return 1;
		}
	}
		
	static public Level getLevel(int level) {
			switch(level) {
			case 1:
				return EASY;
			case 2:
				return MODERATE;
			case 3:
				return HARD;
			default: 
				return EASY;
			}

	} 
}
