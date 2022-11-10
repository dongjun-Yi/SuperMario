package model;

import java.awt.image.BufferedImage;

public abstract class GameObject {
	protected int width, height;
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
	}
}
