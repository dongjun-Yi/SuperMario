package controller;

import java.awt.event.KeyEvent;

import client.GameClient;
import main.GamePanel;

public class PlayerController implements Controller {

	private boolean upPressed, downPressed, leftPressed, rightPressed;
	private boolean spacePressed;
	private boolean keyPressedVal = false;

	public boolean isKeyPressedVal() {
		return keyPressedVal;
	}

	private boolean keyReleasedVal = false;

	public void setKeyReleasedVal(boolean keyReleasedVal) {
		this.keyReleasedVal = keyReleasedVal;
	}

	public boolean isKeyReleasedVal() {
		return keyReleasedVal;
	}

	@Override
	public void initKey() {
		this.upPressed = false;
		this.downPressed = false;
		this.leftPressed = false;
		this.rightPressed = false;
		this.spacePressed = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		keyPressedVal = true;
		keyReleasedVal = false;
		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		keyPressedVal = false;
		keyReleasedVal = true;

		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
	}

	@Override
	public boolean getUpPressed() {
		return upPressed;
	}

	@Override
	public boolean getDownPressed() {
		return downPressed;
	}

	@Override
	public boolean getLeftPressed() {
		return leftPressed;
	}

	@Override
	public boolean getRightPressed() {
		return rightPressed;
	}

	@Override
	public boolean getSpacePressed() {
		return spacePressed;
	}
}
