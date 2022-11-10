package view;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import model.GameMap;
import model.Player;

public class GameRunningView implements GameStatusView {

	private List<Player> players = new ArrayList<Player>();
	private GameMap map;
	
	public GameRunningView(GameMap map) {
		this.map = map;
		players = map.getPlayers();
	}
	
	public void playersInputUpdate() {
		for (Player p : players) {
			p.inputUpdate();
		}
	}
	
	@Override
	public void updates() {
		playersInputUpdate();
	}

	@Override
	public void draw(Graphics2D g) {
		map.draw(g);
	}

}
