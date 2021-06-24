//This is a main Window which get displayed when user starts the game.
package uiwindow;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Constants.*;
import levels.*;
import objects.*;
import inputs.*;
import info.*;

public class StartWindow extends JFrame implements Runnable {

	public JPanel mainPanel;
	private Thread gameThread;
	private Canvas drawableCanvas;
	private BufferStrategy bs;
	public volatile boolean running;
	private Level level;
	private Snake snake;
	private Food food;
	private boolean gameOver;
	private KeyboardInputs keyboard;
	private Score score;
	private Grasses grass;
	private Data data;
	private MouseInputs mouse;

	public StartWindow() {
		keyboard = new KeyboardInputs();
		setTitle(Constants.GameConstants.W_TITLE);
		setBackground(Color.BLACK);
		setSize(GameConstants.W_WIDTH + 30, GameConstants.W_Height + 50);
		setResizable(true);
		data = new Data();
		mouse = new MouseInputs();
	}

	protected void createAndShowGUI() {
		drawableCanvas = new Canvas();
		
		drawableCanvas.setSize(GameConstants.W_WIDTH,GameConstants.W_Height);
		drawableCanvas.setBackground(Color.GREEN);
		drawableCanvas.setLocation((getContentPane().getWidth() - drawableCanvas.getWidth())/2, (getContentPane().getHeight() - drawableCanvas.getHeight())/2);
		System.out.println(" canvas location : " + drawableCanvas.getLocation().toString());

		// Taking keyboard inputs
		drawableCanvas.addKeyListener(keyboard);
		
		//Taking mouse input
		drawableCanvas.addMouseListener(mouse);
		drawableCanvas.addMouseMotionListener(mouse);
		
		setIgnoreRepaint(true);
		getContentPane().add(drawableCanvas);
		getContentPane().setBackground(Color.BLACK);
		setLayout(null);
		setVisible(true);
		drawableCanvas.requestFocus();

		// Making drawable canvas
		drawableCanvas.createBufferStrategy(2);
		bs = drawableCanvas.getBufferStrategy();

		gameThread = new Thread(this);
		gameThread.start();
	}

