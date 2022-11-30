package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.GameSettings;
import view.ImageLoader;

public class GameMapFactory {
	private static GameMapFactory instance = new GameMapFactory(); // Singleton pattern

	private GameMapFactory() {
	}

	public static GameMapFactory getInstance() {
		return instance;
	}

	public GameMap createMap(long seedNumber) {
		
		Random r = new Random();
		r.setSeed(seedNumber);
		
		GameMap gameMap = new GameMap();
		BufferedImage mapData = ImageLoader.getImageLoader().getMapData();
		
		int mapWidthBoundary = mapData.getWidth() * GameSettings.scaledSize;
		
		int mario = (new Color(237, 28, 36)).getRGB();
		int luigi = (new Color(34, 177, 76)).getRGB();
		int goomba = (new Color(128, 64, 64)).getRGB();
		int koopa = (new Color(0, 128, 0)).getRGB();
		int brickBlock = (new Color(255, 127, 39)).getRGB();
		int itemBlock = (new Color(255, 201, 14)).getRGB();
		int hardBlock = (new Color(136, 0, 21)).getRGB();
		int pipe = (new Color(181, 230, 29)).getRGB();
		int flagPole = (new Color(0, 0, 0)).getRGB();
		
		for (int i = 0; i < mapData.getWidth(); i++) {
			for (int j = 0; j < mapData.getHeight(); j++) {
					int rgbInt = mapData.getRGB(i, j);
					
					if(rgbInt == mario) {
						Player p = new Player(i * GameSettings.scaledSize, j * GameSettings.scaledSize, mapWidthBoundary);
						p.setMario(true);
						gameMap.addObject(p);
					}
					else if (rgbInt == luigi) {
						gameMap.addObject(new Player(i * GameSettings.scaledSize, j * GameSettings.scaledSize, mapWidthBoundary));
					}
					else if (rgbInt == goomba) {
						gameMap.addObject(new EnemyGoomba(i * GameSettings.scaledSize, j * GameSettings.scaledSize, mapWidthBoundary));
					}
					else if (rgbInt == koopa) {
						EnemyKoopa ememyKoopa = new EnemyKoopa(i * GameSettings.scaledSize, j * GameSettings.scaledSize, mapWidthBoundary);
						gameMap.koopa = ememyKoopa;		// for Debugging
						gameMap.addObject(ememyKoopa);
					}
					else if (rgbInt == brickBlock) {
						gameMap.addObject(new BlockBrick(i * GameSettings.scaledSize, j * GameSettings.scaledSize));
					}
					else if (rgbInt == itemBlock) {
						int randItemNum = 2;
						if(r.nextInt(11) <= 3)
							randItemNum = 1;
						gameMap.addObject(new BlockItem(i * GameSettings.scaledSize, j * GameSettings.scaledSize, randItemNum));	// random
					}
					else if (rgbInt == hardBlock) {
						gameMap.addObject(new BlockHard(i * GameSettings.scaledSize, j * GameSettings.scaledSize));
					}
					else if (rgbInt == pipe) {
						gameMap.addObject(new BlockPipe(i * GameSettings.scaledSize, j * GameSettings.scaledSize));
					}
					else if (rgbInt == flagPole) {
						gameMap.setFlagPole(new FlagPole(i * GameSettings.scaledSize, j * GameSettings.scaledSize));
					}
			}
		}

		gameMap.setCamera(new GameCamera(mapWidthBoundary));
		
		return gameMap;
	}
}
