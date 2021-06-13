// Level two, this game is level two of the game
package levels;

import java.awt.Graphics;
import java.awt.Point;

import Constants.GameConstants;

import java.awt.Color;

import objects.Snake;

public class LevelTwo extends Level {

	@Override
	public boolean checkCollision(Point point) {
		//Checking Left Side Collision
		if(point.x < 0 && point.y < GameConstants.W_Height/2 - 16 || point.x < 0 && point.y >= GameConstants.W_Height / 2 + 16)
			return true;
		
		//Checking Right Side Collision
		if(point.x > GameConstants.W_WIDTH && point.y <= GameConstants.W_Height / 2 - 16 ||
				point.x > GameConstants.W_WIDTH && point.y >= GameConstants.W_Height/2 + 16)
			return true;
		
		//Checking Upper side Collision
		if(point.y < 0)
			return true;
		
		//Checking Down Side Collision
		if(point.y > GameConstants.W_Height)
			return true;
		
		//Checking for center Collision 
		
		
		//Checking collision with center down line
		int mx = point.x + 8;
		int my = point.y + 8;
		if(mx > GameConstants.W_WIDTH/3 -8 && mx < (GameConstants.W_WIDTH/3)*2 - 8 &&
				my > GameConstants.W_Height/2 - 32 && my < GameConstants.W_Height/2 - 16) {
			System.out.println("Medium values " + mx + " : " + my);
			System.out.println("Values of X : " + (GameConstants.W_WIDTH/3 - 8) + " : " + ((GameConstants.W_WIDTH/3)*2 - 8));
			System.out.println("Values of Y : " + (GameConstants.W_Height/2 -32 ) + " : " + ((GameConstants.W_Height/2 - 16)));
			return true;
		}
		
		//For bottom line of the game
		if(mx > GameConstants.W_WIDTH/3 -8 && mx < (GameConstants.W_WIDTH/3)*2 - 8 &&
				my > GameConstants.W_Height/2 + 16 && my < GameConstants.W_Height/2 + 32) {
			System.out.println("Medium values " + mx + " : " + my);
			System.out.println("Values of X : " + (GameConstants.W_WIDTH/3 - 8) + " : " + ((GameConstants.W_WIDTH/3)*2 - 8));
			System.out.println("Values of Y : " + (GameConstants.W_Height/2 -32 ) + " : " + ((GameConstants.W_Height/2 - 16)));
			return true;
		}
		
	return false;
	}

	@Override
	public void guideSnake(Snake snake) {
		// TODO Auto-generated method stub
		if(snake.getY() >= GameConstants.W_Height/2 - 16 && snake.getY() < GameConstants.W_Height/2 + 16) {
		if(snake.getX() < 0)
			snake.setX(GameConstants.W_WIDTH);
		
		if(snake.getX() > GameConstants.W_WIDTH)
			snake.setX(0);
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		Color temp = g.getColor();
		g.setColor(Color.RED);
		
		//Left side 
		g.fillRect(0, 0, 8, GameConstants.W_Height/2 - 16);
		g.fillRect(0, GameConstants.W_Height / 2 + 16, 8, GameConstants.W_Height/2);
		
		//Right side
		g.fillRect(GameConstants.W_WIDTH - 8, 0, 8, GameConstants.W_Height/2 - 16);
		g.fillRect(GameConstants.W_WIDTH - 8, GameConstants.W_Height / 2 + 16, 8, GameConstants.W_Height/2);
		
		//Upper Side
		g.fillRect(0, 0, GameConstants.W_WIDTH, 8);
		
		//Down Side
		g.fillRect(0, GameConstants.W_Height - 8, GameConstants.W_WIDTH, 8);
		
		//Making a center Wall
		g.fillRect(GameConstants.W_WIDTH / 3 - 8, GameConstants.W_Height / 2 - 32, GameConstants.W_WIDTH / 3, 16);
		g.fillRect(GameConstants.W_WIDTH / 3 - 8, GameConstants.W_Height/2 + 16, GameConstants.W_WIDTH/3, 16);
	}

}