	protected void sleep(long millSec) {
		try {
			Thread.sleep(millSec);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
			System.err.println("Error while sleeping thread" + Thread.currentThread());
		}
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Level getLevel() {
		return level;
	}

	public void run() {

		running = true;
		gameOver = false;
		level = data.getLevel();
		snake = new Snake();
		food = new Food();
		score = new Score();
		grass = new Grasses();
		snake.creatingSnake(GameConstants.W_WIDTH / 2, GameConstants.W_Height / 2);
		snake.setLevelVel(data.getSnakeVel());
		grass.createGrasses();
		food.createFood(snake, level);
		long curNanoSec = System.nanoTime();
		long lastNanoSec = curNanoSec;
		while (running) {
			curNanoSec = System.nanoTime();
			gameLoop(((curNanoSec - lastNanoSec) / 1.0E9));
			// System.out.println("I'm running");
			sleep(GameConstants.G_WAIT_TIME);
			lastNanoSec = curNanoSec;
		}
	}

	protected void gameLoop(double timeDelta) {
		processInput(timeDelta);
		processObjects(timeDelta);
		renderFrame();
	}

	private void renderFrame() {
		do {
			Graphics g = null;
			do {
				try {
					g = bs.getDrawGraphics();
					g.clearRect(0, 0, getWidth(), getHeight());
					render(g);
				} finally {
					if (g != null) {
						g.dispose();
					}
				}
			} while (bs.contentsLost());
			bs.show();
		} while (bs.contentsRestored());
	}

	protected void processInput(double timeDelta) {
		keyboard.poll();
		if (!gameOver) {
				if (keyboard.keyDownOnce(KeyEvent.VK_UP)) {
					if (snake.getDirection() != GameConstants.DOWN)
						snake.setDirection(GameConstants.UP);
				}
				if (keyboard.keyDownOnce(KeyEvent.VK_DOWN)) {
					if (snake.getDirection() != GameConstants.UP)
						snake.setDirection(GameConstants.DOWN);
				}
				if (keyboard.keyDownOnce(KeyEvent.VK_RIGHT)) {
					if (snake.getDirection() != GameConstants.LEFT)
						snake.setDirection(GameConstants.RIGHT);
				}
				if (keyboard.keyDownOnce(KeyEvent.VK_LEFT)) {
					if (snake.getDirection() != GameConstants.RIGHT)
						snake.setDirection(GameConstants.LEFT);
				}
		}else {
			if(mouse.mouseGetClicked() && mouse.insideYesButton()) {
				gameOver = false;
				mouse.mouseSetClicked(false);
				startAgain();
			}
			else if(mouse.mouseGetClicked() && mouse.insideNoButton()) {
				getContentPane().remove(drawableCanvas);
				mouse.mouseSetClicked(false);
				repaint();
				revalidate();
				showMenu();
			}
		}
	}

	private void startAgain() {
		snake.creatingSnake(GameConstants.W_WIDTH/2, GameConstants.W_Height/2);
		snake.setDirection(GameConstants.RIGHT);
		score = new Score();
		level = data.getLevel();
		food.createFood(snake, level);
		gameOver = false;
	}
	
	protected void processObjects(double timeDelta) {
		if (!gameOver) {
			level.guideSnake(snake);
			snake.moveSnake(timeDelta);
			//System.out.println("Snake location " + snake.getFaceLocation().toString());
			if (level.checkCollision(snake.getFaceLocation())) {
				gameOver = true;
				System.out.println("Game over by level");
			}
			if (snake.checkBodyCollision(snake.getFaceLocation())) {
				gameOver = true;
				System.out.println("Game over by Body");
			}
			if (snake.foodEaten(food.getLocation())) {
				food.createFood(snake, level);
				score.increseScore(snake.getLevelVel());
				snake.addBody();
			}
		}
	}

	protected void render(Graphics g) {

		g.setColor(Color.GREEN);
		level.render(g);
		grass.render(g);
		Color temp = g.getColor();
		g.setColor(Color.PINK);
		g.fillRect(food.getLocation().x - 8, food.getLocation().y - 8, 32, 32);
		g.setColor(temp);
		snake.render(g);
		food.render(g);
		score.render(g);
		if (gameOver) {
			g.setColor(Color.black);
			g.fillRect(GameConstants.W_WIDTH / 3, GameConstants.W_Height / 3, 300, 100);
			g.setColor(Color.RED);

			Font tempFont = g.getFont();
			tempFont = tempFont.deriveFont((float) 30.0);
			g.setFont(tempFont);
			g.drawString("Game Over !!!", GameConstants.W_WIDTH / 3 + 50, GameConstants.W_Height / 3 + 50);
			g.drawString("Score :  " + score.getScore(), GameConstants.W_WIDTH / 3 + 85,
					GameConstants.W_Height / 3 + 85);
			g.setFont(tempFont);
			sleep(100);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, GameConstants.W_WIDTH, GameConstants.W_Height);
			tempFont = tempFont.deriveFont((float)50.0);
			g.setFont(tempFont);
			g.setColor(Color.WHITE);
			g.drawString("Play again !!", GameConstants.W_WIDTH/2 - 150, GameConstants.W_Height/2 - 30);
			g.setColor(Color.RED);
			g.fillRect(GameConstants.W_WIDTH/2 - 180, GameConstants.W_Height/2 + 20, 120, 60);
			g.fillRect(GameConstants.W_WIDTH/2+30, GameConstants.W_Height/2 + 20, 120, 60);
			
			if(mouse.insideYesButton())
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.BLACK);
			g.fillRect(GameConstants.W_WIDTH/2 - 175, GameConstants.W_Height/2 + 25, 110, 50);
			
			if(mouse.insideNoButton())
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.BLACK);
			g.fillRect(GameConstants.W_WIDTH/2 + 35, GameConstants.W_Height/2 + 25, 110, 50);
			
