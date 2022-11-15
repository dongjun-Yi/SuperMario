package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class BlockBlocked extends ObjectStatic {

	private ImageLoader imgLoader = ImageLoader.getImageLoader();

	public BlockBlocked(double x, double y) {
		super(x, y);
		width = height = GameSettings.scaledSize;
	}

	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = imgLoader.getBlockedBlockImage();
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int) x, (int) y, null);
	}

}
