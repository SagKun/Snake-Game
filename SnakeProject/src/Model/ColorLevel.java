package Model;

import java.util.HashMap;

import com.sun.javafx.collections.MappingChange.Map;

public enum ColorLevel {
	EASY,
	MODERATE,
	HARD;


	static public int getValue(ColorLevel cl) {
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
		
	static public ColorLevel getLevel(int level) {
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
