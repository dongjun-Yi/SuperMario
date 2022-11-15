package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class EnemyGoomba extends GameObject {
	
	private boolean isDie = false;
	private boolean destroy = false; 
	
	public EnemyGoomba(int x, int y, int mapWidthBoundary) {
		super(x, y, mapWidthBoundary);
		width = height = GameSettings.scaledSize;
		xLeftVel = -2;
	}
	
	public boolean isDestroy() { return destroy; }	// true면 제거
	
	public void die() {
		isDie = true;
		hasCollision = false;
		hasGravity = false;
		initVelocity();
		
		frameCount = 0;
	}
	
	private BufferedImage movingAnimation(int speed) {
		ImageLoader imageLoader = ImageLoader.getImageLoader();
		
		if(frameCount >= speed) {
			animationIdx++;
			frameCount = 0;
		}
		frameCount++;
		
		if(animationIdx >= 2)
			animationIdx = 0;
		
		return imageLoader.getGoombaCurrentImage(animationIdx);
	}
	
	private BufferedImage dyingAnimation() {
		frameCount++;
		if(frameCount >= 25) {
			destroy = true;
		}
		ImageLoader imageLoader = ImageLoader.getImageLoader();
		return imageLoader.getGoombaCurrentImage(2);
	}
	
	@Override
	public void move() {	
		// 충돌 구현하면 수정될 부분
		if(x + xLeftVel >= this.mapWidthBoundary - width || 
				x + xLeftVel >= 400 || // 임시
				x + xLeftVel <= 0)
			xLeftVel *= -1;	// 방향 전환
		updatesCoordinate();
	}

	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = null;
		if(isDie) img = dyingAnimation();
		else 	  img = movingAnimation(10);
		return img;
	}
	
	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int)x, (int)y, width, height,  null);
	}
}
