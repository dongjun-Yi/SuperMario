package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import controller.Controller;
import main.GameSettings;
import view.ImageLoader;

public class Player extends ObjectDynamic {
	private boolean isPlayer1 = false;

	private Controller controller = null;

	private boolean isDie = false;
	private boolean isAttacked = false;
	private boolean isSpeedup = false;
	
	private boolean controlBlocked = false;

	private double dx = 0, dy = 0; // attacked animation용 변수
	private long timer = 0;	// 아이템용 타이머

	public Player(double x, double y, int mapWidthBoundary) {
		super(x, y, mapWidthBoundary);
		width = height = GameSettings.scaledSize;
		setDefaultValues();
	}

	public boolean isPlayer1() {
		return isPlayer1;
	}

	public void setIsPlayer1(boolean isPlayer1) {
		this.isPlayer1 = isPlayer1;
		if (isPlayer1)
			x = 100.0; // players have different x coordinate
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setDefaultValues() {
		direction = jumpDir = 0;
		x = (isPlayer1) ? 100.0 : 200.0;
		y = 300.0;
		xRightVel = 0.0;
		xLeftVel = 0.0;
		yVel = 0.0;
		maxSpeed = 7;
		dx = dy = 0;
		isDie = false;
		hasCollision = true;
		controlBlocked = false;
		isAttacked = false;
	}

	public void jump(int power) {
		jumpDir = direction;
		isJump = true;
		yVel = -power;
	}

	public void die() {
		dx = dy = 0; // 밟히는 애니메이션 종료

		isDie = true;
		hasCollision = false;
		hasGravity = false;
		controlBlocked = true;
		initVelocity();

		frameCount = 0;
	}

	private void dyingAnimation() {
		frameCount++;
		if (y >= 800) {
			frameCount = 0;
			setDefaultValues(); // player respawn
		}
		if (frameCount == 20) {
			hasGravity = true;
			jump(14);
		}
	}

	public void startSpeedUp() {
		isSpeedup = true;
		maxSpeed = 10;
		timer = 0;
	}
	
	private void speedUp() {
		if(timer >= 90) {
			isSpeedup = false;
			maxSpeed = 7;
		}
		timer++;
	}
	
	@Override
	public void attacked(double x) {
		isAttacked = true;
		controlBlocked = true;
		frameCount = 0;
		dx = dy = 0;
	}

	private void attackedAnimation() {
		frameCount++;

		if (frameCount >= 20) {
			controlBlocked = false;
			isAttacked = false;
			dx = dy = 0;
		} else if (frameCount > 10) {
			dx += 2;
			dy -= 2;
		} else {
			dx -= 2;
			dy += 2;
		}
	}

	@Override
	public void move() {
		if (isSpeedup) {
			speedUp();
		}
		
		if (!controlBlocked) {
			if (controller.getRightPressed()) {
				direction = 0;

				xRightVel += 0.2;
				if (xRightVel >= maxSpeed)
					xRightVel = maxSpeed;
			} else {
				xRightVel -= 0.3;
				if (xRightVel <= 0.3)
					xRightVel = 0;
			}

			if (controller.getLeftPressed()) {
				direction = 1;

				xLeftVel -= 0.2;
				if (xLeftVel <= -maxSpeed)
					xLeftVel = -maxSpeed;
			} else {
				xLeftVel += 0.3;
				if (xLeftVel >= -0.3)
					xLeftVel = 0;
			}

			if (!isJump && controller.getUpPressed() && yVel <= 0) {
				jump(16);
			}

			if (controller.getSpacePressed()) {
				// attacked(0);
			}
		}
		// System.out.println("xRightVel: " + xRightVel);
		// System.out.println("xLeftVel: " + xLeftVel);
	}

	private BufferedImage getWalkAnimation(BufferedImage[][] marioImg, int direction, int speed) {
		BufferedImage img = marioImg[direction][animationIdx + 1];

		if (frameCount >= speed) {
			animationIdx++;
			frameCount = 0;
		}
		frameCount++;

		if (animationIdx >= 3)
			animationIdx = 0;

		return img;
	}

	@Override
	public BufferedImage getCurrentImage() {
		ImageLoader imageLoader = ImageLoader.getImageLoader();
		BufferedImage[][] marioImg = imageLoader.getPlayerImage(isPlayer1);
		BufferedImage img = marioImg[direction][0];

		// dying animation
		if (isDie) {
			img = imageLoader.getPlayerDie(isPlayer1);
			dyingAnimation();
		}
		// attacked animation
		else if (isAttacked) {
			if (isJump)
				img = marioImg[jumpDir][5]; // 점프 중 밟힘
			attackedAnimation();
		}
		// jumping image
		else if (isJump) {
			img = marioImg[jumpDir][5];
		}
		// standing image
		else if ((xRightVel + xLeftVel) == 0)
			img = marioImg[direction][0];
		// turn direction image
		else if ((xLeftVel <= -4.5 && xRightVel < -xLeftVel && direction == 0)
				|| (xRightVel >= 4.5 && -xLeftVel < xRightVel && direction == 1))
			img = marioImg[direction][4];
		// walking image
		else if ((xLeftVel < 0) || (xRightVel > 0)) {
			// animation speed is influenced by xVelocity
			int speed = (-xLeftVel > xRightVel) ? (int) -xLeftVel : (int) xRightVel;
			img = getWalkAnimation(marioImg, direction, 10 - speed);
		}

		return img;
	}

	@Override
	public void draw(Graphics2D g2) {
		// 애니메이션이 이루어질 때의 좌표 계산 고려
		int drawX = (int) (x + dx);
		int drawY = (int) (y + dy);
		int drawWidth = width + (int) (-2 * dx);
		int drawHeight = height - (int) dy;
		g2.drawImage(getCurrentImage(), drawX, drawY, drawWidth, drawHeight, null);

		// HitBox 표시
		// g2.drawRect((int) x + 8, (int) y + 5, width - 16, height - 7 - 5); // hitbox
		g2.drawRect((int) x + 8, (int) y + 5, width - 16, 5); // tophitbox
		g2.drawRect((int) (x + xLeftVel + xRightVel) + 8, (int) y + 10, width - 16, 31); // centerhitbox
		g2.drawRect((int) x + 8, (int) y + height - 7, width - 16, 5 + (int) yVel); // bottomhitbox
	}

	@Override
	public Rectangle getHitbox() {
		return new Rectangle((int) x + 8, (int) y + 5, width - 16, height - 7 - 5);
	}

	public Rectangle getTopHitbox() {
		return new Rectangle((int) x + 8, (int) y + 5, width - 16, 5);
	}

	public Rectangle getCenterHitbox() {
		return new Rectangle((int) (x + xLeftVel + xRightVel) + 8, (int) y + 10, width - 16, 32);
	}

	public Rectangle getBottomHitbox() {
		return new Rectangle((int) x + 8, (int) y + height - 7, width - 16, 5 + (int) yVel);
	}
}
