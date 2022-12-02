package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import audio.Audio;
import main.GameSettings;
import view.ImageLoader;

public class EnemyGoomba extends ObjectDynamic {

	private boolean isDie = false;

	public EnemyGoomba(int x, int y, int mapWidthBoundary) {
		super(x, y, mapWidthBoundary);
		width = height = GameSettings.scaledSize;
		xLeftVel = -2;
	}

	public void die() {
		isDie = true;
		hasCollision = false;
		hasGravity = false;
		initVelocity();

		frameCount = 0;
	}
	
	public void dieByKoopa(double x) {	// 거북이 등껍질에 의해 죽었을 때
		Audio.getInstance().play("smb_kick");
		hasCollision = false;
		height *= -1;	// 이미지 거꾸로 출력하기 위해
		y -= height;
		initVelocity();
		xLeftVel = x * 4;	// 등껍질 이동 방향에 맞춰서 날아가도록
		jump(10);
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

		return imageLoader.getGoombaCurrentImage(animationIdx);
	}

	private BufferedImage dyingAnimation() {
		frameCount++;
		if (frameCount >= 25) {
			destroy = true;
		}
		ImageLoader imageLoader = ImageLoader.getImageLoader();
		return imageLoader.getGoombaCurrentImage(2);
	}

	@Override
	public void move() {
		if (x + xLeftVel >= this.mapWidthBoundary - width || x + xLeftVel <= 0)
			changeDir();
		if (y >= 800)
			destroy = true;
	}

	@Override
	public BufferedImage getCurrentImage() {
		BufferedImage img = null;
		if (isDie)
			img = dyingAnimation();
		else
			img = movingAnimation(10);
		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getCurrentImage(), (int) x, (int) y, width, height, null);

		// HitBox 표시
		//g2.drawRect((int)(x + xLeftVel + xRightVel) + 5, (int) y + 12, width - 10, height - 25);
		//g2.drawRect((int) x + 8, (int) y + height - 7, width - 16, 5 + (int) yVel);
	}

	@Override
	public void attacked(double x) {
		if(x == -1 || x == 1)
			dieByKoopa(x);
		else
			die();
	}

	@Override
	public Rectangle getHitbox() {
		return new Rectangle((int)(x + xLeftVel + xRightVel) + 5, (int) y + 7, width - 10, height - 15);
	}
}
