package View;

import Model.FoodFactory;
import Model.FoodType;
import Model.Level;
import Model.Question;
import javafx.scene.image.ImageView;

public class QuestionsTableInstructions {
		private ImageView image;
		private String level;
		private Integer points;
		private Integer penalty;
		
		public QuestionsTableInstructions(Level level) {
			super();
			FoodFactory factory = new FoodFactory();
			Question q = factory.getQuestion(level, 0, 0, "", null, "", "");
			this.level = q.getLevel().toString();
			points = q.getPoints();
			penalty = q.getSetBack();
			if(level.equals(Level.EASY))
				image = new ImageView("View/icons/GameObjects/question_white.png");
			else if(level.equals(Level.MODERATE))
				image = new ImageView("View/icons/GameObjects/question.png");
			else if(level.equals(Level.HARD))
				image = new ImageView("View/icons/GameObjects/faq.png");
		}

		public ImageView getImage() {
			return image;
		}

		public void setImage(ImageView image) {
			this.image = image;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public Integer getPoints() {
			return points;
		}

		public void setPoints(Integer points) {
			this.points = points;
		}

		public Integer getPenalty() {
			return penalty;
		}

		public void setPenalty(Integer penalty) {
			this.penalty = penalty;
		}
		
		
}
