package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class ItemMushroom extends ObjectDynamic {

	private double startY;
	private boolean isMoving = false;
	private ImageLoader imgLoader = ImageLoader.getImageLoader();

	public ItemMushroom(double x, double y, int mapWidthBoundary) {
		super(x, y, mapWidthBoundary);
		width = height = GameSettings.scaledSize;
		isItem = true;

		// 생성될 때 애니메이션
		startY = y;
		hasGravity = false;
		yVel = -5;
	}

	public void apearAnimation() {
		if (startY - y >= GameSettings.scaledSize - 8) {
			initVelocity();
			frameCount++;

			if (frameCount >= 13) {
				hasGravity = true;
				isMoving = true;
				xLeftVel = 4.4;
			}
		}
	}

	@Override
	public void move() {
		if (!isMoving) {
			apearAnimation();
		} else {
			// 충돌 구현하면 수정될 부분
			if (x + xRightVel >= this.mapWidthBoundary - width || x + xRightVel <= 0)
				changeDir();
		}
	}

	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = imgLoader.getMushroomImage();
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int) x, (int) y, width, height, null);

		// HitBox 표시
		g2.drawRect((int) (x + xLeftVel + xRightVel) + 10, (int) y + 10, width - 20, height - 20);
		g2.drawRect((int) x + 8, (int) y + height - 7, width - 16, 5 + (int) yVel);
	}

	@Override
	public Rectangle getHitbox() {
		return new Rectangle((int) (x + xLeftVel + xRightVel) + 10, (int) y + 10, width - 20, height - 20);
	}

	@Override
	public void attacked(double x) {	
	}
}
