package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import controller.Controller;
import main.GameSettings;
import view.ImageLoader;

public class Player extends GameObject {
	private boolean isPlayer1 = false;
	
	private Controller controller = null;
	private int animationIdx = 0;
	private int frameCount = 0;
	private boolean isDie = false;
	private boolean controlBlocked = false;
	
	public Player() {
		width = height = GameSettings.tileSize;
		setDefaultValues();
	}
	
	public void setIsPlayer1(boolean isPlayer1) {
		this.isPlayer1 = isPlayer1;
		if(isPlayer1)	x = 100.0;	// players have different x coordinate
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public void setDefaultValues() {
		x = (isPlayer1) ? 100.0 : 200.0;
		y = 300.0;
		xRightVel = 0.0;
		xLeftVel = 0.0;
		yVel = 0.0;
		maxSpeed = 7;
		isDie = false;
		hasCollision = true;
		controlBlocked = false;
	}
	
	public void jump(int power) {
		jumpDir = direction;
		isJump = true;
		yVel = -power;
	}

	public void die() {
		isDie = true;
		hasCollision = false;
		hasGravity = false;
		controlBlocked = true;
		xRightVel = 0.0;
		xLeftVel = 0.0;
		yVel = 0.0;
		
		frameCount = 0;
	}
	
	public void dyingAnimation() {
		frameCount++;
		if(y >= 800) {
			frameCount = 0;
			setDefaultValues();	// player respawn
		}
		if(frameCount == 20) {
			hasGravity = true;
			jump(14);
		}
	}
	
	public void attackedAnimation() {
		
	}
	
	public void inputUpdate() {
		if (!controlBlocked) {		
			if(controller.getRightPressed()) {
				direction = 0;
				
				xRightVel += 0.2;
				if(xRightVel >= maxSpeed)
					xRightVel = maxSpeed;
			} else {
				xRightVel -= 0.3;
				if(xRightVel <= 0.3)
					xRightVel = 0;
			}
			
			if(controller.getLeftPressed()) {
				direction = 1;
				
				xLeftVel -= 0.2;
				if(xLeftVel <= -maxSpeed)
					xLeftVel = -maxSpeed;
			} else {
				xLeftVel += 0.3;
				if(xLeftVel >= -0.3)
					xLeftVel = 0;
			}
			
			if(!isJump && controller.getUpPressed()) {
				jump(16);
			}
			
			if(controller.getSpacePressed()) {
				die();
			}
		}
		
		// update coordinate
		updatesCoordinate();
		
		//System.out.println("xRightVel: " + xRightVel);
		//System.out.println("xLeftVel: " + xLeftVel);
	}
	
	public BufferedImage getWalkAnimation(BufferedImage[][] marioImg, int direction, int speed) {
		BufferedImage img = marioImg[direction][animationIdx + 1];
		
		if(frameCount >= speed) {
			animationIdx++;
			frameCount = 0;
		}
		frameCount++;
		
		if(animationIdx >= 3)
			animationIdx = 0;
		
		return img;
	}
	
	public BufferedImage getCurrentImage() {
		ImageLoader imageLoader = ImageLoader.getImageLoader();;
		BufferedImage[][] marioImg = imageLoader.getPlayerImage(isPlayer1);
		BufferedImage img = marioImg[direction][0];
		
		// jumping image
		if(isJump) {
			img = marioImg[jumpDir][5];
		}
		else if ((xRightVel + xLeftVel) == 0)
			img = marioImg[direction][0];
		// turn direction image
		else if( (xLeftVel <= -4.5 && xRightVel < -xLeftVel && direction == 0) 
				|| (xRightVel >= 4.5 && -xLeftVel < xRightVel && direction == 1))
			img = marioImg[direction][4];
		// walking image
		else if ( (xLeftVel < 0) || (xRightVel > 0) ) {
			// animation speed is influenced by xVelocity
			int speed = (-xLeftVel > xRightVel) ? (int)-xLeftVel : (int)xRightVel;
			img = getWalkAnimation(marioImg, direction, 10 - speed);
		}
		
		if(isDie) {
			img = imageLoader.getPlayerDie(isPlayer1);
			dyingAnimation();
		}
		return img;
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int)x, (int)y, width, height, null);
	}
}
