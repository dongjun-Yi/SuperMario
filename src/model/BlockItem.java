package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class BlockItem extends ObjectStatic {

	private int itemNum = 0;
	private ImageLoader imgLoader = ImageLoader.getImageLoader();

	public BlockItem(double x, double y, int itemNum) {
		super(x, y);
		width = height = GameSettings.scaledSize;
		this.itemNum = itemNum;
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

	@Override
	public Rectangle getHitbox() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	@Override
	public int touch() {
		return itemNum;
	}
}
