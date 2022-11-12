package view;

import java.awt.Graphics2D;
import java.util.List;

import controller.Controller;
import main.GameSettings;
import model.GameCamera;
import model.GameMap;
import model.Player;

public class GameRunningView implements GameStatusView {

	private List<Player> players;
	private Player player1;
	private GameMap map;
	private GameCamera camera;
	
	public GameRunningView(Controller controller, Controller othersController, int playerNumber) {
		// 게임 첫 시작 -> 맵생성, 플레이어 설정
		map = new GameMap();
		players = map.getPlayers();
		camera = map.getCamera();
		
		// players settings (controller and 1p 2p)
		for (int i = 0; i < GameSettings.maxPlayerCount; i++) {
			Player p = players.get(i);
			if (i == playerNumber) {
				p.setController(controller);
				p.setIsPlayer1(true);
				player1 = p;
			} else {
				p.setController(othersController);
				p.setIsPlayer1(false);
			}
		}
	}
	
	public void playersInputUpdate() {
		for (Player p : players) {
			p.move();
		}
	}
	
	public void cameraPositionUpdate() {
		camera.moveX(player1.getX() - GameSettings.scaledSize * 5);	// 플레이어가 화면 왼쪽에서 6칸 이상을 넘어갈 때, 카메라를 이동시킴
	}
	
	@Override
	public void updates() {
		playersInputUpdate();
		cameraPositionUpdate();
	}

	@Override
	public void draw(Graphics2D g) {
		g.translate(-camera.getX(), 0);	// 그릴 맵 정보들의 원점을 현재 카메라 x좌표만큼 왼쪽으로 이동
		map.draw(g);					// 바뀐 원점에서 그려서 카메라가 이동하는 것처럼 보임
		g.translate(camera.getX(), 0);	// 원점 원상복구
	}
}
