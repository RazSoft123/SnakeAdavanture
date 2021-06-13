// The class represents the snake in the game 
package objects;

import java.util.ArrayList;
import java.awt.*;

import Constants.GameConstants;

public class Snake {

	private Box face;
	private ArrayList<Box> body;
	private int direction;
	private double deltaMove;
	private Box preFaceLocation;
	private int levelVel;
	
	public Snake() {
		face = new Box();
		body = new ArrayList<Box>();
		direction = GameConstants.RIGHT;
		preFaceLocation = new Box();
		deltaMove = 0.0f;
		levelVel = 4;
	}
	public Point getFaceLocation() {
		return face.getLocation();
	}
	public void setFaceLocation(Point location) {
		face.setLocation(location.x, location.y);
	}
	public void setLevelVel(int levelVel) {
		this.levelVel = levelVel;
	}
	public int getX() {
		return face.getX();
	}
	public int getY() {
		return face.getY();
	}
	public void setX(int x) {
		 face.setX(x);
	}
	public void setY(int y) {
		face.setY(y);
	}
	public int getLevelVel() {
		return levelVel;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getDirection() {
		return direction;
	}
	public boolean foodEaten(Point point) {
		//for upper side eat
		if(face.getX() >= point.x && face.getX() <= point.x + 16 && face.getY() >= point.y && face.getY() <= point.y + 16)
			return true;
		
		//for down side eat
		int dx = face.getX() + 16;
		int dy = face.getY() + 16;
		
		if(dx >= point.x && dx <= point.x + 16 && dy >= point.y && dy <= point.y + 16)
			return true;
			
		return false;
	}
	
	public void creatingSnake(int x, int y) {
		face.setLocation(x,y);
		face.loadImage("res/img/Face.png");
		
		addBody(face.getX() - 16, face.getY());
		addBody(body.get(0).getX() - 16, body.get(0).getY());
		addBody(body.get(1).getX() - 16, body.get(1).getY());
		
	}
	
	public boolean leftWorld() {
		if(face.getX() < 0 && face.getX() > GameConstants.W_WIDTH)
			return true;
		
		if(face.getY() < 0 && face.getY() > GameConstants.W_Height)
			return true;
		
		return false;
	}
	
	public void addBody() {
		addBody(body.get(body.size() - 1).getX(),body.get(body.size() - 1).getY());
	}
	
	public void addBody(int x, int y) {
	    Box	temp = new Box();
		temp.setLocation(x, y);
		temp.loadImage("res/img/Body.png");
		
		body.add(temp);
	}
	
	public void moveSnake(double time) {
		
		if(direction == GameConstants.UP) {
			deltaMove = deltaMove + 16 * time * levelVel;
			
			if(deltaMove > 16) {
				preFaceLocation.setLocation(face.getLocation());
				face.setY(face.getY() - (int)deltaMove);
				deltaMove -= 16;
				moveBody();
			}
		}
		else if(direction == GameConstants.DOWN) {
			deltaMove = deltaMove + 16 * time * levelVel;
			//System.out.println("Delta move " + deltaMove + " Delta time " + time + "Curretn Time " + System.currentTimeMillis());
			
			if(deltaMove > 16) {
				preFaceLocation.setLocation(face.getLocation());
				face.setY(face.getY() + (int)deltaMove);
				deltaMove -= 16;
				moveBody();
			}
		}else if(direction == GameConstants.LEFT) {
			deltaMove += 16 * time * levelVel;
			
			if(deltaMove > 16) {
				preFaceLocation.setLocation(face.getLocation());
				face.setX(face.getX() - (int)deltaMove);
				deltaMove -= 16;
				moveBody();
			}
		}else if(direction == GameConstants.RIGHT) {
			deltaMove += 16 * time * levelVel;
			
			if(deltaMove > 16) {
				preFaceLocation.setLocation(face.getLocation());
				face.setX(face.getX() + (int)deltaMove);
				deltaMove -= 16;
				moveBody();
			}
		}
		
	}
	public boolean checkFaceCollision(Point location) {
		if(location.x == face.getX() && location.y == face.getY())
			return true;
		return false;
	}
	public boolean checkBodyCollision(Point location) {
		for(int i = 0; i < body.size(); i++) {
			if(body.get(i).getX() == location.x && body.get(i).getY() == location.y) {
				System.out.println("Snake body Location" + body.get(i).getY());
				return true;
			}
		}
		return false;
	}
	private void moveBody() {
		
		for(int i = body.size() - 1; i > 0; i--) {
			body.get(i).setLocation(body.get(i - 1).getLocation());
		}
		body.get(0).setLocation(preFaceLocation.getLocation());
			
	}
	
	public void render(Graphics g) {
		face.render(g);
		Color temp = g.getColor();
		g.setColor(Color.RED);
		//g.drawString("( "+ face.getX() + " , " + face.getY() + " ) ", face.getX(), face.getY());
		g.setColor(temp);
		for(int i=0; i < body.size(); i++) {
			body.get(i).render(g);
		}
	}
}
