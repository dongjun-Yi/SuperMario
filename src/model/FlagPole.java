package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GameSettings;

public class FlagPole extends ObjectStatic {

	public FlagPole(double x, double y) {
		super(x, y);
		width = GameSettings.scaledSize - 18;
		height = GameSettings.scaledSize * 11;
	}

	@Override
	public int touch() {
		return 0;
	}

	@Override
	public Rectangle getHitbox() {
		return new Rectangle((int)x, (int)y, width, height);
	}

	@Override
	public BufferedImage getCurrentImage() {
		return null;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawRect((int)x, (int)y, width, height);
	}

}
