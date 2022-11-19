package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class BlockBrick extends ObjectStatic {

	protected double yVel;
	private ImageLoader imgLoader = ImageLoader.getImageLoader();

	public BlockBrick(double x, double y) {
		super(x, y);
		width = height = GameSettings.scaledSize;
	}

	@Override
	public int touch() {
		yVel = -3.0;
		isTouched = true;
		return 0;
	}

	public void touchedAnimation() {
		if (frameCount >= 14) {
			yVel = 0;
			frameCount = 0;
			isTouched = false;
			return;
		}

		if (frameCount >= 7) {
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
		if (isTouched)
			touchedAnimation();
		g2.drawImage(getCurrentImage(), (int) x, (int) y, null);
	}

	@Override
	public Rectangle getHitbox() {
		return new Rectangle((int) x, (int) y, width, height);
	}
}
