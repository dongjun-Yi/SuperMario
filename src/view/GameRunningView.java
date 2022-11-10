package view;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import main.GameSettings;
import model.GameMap;
import model.Player;

public class GameRunningView implements GameStatusView {

	private List<Player> players = new ArrayList<Player>();
	private GameMap map;
	
	public GameRunningView(Controller controller, Controller othersController, int playerNumber) {
		// 게임 첫 시작 -> 맵생성, 플레이어 설정
		map = new GameMap();
		players = map.getPlayers();

		// players settings (controller and 1p 2p)
		for (int i = 0; i < GameSettings.maxPlayerCount; i++) {
			Player p = players.get(i);
			if (i == playerNumber) {
				p.setController(controller);
				p.setIsPlayer1(true);
			} else {
				p.setController(othersController);
				p.setIsPlayer1(false);
			}
		}
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
