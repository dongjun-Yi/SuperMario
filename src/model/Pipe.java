package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class Pipe extends GameObject {

	ImageLoader imgLoader = ImageLoader.getImageLoader();

	public Pipe(double x, double y, int mapWidthBoundary) {
		super(x, y, mapWidthBoundary);
		// TODO Auto-generated constructor stub
		width = height = GameSettings.scaledSize;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = imgLoader.getPipeImage();
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int) x, (int) y, width * 2, height * 2, null);
	}

}
