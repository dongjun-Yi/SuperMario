package model;

import java.awt.Graphics2D;

public abstract class GameObject {
	protected int width, height;
	protected int mapWidthBoundary;
	
	protected double x, y;
	protected double xRightVel = 0;
	protected double xLeftVel = 0;
	protected double yVel = 0;
	protected int maxSpeed;
	
	protected int direction = 0;	// 0 : right 1 : left
	protected int jumpDir = 0;
	
	protected boolean isJump = false;
	protected boolean hasCollision = true;
	protected boolean hasGravity = true;
	
	public GameObject(double x, double y, int mapWidthBoundary) {
		this.x = x;
		this.y = y;
		this.mapWidthBoundary = mapWidthBoundary;
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	public boolean hasCollision() { return hasCollision; }
	
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	public void setWidth(int width) { 
		this.width = width;
	}
	
	public void setHeight(int height) { 
		this.height = height;
	}
	
	public void updatesCoordinate() {
		x += (xRightVel + xLeftVel);
		y += yVel;
		
		// gravity
		if(hasGravity)
			yVel += 0.65;	
		// grounded
		if(hasCollision && y + height >= 625.0) {
			isJump = false;
			y = 625.0 - height;
			yVel = 0.0;
		}
		// map boundary
		if(x <= 0)
			this.x = 0;
		if(x >= this.mapWidthBoundary - width)
			this.x = mapWidthBoundary - width;
	}
	
	public abstract void move();
	public abstract void draw(Graphics2D g2);
}
