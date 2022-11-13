package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.GameSettings;
import view.ImageLoader;

public class GameMap {
	private BufferedImage background;
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private List<Player> players = new ArrayList<Player>();
	private GameCamera camera;

	public GameMap() {
		ImageLoader imageLoader = ImageLoader.getImageLoader();
		background = imageLoader.getBackgroundImage();
		for (int i = 0; i < GameSettings.maxPlayerCount; i++) {
			players.add(new Player(0, 0, background.getWidth()));
		}
		camera = new GameCamera(background.getWidth());
		gameObjects.add(new Goomba(100, 100, background.getWidth()));
		gameObjects.add(new Koopa(300, 100, background.getWidth()));
		gameObjects.add(new Block(100, 100, background.getWidth()));
		gameObjects.add(new GroundBlock(200, 100, background.getWidth()));
		gameObjects.add(new ItemBlock(300, 100, background.getWidth()));
		gameObjects.add(new Pipe(400, 100, background.getWidth()));

		gameObjects.add(new MushroomItem(500, 100, background.getWidth()));
		gameObjects.add(new CoinItem(550,100,background.getWidth()));
	}

	public GameCamera getCamera() {
		return camera;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public void drawPlayers(Graphics2D g2) {
		for (Player player : players) {
			player.draw(g2);
		}
	}

	public void drawGameObjects(Graphics2D g2) {
		for (GameObject go : gameObjects) {
			go.draw(g2);
		}
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, null);
		drawGameObjects(g2);
		drawPlayers(g2);
	}
}
