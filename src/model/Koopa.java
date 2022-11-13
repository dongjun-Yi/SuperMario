package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class Koopa extends GameObject {

	private boolean isMoving = true;	// !isMoving && isHide(등껍질 상태) 일 땐, 플레이어와 닿으면 공격받지 않고 등껍질이 밀어짐
	private boolean isHide = false;
	
	public Koopa(double x, double y, int mapWidthBoundary) {
		super(x, y, mapWidthBoundary);
		width = GameSettings.scaledSize;
		height = 24 * GameSettings.scale;
		xLeftVel = -2;
		direction = 1;
	}
	
	private void hide() {
		isMoving = false;
		isHide = true;
		height = GameSettings.scaledSize;
		initVelocity();
	}
	
	public void touched(double playerX) {
		isMoving = true;
		
		this.direction = (playerX <= x + width/2) ? 0 : 1;	// 플레이어가 등껍질 왼쪽에서 친다면 오른쪽으로 진행
		if(direction == 0)
			xLeftVel = 8;
		else
			xLeftVel = -8;
	}
	
	private BufferedImage attackedAnimation() {
		ImageLoader imageLoader = ImageLoader.getImageLoader();
		return imageLoader.getKoopaCarapaceImage();
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
		
		return imageLoader.getKoopaCurrentImage(animationIdx, direction, width/3, height/3);
	}
	
	int cnt = 0;
	@Override
	public void move() {	
		// 충돌 구현하면 수정될 부분
		if(x + xLeftVel >= this.mapWidthBoundary - width || 
				x + xLeftVel >= 400 || // 임시
				x + xLeftVel <= 0) {
			xLeftVel *= -1;	// 방향 전환
			direction = (direction + 1) % 2;
		}
		updatesCoordinate();
	}

	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = null;
		if(isHide)	  img = attackedAnimation();
		else 	 	  img = movingAnimation(10);
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int)x, (int)y, width, height,  null);
	}
}
