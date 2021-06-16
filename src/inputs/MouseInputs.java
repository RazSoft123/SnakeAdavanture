//The class handles the mouse inputs in the program

package inputs;

import java.awt.event.*;

import Constants.GameConstants;

import java.awt.Point;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private Point mousePoint;
	private boolean mouseClicked;
	public MouseInputs() {
		mousePoint = new Point();
		mouseClicked = false;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mousePoint = e.getPoint();
	}
	
	public boolean insideYesButton() {
		if(mousePoint.x > GameConstants.W_WIDTH/2 - 180 && mousePoint.x < GameConstants.W_WIDTH/2 - 60)
			if(mousePoint.y > GameConstants.W_Height/2 + 20 && mousePoint.y < GameConstants.W_Height/2 + 80)
				return true;
		return false;
	}
	
	public boolean insideNoButton() {
		
		if(mousePoint.x > GameConstants.W_WIDTH/2 + 30 && mousePoint.x < GameConstants.W_WIDTH/2 + 150)
			if(mousePoint.y > GameConstants.W_Height/2 + 20 && mousePoint.y < GameConstants.W_Height/2 + 80)
				return true;
		
		return false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	public boolean mouseGetClicked() {
		return mouseClicked;
	}

	public void mouseSetClicked(boolean mouseClick ) {
		mouseClicked = false;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseClicked = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseClicked = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
