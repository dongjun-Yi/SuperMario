package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class CoinItem extends GameObject{
	
	private int destroyCnt = 0;
	private boolean destroy = false; 
	private ImageLoader imgLoader = ImageLoader.getImageLoader();

	public CoinItem(double x, double y, int mapWidthBoundary) {
		super(x, y, mapWidthBoundary);
		width = height = GameSettings.scaledSize;
		frameCount = 0;
		
		yVel = -16;	// first do jump animation
	}

	@Override
	public void move() {
		if(!destroy)
			updatesCoordinate();
	}

	public BufferedImage coinAnimation(int speed) {
		
		if(destroyCnt > 40)	// 애니메이션이 끝나면 제거
			destroy = true;
		
		if(frameCount >= speed) {
			animationIdx++;
			frameCount = 0;
		}
		
		frameCount++;
		destroyCnt++;
		
		if(animationIdx >= 3)
			animationIdx = 0;
		
		return imgLoader.getCoinItemImage(animationIdx);
	}
	
	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = coinAnimation(3);
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int) x, (int) y, width, height, null);
	}

}
