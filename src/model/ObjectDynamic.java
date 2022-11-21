package model;

import java.awt.Rectangle;

import main.GameSettings;

public abstract class ObjectDynamic extends GameObject {

	protected int mapWidthBoundary;
	protected int objectNum;
	
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
	private boolean isMoving = true; 
	
	protected boolean hasCollision = true;
	protected boolean hasGravity = true;

	public ObjectDynamic(double x, double y, int mapWidthBoundary) {
		super(x, y);
		this.mapWidthBoundary = mapWidthBoundary;
	}
	
	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public int getObjectNum() {
		return objectNum;
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
	
	public double getxRightVel() {
		return xRightVel;
	}

	public void setxRightVel(double xRightVel) {
		this.xRightVel = xRightVel;
	}

	public double getxLeftVel() {
		return xLeftVel;
	}

	public void setxLeftVel(double xLeftVel) {
		this.xLeftVel = xLeftVel;
	}

	public double getyVel() {
		return yVel;
	}

	public void setyVel(double yVel) {
		this.yVel = yVel;
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

	public boolean isJump() {
		return isJump;
	}

	public void setJump(boolean isJump) {
		this.isJump = isJump;
	}

	public Rectangle getBottomHitbox() {
		return new Rectangle((int) x + 8, (int) y + height - 7, width - 16, 5 + (int) yVel);
	}

	public Rectangle getHitboxSpace() {
		return new Rectangle((int) x - 2 * width, (int) y - 2 * height, width * 5, height * 5);
	}
	
	public boolean isItInHitboxSpace(double x, double y) {
		if(this.x - 2 * width <= x && x <= this.x + width * 5 &&
				this.y - 2 * height <= y && y <= this.y + height * 5) {
			return true;
		}
		return false;
	}
	
	public abstract void attacked(double x);
	public abstract void move();
}
