//The class contain all the data about the game
package info;

import levels.*;

public class Data {
	
	private Level level;
	private int snakeVel;
	
	public Data(){
		level = new LevelZero();
		snakeVel = 4;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public  int getSnakeVel() {
		return snakeVel;
	}
	
	public void setSnakeVel(int snakeVel) {
		this.snakeVel = snakeVel;
	}
}
