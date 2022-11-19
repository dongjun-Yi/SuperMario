package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class BlockHard extends ObjectStatic {

	private ImageLoader imgLoader = ImageLoader.getImageLoader();

	public BlockHard(double x, double y) {
		super(x, y);
		width = height = GameSettings.scaledSize;
	}

	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = imgLoader.getHardBlockImage();
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int) x, (int) y, null);
	}

	@Override
	public Rectangle getHitbox() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	@Override
	public int touch() {
		return 0;
	}
}
