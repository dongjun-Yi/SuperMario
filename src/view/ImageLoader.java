package view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GameSettings;

public class ImageLoader {
	// singleton pattern
	private static ImageLoader imageLoader = new ImageLoader();
	private BufferedImage playerAllImg;
	private BufferedImage items;
	private BufferedImage enemies;
	private BufferedImage[][] marioImg; // first index = 0 : right, 1 : left
	private BufferedImage[][] luigiImg;
	private BufferedImage background;
	private BufferedImage startScreen;
	private BufferedImage blocksImg;

	private ImageLoader() {
		readImages();
		loadPlayerImage();
	}

	public static ImageLoader getImageLoader() {
		return imageLoader;
	}

	private void readImages() {
		try {
			startScreen = ImageIO.read(getClass().getResource("../images/startscreen.png"));
			playerAllImg = ImageIO.read(getClass().getResourceAsStream("/images/mario.png"));
			background = ImageIO.read(getClass().getResourceAsStream("/images/background.png"));
			items = ImageIO.read(getClass().getResourceAsStream("/images/items.png"));
			enemies = ImageIO.read(getClass().getResourceAsStream("/images/enemies.png"));
			blocksImg = ImageIO.read(getClass().getResourceAsStream("/images/blocks.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadPlayerImage() {
		marioImg = new BufferedImage[2][6];
		luigiImg = new BufferedImage[2][6];
		int width = GameSettings.imageSize, height = GameSettings.imageSize;

		// 18 34 50 66
		// 16 16 16

		// right image
		for (int i = 0; i < 6; i++) {
			marioImg[0][i] = playerAllImg.getSubimage(18 + (i * width), 42, width, height);
			luigiImg[0][i] = playerAllImg.getSubimage(18 + (i * width), 118 + 42, width, height);
		}

		// left image
		for (int i = 0; i < 6; i++) {
			marioImg[1][i] = playerAllImg.getSubimage(18 + (i * width), 101, width, height);
			luigiImg[1][i] = playerAllImg.getSubimage(18 + (i * width), 118 + 101, width, height);
		}
	}

	public BufferedImage getBackgroundImage() {
		return background;
	}

	public BufferedImage[][] getPlayerImage(boolean isPlayer1) {
		if (isPlayer1)
			return marioImg;
		else
			return luigiImg;
	}

	public BufferedImage getPlayerDie(boolean isPlayer1) {
		int width = GameSettings.imageSize, height = GameSettings.imageSize;
		if (isPlayer1)
			return playerAllImg.getSubimage(114, 42, width, height);
		else
			return playerAllImg.getSubimage(114, 118 + 42, width, height);
	}

	public BufferedImage getPlayerStartImage(boolean isPlayer1) {
		if (isPlayer1)
			return marioImg[0][0];
		else
			return luigiImg[0][0];
	}

	public BufferedImage getMushroomImage() {
		BufferedImage mushroom = items.getSubimage(184, 34, GameSettings.imageSize, GameSettings.imageSize);
		return mushroom;
	}

	public BufferedImage getStartScreenImage() {
		return startScreen;
	}

	public BufferedImage getGoombaCurrentImage(int frameCount) { // frameCount == 2이면 die image (x=60, y=0)
		int y = (frameCount == 2) ? 0 : 4;
		BufferedImage goomba = enemies.getSubimage(0 + 30 * frameCount, y, GameSettings.imageSize,
				GameSettings.imageSize);
		return goomba;
	}

	public BufferedImage getKoopaCurrentImage(int frameCount, int direction, int w, int h) {
		int xStart = (direction == 0) ? 210 : 180;
		int idxJump = (direction == 0) ? 30 : -30;
		BufferedImage koopa = enemies.getSubimage(xStart + idxJump * frameCount, 0, w, h);
		return koopa;
	}

	public BufferedImage getKoopaCarapaceImage() {
		BufferedImage carapace = enemies.getSubimage(360, 3, GameSettings.imageSize, GameSettings.imageSize);
		return carapace;
	}

	public BufferedImage getBlockImage() {
		BufferedImage block = blocksImg.getSubimage(1, 1, 48, 48);
		return block;
	}

	public BufferedImage getGroundBlockImage() {
		BufferedImage groundBlock = blocksImg.getSubimage(48, 48, 48, 48);
		return groundBlock;
	}

	public BufferedImage getItemBlockImage() {
		BufferedImage itemBlock = blocksImg.getSubimage(48, 1, 48, 48);
		return itemBlock;
	}

	public BufferedImage getPipeImage() {
		BufferedImage pipe = blocksImg.getSubimage(96, 1, 48 * 2, 48 * 2);
		return pipe;
	}
	
	public BufferedImage getCoinItemImage() {
		BufferedImage coin = items.getSubimage(3,95, GameSettings.imageSize, GameSettings.imageSize);
		return coin;
		
	}
}
