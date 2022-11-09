package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import view.ImageLoader;

public class GameMap {
	private BufferedImage background;
	private List<Player> players = new ArrayList<Player>();
	
	public GameMap() {
		ImageLoader imageLoader = ImageLoader.getImageLoader();
		background = imageLoader.getBackgroundImage();
		
		players.add(new Player());
		players.add(new Player());
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void drawPlayers(Graphics2D g2) {
		for(Player player : players) 
			player.draw(g2);
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, null);
		drawPlayers(g2);
	}
}
