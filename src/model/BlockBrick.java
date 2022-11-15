package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class BlockBrick extends ObjectStatic {
	
	private double yVel;
	private boolean isTouched = false;
	private ImageLoader imgLoader = ImageLoader.getImageLoader();
	
	public BlockBrick(double x, double y) {
		super(x, y);
		width = height = GameSettings.scaledSize;
		
		yVel = -3.0;
		isTouched = true;	// (임시) 애니메이션 확인용
	}

	public void touchedAnimation() {		
		if(frameCount >= 14) {
			yVel = 0;
			frameCount = 0;
			isTouched = false;
			return;
		}
		
		if(frameCount >= 7) {
			yVel = 3.0;
		}
			
		frameCount++;
		y += yVel;
	}

	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = imgLoader.getBrickBlockImage();
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		if(isTouched)	touchedAnimation();
		g2.drawImage(getCurrentImage(), (int)x, (int)y, null);
	}

}
