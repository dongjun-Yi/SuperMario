package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class GroundBlock extends GameObject {

	private ImageLoader imgLoader = ImageLoader.getImageLoader();

	public GroundBlock(double x, double y, int mapWidthBoundary) {
		super(x, y, mapWidthBoundary);
		width = height = GameSettings.scaledSize;
		hasGravity = false;
	}

	@Override
	public void move() {

	}

	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = imgLoader.getGroundBlockImage();
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int)x, (int)y, null);
	}

}
