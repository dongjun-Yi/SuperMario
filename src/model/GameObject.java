package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class GameObject {
	protected int width, height;
	protected int mapWidthBoundary;
	
	protected double x, y;
	protected double xRightVel = 0;
	protected double xLeftVel = 0;
	protected double yVel = 0;
	protected int maxSpeed;
	
	protected BufferedImage image;
	
	protected int direction = 0;	// 0 : right 1 : left
	protected int jumpDir = 0;
	protected boolean isJump = false;
	
	protected boolean hasCollision = true;
	protected boolean hasGravity = true;
	
	public double getX() { return x; }
	public double getY() { return y; }
	
	public GameObject(int mapWidthBoundary) {
		this.mapWidthBoundary = mapWidthBoundary;
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
