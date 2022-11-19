package server;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class GameModelMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	private String playerName;
	private String code;
	// private String data;
	private double x, y;
	private boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;

	public boolean isUpPressed() {
		return upPressed;
	}

	public boolean isDownPressed() {
		return downPressed;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public boolean isSpacePressed() {
		return spacePressed;
	}

	public GameModelMsg(String playerName, String code) {
		this.playerName = playerName;
		this.code = code;
	}

	public GameModelMsg(String playerName, String code, double x, double y, boolean upPressed, boolean downPressed, boolean leftPressed,
			boolean rightPressed, boolean spacePressed) {
		this.x = x;
		this.y = y;
		this.playerName = playerName;
		this.code = code;
		this.upPressed = upPressed;
		this.downPressed = downPressed;
		this.leftPressed = leftPressed;
		this.rightPressed = rightPressed;
		this.spacePressed = spacePressed;
	}

	public String inputToString() {
		return "upPressed=" + upPressed + ", spacePressed=" + spacePressed
			+ "\nleftPressed=" + leftPressed + ", rightPressed=" + rightPressed;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
