package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class Block extends GameObject{
	ImageLoader imgLoader = ImageLoader.getImageLoader();
	
	public Block(double x, double y, int mapWidthBoundary) {
		super(x, y, mapWidthBoundary);
		width = height = GameSettings.scaledSize;
	}

	@Override
	public void move() {
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
