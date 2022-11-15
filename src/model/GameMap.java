package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.GameSettings;
import view.ImageLoader;

public class GameMap {
	private BufferedImage background;
	private List<ObjectDynamic> objectDynamic = new ArrayList<ObjectDynamic>();
	private List<ObjectStatic> objectStatic = new ArrayList<ObjectStatic>();
	private List<Player> players = new ArrayList<Player>();
	private GameCamera camera;

	public GameMap() {
		ImageLoader imageLoader = ImageLoader.getImageLoader();
		background = imageLoader.getBackgroundImage();
		for (int i = 0; i < GameSettings.maxPlayerCount; i++) {
			players.add(new Player(0, 0, background.getWidth()));
		}
		camera = new GameCamera(background.getWidth());
		objectDynamic.add(new EnemyGoomba(100, 300, background.getWidth()));
		objectDynamic.add(new EnemyKoopa(300, 300, background.getWidth()));
		
		objectDynamic.add(new ItemMushroom(500, 300, background.getWidth()));
		objectDynamic.add(new ItemCoin(550, 300, background.getWidth()));
		
		objectStatic.add(new BlockBrick(100, 300));
		objectStatic.add(new BlockHard(200, 300));
		objectStatic.add(new BlockItem(300, 300));
		objectStatic.add(new BlockPipe(400, 300));
		objectStatic.add(new BlockBlocked(600, 300));	
	}

	public GameCamera getCamera() {
		return camera;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<ObjectDynamic> getObjectDynamic() {
		return objectDynamic;
	}
	
	public List<ObjectStatic> getObjectStatic() {
		return objectStatic;
	}

	public void drawPlayers(Graphics2D g2) {
		for (Player player : players) {
			player.draw(g2);
		}
	}

	public void drawObjectDynamic(Graphics2D g2) {
		for (GameObject go : objectDynamic) {
			go.draw(g2);
		}
	}
	
	public void drawObjectStatic(Graphics2D g2) {
		for (GameObject go : objectStatic) {
			go.draw(g2);
		}
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, null);
		drawObjectDynamic(g2);
		drawObjectStatic(g2);
		drawPlayers(g2);
	}
}
