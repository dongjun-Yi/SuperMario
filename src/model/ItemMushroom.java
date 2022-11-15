package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class ItemMushroom extends GameObject{
	
	private double startY;
	private boolean isMoving = false;
	private boolean destroy = false; 
	private ImageLoader imgLoader = ImageLoader.getImageLoader();

	public ItemMushroom(double x, double y, int mapWidthBoundary) {
		super(x, y, mapWidthBoundary);
		width = height = GameSettings.scaledSize;
		
		// 생성될 때 애니메이션
		startY = y;
		hasGravity = false;
		yVel = -3;
	}

	public void apearAnimation() {
		if(startY - y >= GameSettings.scaledSize) {
			initVelocity();
			frameCount++;
			
			if(frameCount >= 13) {
				hasGravity = true;
				isMoving = true;
				xRightVel = 3;
			}
		}	
	}
	
	@Override
	public void move() {
		if(!isMoving) {
			apearAnimation();
		}
		else {
			// 충돌 구현하면 수정될 부분
			if(x + xRightVel >= this.mapWidthBoundary - width || 
					x + xRightVel >= 600 || // 임시
					x + xRightVel <= 0)
				xRightVel *= -1;	// 방향 전환
		}
		updatesCoordinate();	
	}

	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = imgLoader.getMushroomImage();
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.drawImage(getCurrentImage(), (int)x, (int)y, width, height,  null);
	}

}
