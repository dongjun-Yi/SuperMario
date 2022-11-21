package server;

import java.io.Serializable;

public class GameModelMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	private String playerName;
	private String code;
	// private String data;
	private double x, y;
	private double xLeftVel, xRightVel, yVel;
	private boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;
	private int playerNum = 0;
	private int randomSeedNumber = 0;

	public int getRandomSeedNumber() {
		return randomSeedNumber;
	}

	public int getPlayerNum() {
		return playerNum;
	}

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

	public GameModelMsg(String playerName, String code, int playerNum, int randomSeedNumber) {
		this.playerName = playerName;
		this.code = code;
		this.playerNum = playerNum;
		this.randomSeedNumber = randomSeedNumber;
	}

	public GameModelMsg(String playerName, String code, double x, double y, double xLeftVel, double xRightVel,
			double yVel, boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed,
			boolean spacePressed) {
		this.x = x;
		this.y = y;
		this.xLeftVel = xLeftVel;
		this.xRightVel = xRightVel;
		this.yVel = yVel;

		this.playerName = playerName;
		this.code = code;
		this.upPressed = upPressed;
		this.downPressed = downPressed;
		this.leftPressed = leftPressed;
		this.rightPressed = rightPressed;
		this.spacePressed = spacePressed;
	}

	public double getxLeftVel() {
		return xLeftVel;
	}

	public double getxRightVel() {
		return xRightVel;
	}

	public double getyVel() {
		return yVel;
	}

	public String posToString() {
		return "x=" + x + ", y=" + y;
	}

	public String velToString() {
		return "xLeftVel=" + xLeftVel + ", xRightVel=" + xRightVel + "\nyVel=" + yVel;
	}

	public String inputToString() {
		return "upPressed=" + upPressed + ", spacePressed=" + spacePressed + "\nleftPressed=" + leftPressed
				+ ", rightPressed=" + rightPressed;
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
