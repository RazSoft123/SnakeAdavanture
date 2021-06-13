/**
 * 
 */
package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Rohit Raz Chandravanshi
 * @category Input handling
 * @version 1.0
 * @since 06/07/2021
 *
 */
public class KeyboardInputs implements KeyListener {

	private int[] polled;
	private boolean[] keys;
	
	public KeyboardInputs() {
		polled = new int[256];
		keys = new boolean[256];
	}
	
	public boolean keyDown(int keyCode) {
		return polled[keyCode] > 0;
	}
	
	public boolean keyDownOnce(int keyCode) {
		return polled[keyCode] == 1;
	}
	
	public synchronized void poll() {
		for(int i = 0; i < polled.length; i++) {
			if(keys[i]) {
				polled[i]++;
			}else {
				polled[i] = 0;
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		int keyCode = e.getKeyCode();
		if(keyCode >= 0 && keyCode < keys.length) {
			keys[keyCode] = true;
		}
		//System.out.println("THis key is get pressed" + e.getKeyCode() + " and " + e.getKeyChar());
		
	}

	@Override
	public synchronized void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		if(keyCode >=0 && keyCode < keys.length ) {
			keys[keyCode] = false;
		}

	}

}
