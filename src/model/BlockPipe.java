package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class BlockPipe extends ObjectStatic {

	private ImageLoader imgLoader = ImageLoader.getImageLoader();

	public BlockPipe(double x, double y) {
		super(x, y);
		width = height = GameSettings.scaledSize * 2;
	}

	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = imgLoader.getPipeImage();
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int) x, (int) y, null);
	}

}
