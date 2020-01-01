package View;

import Model.FoodFactory;
import Model.FoodType;
import Model.SnakeFood;
import javafx.scene.image.ImageView;

public class FoodTableForInstructions {
	
		private ImageView image;
		private Integer foodPoints;
		private Integer extraLength;
		private Integer extraLife;
		
		
		public FoodTableForInstructions(FoodType type) {
			super();
			FoodFactory factory = new FoodFactory();
			SnakeFood food = factory.getFood(type, 0, 0);
			foodPoints = food.getPoints();
			extraLength = food.getExtraLength();
			extraLife = food.getExtraLife();
			if(type.equals(FoodType.Apple))
				image = new ImageView("View/icons/GameObjects/apple24.png");
			else if(type.equals(FoodType.Banana))
				image = new ImageView("View/icons/GameObjects/banana24.png");
			else if(type.equals(FoodType.Pear))
				image = new ImageView("View/icons/GameObjects/pear24.png");
			else if(type.equals(FoodType.Mouse))
				image = new ImageView("View/icons/GameObjects/mouse.png");
		}


		public ImageView getImage() {
			return image;
		}


		public void setImage(ImageView image) {
			this.image = image;
		}


		public Integer getFoodPoints() {
			return foodPoints;
		}


		public void setFoodPoints(Integer foodPoints) {
			this.foodPoints = foodPoints;
		}


		public Integer getExtraLength() {
			return extraLength;
		}


		public void setExtraLength(Integer extraLength) {
			this.extraLength = extraLength;
		}


		public Integer getExtraLife() {
			return extraLife;
		}


		public void setExtraLife(Integer extraLife) {
			this.extraLife = extraLife;
		}
		
		
		
}
