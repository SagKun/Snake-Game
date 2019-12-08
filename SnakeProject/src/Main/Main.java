package Main;

import Test.Test;
import View.MainView;

public class Main{

	public static void main(String args[]) {
		
		MainView mainView = new MainView();
		
		mainView.getBoard().initializeObjects();
	}	
}
