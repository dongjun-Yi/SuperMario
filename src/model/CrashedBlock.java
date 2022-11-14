package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class CrashedBlock extends GameObject {

	private ImageLoader imgLoader = ImageLoader.getImageLoader();

	public CrashedBlock(double x, double y, int mapWidthBoundary) {
		super(x, y, mapWidthBoundary);
		width = height = GameSettings.scaledSize;
		hasGravity = false;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = imgLoader.getCrashedBlockImage();
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int) x, (int) y, null);
	}

}
