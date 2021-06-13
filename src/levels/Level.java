// It's a base class for all levels in this game.
package levels;

import java.awt.Graphics;
import java.awt.Point;
import objects.Snake;

public abstract class Level {

	public abstract boolean checkCollision(Point point);
	public abstract void guideSnake(Snake snake);
	public abstract void render(Graphics g);
}