			tempFont = tempFont.deriveFont((float)20.0);
			g.setFont(tempFont);
			g.setColor(Color.RED);
			g.drawString("YES", GameConstants.W_WIDTH/2 - 140, GameConstants.W_Height/2 + 55 );
			g.drawString("NO", GameConstants.W_WIDTH/2 + 75, GameConstants.W_Height/2 + 55 );
			
			
		}
	}

	protected void closeWindow() {
		if(gameThread != null) {
		try {
			running = false;
			gameThread.join();
			System.out.println("I'm stopped");
		} catch (InterruptedException ie) {
			ie.printStackTrace();
			System.err.println("An error occoured in stoping gameThread");
		}
	}
		System.exit(0);
	}

	public void showWelcomeScreen() {

		Canvas canvas = new Canvas();
		canvas.setSize(GameConstants.W_WIDTH, GameConstants.W_Height);
		canvas.setBackground(Color.BLACK);
		getContentPane().add(canvas);
		setVisible(true);

		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		do {
			Graphics g = null;
			do {
				try {
					g = bs.getDrawGraphics();
					g.clearRect(0, 0, getWidth(), getHeight());
					renderStart(g);
				} finally {
					if (g != null) {
						g.dispose();
					}
				}
			} while (bs.contentsLost());
			bs.show();
		} while (bs.contentsRestored());
		sleep(30);
		getContentPane().remove(canvas);
		repaint();
	}

	public void renderStart(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameConstants.W_WIDTH, GameConstants.W_Height);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Cooper", Font.BOLD, 60));
		g.drawString("RazSoft", GameConstants.W_WIDTH / 3, GameConstants.W_Height / 2);
		g.setColor(Color.RED);
		g.fillRect(GameConstants.W_WIDTH / 3, GameConstants.W_Height / 2 + 10, 230, 5);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Cooper", Font.BOLD, 30));
		g.drawString("Snake Advanture", GameConstants.W_WIDTH / 3, GameConstants.W_Height / 2 + 45);
		/*
		 * Snake sSnake = new Snake(); sSnake.creatingSnake(GameConstants.W_WIDTH/3,
		 * GameConstants.W_Height/2 + 55); sSnake.render(g); sleep(100);
		 */
	}

	public void showMenu() {
		JPanel jpanel = new JPanel();
		jpanel.setSize(GameConstants.W_WIDTH, GameConstants.W_Height);
		jpanel.setLocation(0, 0);
		jpanel.setLayout(null);
		
		JButton play = new JButton("PLAY");
		JButton settings = new JButton("SETTING");
		JButton about = new JButton("ABOUT");
		JButton exit = new JButton("EXIT");
		
		play.setSize(90, 30);
		play.setLocation(GameConstants.W_WIDTH / 2 - 30, GameConstants.W_Height / 3);
		jpanel.add(play);
		
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						createAndShowGUI();
					}
				});
				remove(jpanel);
				repaint();
				revalidate();
			}
			
		});

		//Setting button 
		settings.setSize(90, 30);
		settings.setLocation(GameConstants.W_WIDTH / 2 - 30, GameConstants.W_Height / 3 + 40);
		jpanel.add(settings);
		
		settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				jpanel.remove(play);
				jpanel.remove(settings);
				jpanel.remove(about);
				jpanel.remove(exit);
				
				JLabel levelValue = new JLabel();
				JLabel level = new JLabel("Level");
				level.setLocation(GameConstants.W_WIDTH/2-20, GameConstants.W_Height/2-40);
				level.setSize(40,10);
				jpanel.add(level);
				
				
				JSlider levelSlider = new JSlider(JSlider.HORIZONTAL, 1, 3,1);
				levelSlider.setLocation(GameConstants.W_WIDTH/2 -20, GameConstants.W_Height/2 - 20);
				levelSlider.setSize(100, 20);
				levelSlider.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						levelValue.setText(" " + levelSlider.getValue());
					}
					
				});
				jpanel.add(levelSlider);
			
				levelValue.setLocation(GameConstants.W_WIDTH/2 + 20, GameConstants.W_Height/2-40);
				levelValue.setSize(40,10);
				levelValue.setText(" " + levelSlider.getValue());
				jpanel.add(levelValue);
				
				JLabel speedValue = new JLabel();
				JLabel speed = new JLabel("Speed");
				speed.setLocation(GameConstants.W_WIDTH/2-20, GameConstants.W_Height/2);
				speed.setSize(40,20);
				jpanel.add(speed);
				
				speedValue.setLocation(GameConstants.W_WIDTH/2 + 20, GameConstants.W_Height/2);
				speedValue.setSize(40,20);
				
				JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 5,1);
				speedSlider.setLocation(GameConstants.W_WIDTH/2 -20, GameConstants.W_Height/2 + 20);
				speedSlider.setSize(100, 20);
				speedSlider.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						speedValue.setText(" " + speedSlider.getValue());
					}
					
				});
				jpanel.add(speedSlider);
				speedValue.setText(" " + speedSlider.getValue());
				jpanel.add(speedValue);
				
				JButton ok = new JButton("OK");
				ok.setLocation(GameConstants.W_WIDTH/2 , GameConstants.W_Height/2 + 40);
				ok.setSize(60,30);
				ok.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						switch(levelSlider.getValue()) {
						case 1:
							data.setLevel(new LevelZero());
							break;
						case 2:
							data.setLevel(new LevelObe());
							break;
						case 3:
							data.setLevel(new LevelTwo());
							break;
						}
						
						data.setSnakeVel(speedSlider.getValue() * 4);
						
						jpanel.remove(speedSlider);
						jpanel.remove(levelSlider);
						jpanel.remove(levelValue);
						jpanel.remove(speedValue);
						jpanel.remove(speed);
						jpanel.remove(level);
						jpanel.remove(ok);
						
						jpanel.add(play);
						jpanel.add(settings);
						jpanel.add(about);
						jpanel.add(exit);
						
						jpanel.repaint();
						jpanel.revalidate();
					}
					
				});
				jpanel.add(ok);
				
				jpanel.repaint();
				jpanel.revalidate();
				System.out.println("It's running inside settings");
			}
		});

		
		about.setSize(90, 30);
		about.setLocation(GameConstants.W_WIDTH / 2 - 30, GameConstants.W_Height / 3 + 80);
		jpanel.add(about);

		
		exit.setSize(90, 30);
		exit.setLocation(GameConstants.W_WIDTH / 2 - 30, GameConstants.W_Height / 3 + 120);
		jpanel.add(exit);
		
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				running = false;
				if(gameThread == null) {
					System.exit(0);
				}
			}
			
		});

		add(jpanel);
		repaint();
	}

	public static void main(String[] args) {
		final StartWindow app = new StartWindow();
		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				app.closeWindow();
			}
		});

		app.showWelcomeScreen();
		app.showMenu();	 
	}
}
