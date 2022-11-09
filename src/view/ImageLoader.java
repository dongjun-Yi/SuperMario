package view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GameSettings;

public class ImageLoader {
	// singleton pattern
	private static ImageLoader imageLoader = new ImageLoader();
	private BufferedImage playerAllImg;
	private BufferedImage[][] marioImg;	// first index = 0 : right, 1 : left
	private BufferedImage[][] luigiImg;
	private BufferedImage background;
	
	public ImageLoader() {
		readImages();
		loadPlayerImage();
	}
	
	public static ImageLoader getImageLoader() { return imageLoader; }
	
	private void readImages() {
		try {
			playerAllImg = ImageIO.read(getClass().getResourceAsStream("/images/mario.png"));
			background = ImageIO.read(getClass().getResourceAsStream("/images/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadPlayerImage() {
		marioImg = new BufferedImage[2][6];
		luigiImg = new BufferedImage[2][6];
		int width = GameSettings.originalTileSize, height = GameSettings.originalTileSize;
		
		// 18 34 50 66
		//  16 16 16
		
		// right image
		for(int i = 0; i < 6; i++) {
			marioImg[0][i] = playerAllImg.getSubimage(18 + (i * width), 42, width, height);
			luigiImg[0][i] = playerAllImg.getSubimage(18 + (i * width), 118 + 42, width, height);
		}
		
		// left image
		for(int i = 0; i < 6; i++) {
			marioImg[1][i] = playerAllImg.getSubimage(18 + (i * width), 101, width, height);
			luigiImg[1][i] = playerAllImg.getSubimage(18 + (i * width), 118 + 101, width, height);
		}
	}
	
	public BufferedImage getBackgroundImage() {
		return background;
	}
	
	public BufferedImage[][] getPlayerImage(boolean isPlayer1) {
		if(isPlayer1)
			return marioImg;
		else
			return luigiImg;
	}
	
	public BufferedImage getPlayerDie(boolean isPlayer1) {
		int width = GameSettings.originalTileSize, height = GameSettings.originalTileSize;
		if(isPlayer1)
			return playerAllImg.getSubimage(114, 42, width, height);
		else
			return playerAllImg.getSubimage(114, 118 + 42, width, height);
	}
	
	public BufferedImage getPlayerStartImage(boolean isPlayer1) {
		if(isPlayer1)
			return marioImg[0][0];
		else
			return luigiImg[0][0];
	}
	
	public BufferedImage getMushroomImage() {
		BufferedImage items = null;
		try {
			items = ImageIO.read(getClass().getResourceAsStream("/images/items.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedImage mushroom = items.getSubimage(184, 34, GameSettings.originalTileSize, GameSettings.originalTileSize);
		return mushroom;
	}
	
	public BufferedImage getStartScreenImage() {
		BufferedImage screen = null;
		try {
			screen = ImageIO.read(getClass().getResource("../images/startscreen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screen;
	}
}
