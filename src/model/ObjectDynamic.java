package model;

import java.awt.Rectangle;

import main.GameSettings;

public abstract class ObjectDynamic extends GameObject {

	protected int mapWidthBoundary;

	protected double xRightVel = 0;
	protected double xLeftVel = 0;
	protected double yVel = 0;
	protected double yGround = 625.0;

	protected int maxSpeed;

	protected int direction = 0; // 0 : right 1 : left
	protected int jumpDir = 0;

	protected boolean isItem = false;
	protected boolean isJump = false;
	protected boolean isCollided = false;

	protected boolean hasCollision = true;
	protected boolean hasGravity = true;

	public ObjectDynamic(double x, double y, int mapWidthBoundary) {
		super(x, y);
		this.mapWidthBoundary = mapWidthBoundary;
	}

	public boolean isItem() {
		return isItem;
	}

	public boolean isCollided() {
		return isCollided;
	}

	public void setCollided(boolean isCollided) {
		this.isCollided = isCollided;
	}

	public boolean hasCollision() {
		return hasCollision;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public void initVelocity() {
		xRightVel = 0.0;
		xLeftVel = 0.0;
		yVel = 0.0;
	}

	public void landing(double yGround) {
		this.yGround = yGround;
		isJump = false;
	}

	public void changeDir() {
		xLeftVel *= -1; // 방향 전환
		direction = (direction + 1) % 2;
	}

	public void setyGround(double yGround) {
		this.yGround = yGround;
	}

	public void updatesCoordinate() {
		x += (xRightVel + xLeftVel);
		y += yVel;

		// gravity
		if (hasGravity) {
			yVel += GameSettings.gravity;
		}
		// grounded 충돌 구현하면 수정될 부분
		if (hasCollision && y + height >= yGround) {
			isJump = false;
			y = yGround - height;
			yVel = 0.0;
		}
		// map boundary
		if (x <= 0)
			this.x = 0;
		if (x >= this.mapWidthBoundary - width)
			this.x = mapWidthBoundary - width;
	}

	public void setyVel(double yVel) {
		this.yVel = yVel;
	}

	public boolean isJump() {
		return isJump;
	}

	public void setJump(boolean isJump) {
		this.isJump = isJump;
	}

	public double getyVel() {
		return yVel;
	}

	public void setxRightVel(double xRightVel) {
		this.xRightVel = xRightVel;
	}

	public void setxLeftVel(double xLeftVel) {
		this.xLeftVel = xLeftVel;
	}

	public Rectangle getBottomHitbox() {
		return new Rectangle((int) x + 8, (int) y + height - 7, width - 16, 5 + (int) yVel);
	}

	public abstract void attacked(double x);
	public abstract void move();
}
