//First level
package levels;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

import Constants.GameConstants;
import objects.Snake;

public class LevelObe extends Level{
	
	@Override
	public boolean checkCollision(Point point) {
		
		if(point.x < 0 || point.x > GameConstants.W_WIDTH || point.y < 0 || point.y > GameConstants.W_Height)
			return true;
		
		return false;
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		Color temp = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(0, 0, GameConstants.W_WIDTH, GameConstants.W_Height);
		g.setColor(temp);
		g.fillRect(5, 5, GameConstants.W_WIDTH - 10, GameConstants.W_Height - 10);
	}

	@Override
	public void guideSnake(Snake snake) {
		// TODO Auto-generated method stub
		
	}
	

}
