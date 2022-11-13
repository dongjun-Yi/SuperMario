package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class Block extends GameObject{
	
	private boolean isTouched = false;
	private ImageLoader imgLoader = ImageLoader.getImageLoader();
	
	public Block(double x, double y, int mapWidthBoundary) {
		super(x, y, mapWidthBoundary);
		width = height = GameSettings.scaledSize;
		
		hasGravity = false;
		yVel = -3;
		
		isTouched = true;	// (임시) 애니메이션 확인용
	}

	public void touchedAnimation() {
		if(frameCount >= 14) {
			isTouched = false;
		}
		
		if(frameCount >= 7) {
			yVel = 3;
		}
			
		frameCount++;
	}
	
	@Override
	public void move() {
		if(isTouched) {
			touchedAnimation();
			updatesCoordinate();
		}
	}

	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = imgLoader.getBlockImage();
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int)x, (int)y, width, height,  null);
	}

}
