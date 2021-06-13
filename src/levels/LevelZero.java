//Level Zero is first and default level
package levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import Constants.GameConstants;
import objects.Snake;

public class LevelZero extends Level{

	@Override
	public boolean checkCollision(Point point) {
		// No collsion object
		return false;
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, GameConstants.W_WIDTH, GameConstants.W_WIDTH);
	}

	@Override
	public void guideSnake(Snake snake) {
		//To make snake visible through the game
		if(snake.getX() < 0)
			snake.setX(GameConstants.W_WIDTH - 16);
		
		if(snake.getX() > GameConstants.W_WIDTH )
			snake.setX(0);
		
		if(snake.getY() < 0 )
			snake.setY(GameConstants.W_Height - 16);
		
		if(snake.getY()  > GameConstants.W_Height )
			snake.setY(0);
		
		if(snake.getX() + 16 < 0)
			snake.setX(GameConstants.W_WIDTH - 16);
		
		if(snake.getX() + 16 > GameConstants.W_WIDTH )
			snake.setX(0);
		
		if(snake.getY() + 16 < 0 )
			snake.setY(GameConstants.W_Height - 16);
		
		if(snake.getY() + 16 > GameConstants.W_Height )
			snake.setY(0);
	}

}
