//This class is a base class for all objects like snake, food and grasses.
package objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Box {

	private  Point location;
	private BufferedImage sprite;
	
	public Box() {
		location = new Point();
	}
	public void setLocation(Point location) {
		this.location.x = location.x;
		this.location.y = location.y;
	}
	
	public void setLocation(int x, int y) {
		location.x = x;
		location.y = y;
	}
	public void setX(int x) {
		location.x = x;
	}
	public void setY(int y) {
		location.y = y;
	}
	
	public int getX() {
		return location.x;
	}
	
	public int getY() {
		return location.y;
	}
	public Point getLocation() {
		return new Point(location.x, location.y);
	}
	
	public void loadImage(String imagePath) {
		try {
			sprite = ImageIO.read(new File(imagePath));
			//System.out.println("Loaded image is " + imagePath);
		}catch(IOException ie) {
			ie.printStackTrace();
			sprite = null;
			System.err.println("Can't load image");
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, location.x, location.y, null);
	}
}
