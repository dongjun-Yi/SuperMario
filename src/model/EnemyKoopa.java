package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GameSettings;
import view.ImageLoader;

public class EnemyKoopa extends ObjectDynamic {

	private boolean isHide = false;// !isMoving && isHide(등껍질 상태) 일 땐, 플레이어와 닿으면 공격받지 않고 등껍질이 밀어짐

	public EnemyKoopa(double x, double y, int mapWidthBoundary) {
		super(x, y, mapWidthBoundary);
		width = GameSettings.scaledSize;
		height = 24 * GameSettings.scale;
		xLeftVel = -2;
		direction = 1;
		
		objectNum = 10;
	}

	private void hide() {
		setMoving(false);
		isHide = true;
		height = GameSettings.scaledSize;
		initVelocity();
	}

	public boolean isHide() {
		return isHide;
	}
	
	public void touched(double playerX) {
		if (!isMoving()) {
			setMoving(true);

			this.direction = (playerX <= x + width / 2) ? 0 : 1; // 플레이어가 등껍질 왼쪽에서 친다면 오른쪽으로 진행
			if (direction == 0)
				xLeftVel = 8;
			else
				xLeftVel = -8;
		} else {
			hide();
		}
	}

	private BufferedImage attackedAnimation() {
		ImageLoader imageLoader = ImageLoader.getImageLoader();
		return imageLoader.getKoopaCarapaceImage();
	}

	private BufferedImage movingAnimation(int speed) {
		ImageLoader imageLoader = ImageLoader.getImageLoader();

		if (frameCount >= speed) {
			animationIdx++;
			frameCount = 0;
		}
		frameCount++;

		if (animationIdx >= 2)
			animationIdx = 0;

		return imageLoader.getKoopaCurrentImage(animationIdx, direction, width / 3, height / 3);
	}

	@Override
	public void move() {
		// 충돌 구현하면 수정될 부분
		if (x + xLeftVel >= this.mapWidthBoundary - width || x + xLeftVel <= 0) {
			changeDir();
		}
	}

	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = null;
		if (isHide)
			img = attackedAnimation();
		else
			img = movingAnimation(10);
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int) x, (int) y, width, height, null);

		// HitBox 표시
		//g2.drawRect((int) (x + xLeftVel + xRightVel) + 6, (int) y + height / 2 - 12, width - 12, height - height / 2);
		//g2.drawRect((int) x + 8, (int) y + height - 7, width - 16, 5 + (int) yVel);
	}

	@Override
	public Rectangle getHitbox() {
		return new Rectangle((int) (x + xLeftVel + xRightVel) + 6, (int) y + height / 2 - 12, width - 12, height - height / 2);
	}

	@Override
	public void attacked(double x) {
		if (!isHide)
			hide();
		else
			touched(x);
	}
}
