package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class ItemBlock extends GameObject {

	private ImageLoader imgLoader = ImageLoader.getImageLoader();

	public ItemBlock(double x, double y, int mapWidthBoundary) {
		super(x, y, mapWidthBoundary);
		width = height = GameSettings.scaledSize;
		
		hasGravity = false;
		
	}
	
	@Override
	public void move() {
	}

	public BufferedImage animation(int speed) {
		frameCount++;
		
		if(frameCount >= speed) {
			if(animationIdx == 0 && frameCount <= speed * 2)
				;
			else {
				animationIdx++;
				frameCount = 0;
			}
		}
		
		if(animationIdx >= 3)
			animationIdx = 0;
		
		return imgLoader.getItemBlockImage(animationIdx);
	}
	
	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = animation(15);
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int) x, (int) y, width, height, null);

	}

}
