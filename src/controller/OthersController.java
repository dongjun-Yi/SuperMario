package controller;

import java.awt.event.KeyEvent;

public class OthersController implements Controller {

	private boolean upPressed, downPressed, leftPressed, rightPressed;
	private boolean spacePressed;
	
	@Override
	public void initKey() {
		this.upPressed = false;
		this.downPressed = false;
		this.leftPressed = false;
		this.rightPressed = false;
		this.spacePressed = false;
	}
	
	public void setKeyPressed(boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed, boolean spacePressed) {
		this.upPressed = upPressed;
		this.downPressed = downPressed;
		this.leftPressed = leftPressed;
		this.rightPressed = rightPressed;
		this.spacePressed = spacePressed;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_UP) {
			System.out.println("up");
			upPressed = true;
		}
		if(code == KeyEvent.VK_DOWN) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_LEFT) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_UP) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
	}

	@Override
	public boolean getUpPressed() { return upPressed; }

	@Override
	public boolean getDownPressed() { return downPressed; }

	@Override
	public boolean getLeftPressed() { return leftPressed; }

	@Override
	public boolean getRightPressed() { return rightPressed; }

	@Override
	public boolean getSpacePressed() { return spacePressed; }
}
