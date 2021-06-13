//This will create grasses in the game
package objects;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;

public class Grasses {

	private ArrayList<Box> greenGrass;
	private ArrayList<Box> flowerGrass;
	private ArrayList<Box> flower;
	private ArrayList<Box> bigGrass;
	private Random rand;
	
	public Grasses() {
		greenGrass = new ArrayList<Box>();
		flowerGrass = new ArrayList<Box>();
		flower = new ArrayList<Box>();
		bigGrass = new ArrayList<Box>();
		rand = new Random();
	}
	
	public void createGrasses() {
		//For creating green grass land
		Box temp;
		int x = 0;
		int y = 0;
		for(int i =0; i < 50; i++) {
			temp = new Box();
			x = rand.nextInt(43) * 16 + 16;
			y = rand.nextInt(25) * 16 + 16;
			temp.setLocation(x,y);
			temp.loadImage("res/img/Grass3.png");
			greenGrass.add(temp);
		}
		
		//flowers Group
		for(int i = 0; i < 10; i++) {
			temp = new Box();
			x = rand.nextInt(43) * 16 + 16;
			y = rand.nextInt(25) * 16 + 16;
			temp.setLocation(x,y);
			temp.loadImage("res/img/Grass1.png");
			flowerGrass.add(temp);
		}
		
		//For single flowers
		for(int i = 0; i < 5; i++) {
			
			temp = new Box();
			x = rand.nextInt(43) * 16 + 16;
			y = rand.nextInt(25) * 16 + 16;
			temp.setLocation(x,y);
			temp.loadImage("res/img/Grass2.png");
			flower.add(temp);
		}
		
		//For BIG flower
		for(int i = 0; i < 2; i++) {
			temp = new Box();
			x = rand.nextInt(43) * 16 + 16;
			y = rand.nextInt(25) * 16 + 16;
			temp.setLocation(x,y);
			temp.loadImage("res/img/Grass4.png");
			bigGrass.add(temp);
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < greenGrass.size(); i++) {
			//System.out.println("Green grass location is " + greenGrass.get(i).getX() + " : " + greenGrass.get(i).getY());
			greenGrass.get(i).render(g);
		}
		
		for(int i = 0; i < flowerGrass.size(); i++)
			flowerGrass.get(i).render(g);
		
		for(int i = 0; i < flower.size(); i++)
			flower.get(i).render(g);
		
		for(int i = 0; i < bigGrass.size(); i++)
			bigGrass.get(i).render(g);
	}
}
