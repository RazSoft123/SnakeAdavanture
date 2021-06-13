// The class is used to handle score related stuffs.
package info;

import java.awt.Graphics;
import java.awt.Color;

public class Score {

	private int score;
	
	public Score() {
		score = 0;
	}
	
	public int getScore() {
		return score;
	}
	
	public void increseScore(int level) {
		score += level;
	}
	
	public void render(Graphics g) {
		Color temp = g.getColor();
		g.setColor(Color.RED);
		g.drawString("Score : " + score, 30, 30);
		g.setColor(temp);
	}
	
}
