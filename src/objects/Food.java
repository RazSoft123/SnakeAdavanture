package objects;

import levels.*;
import java.util.Random;
import java.awt.Point;
import java.awt.Graphics;

public class Food {

	private Box food;
	private int x ;
	private int y;
	private Random rand;
	private boolean created;
	public Food() {
		rand = new Random();
		food = new Box();
	}
	
	public Point getLocation() {
		return food.getLocation();
	}
	
	public void createFood(Snake snake, Level level) {
		created = false;
		while(!created) {
			x = rand.nextInt(41) * 16 + 16;
			y = rand.nextInt(24) * 16 + 16;
			
			if(snake.checkFaceCollision(new Point(x,y))) {
				created = false;
				continue;
			}else if(snake.checkBodyCollision(new Point(x,y))) {
				created = false;
				continue;
			}else if(level.checkCollision(new Point(x,y))) {
				created = false;
				continue;
			}
			
			created = true;
		}
		food.setLocation(x, y);
		
		switch(rand.nextInt(5)) {
		case 0:
			food.loadImage("res/img/Apple.png");
			break;
		case 1:
			food.loadImage("res/img/Banana.png");
			break;
		case 2:
			food.loadImage("res/img/Guava.png");
			break;
		case 3:
			food.loadImage("res/img/Orange.png");
			break;
		case 4:
			food.loadImage("res/img/Strawberry.png");
			break;
		default :
			food.loadImage("res/img/Apple.png");
			break;
		}
		
	}
	public void render(Graphics g) {
		food.render(g);
	}
}
