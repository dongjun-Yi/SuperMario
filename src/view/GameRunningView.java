package view;

import java.awt.Graphics2D;
import java.util.List;

import client.GameClient;
import controller.Controller;
import controller.PlayerController;
import main.GameSettings;
import model.GameCamera;
import model.GameMap;
import model.ObjectDynamic;
import model.ObjectStatic;
import model.Player;

public class GameRunningView implements GameStatusView {

	private List<Player> players;

	public List<Player> getPlayers() {
		return players;
	}

	private List<ObjectDynamic> objectDynamic;
	private List<ObjectStatic> objectStatic;
	private Player player1;
	private Player player2;
	private GameMap map;
	private GameCamera camera;

	private Controller controller;
	private PlayerController playerController;
	private GameClient gameClient;

	public GameRunningView(Controller controller, Controller othersController, int playerNumber,
			GameClient gameClient) {
		// 게임 첫 시작 -> 맵생성, 플레이어 설정
		map = new GameMap();
		players = map.getPlayers();
		camera = map.getCamera();
		objectDynamic = map.getObjectDynamic();
		objectStatic = map.getObjectStatic();

		this.controller = controller;
		this.playerController = (PlayerController) controller;
		this.gameClient = gameClient;

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
				player2 = p;
			}
		}
	}

	public void playersInputUpdate() {
		for (Player p : players) {
			p.move();
		}
	}

	public void objectDynamicUpdate() {
		for (ObjectDynamic od : objectDynamic) {
			od.move();
		}
	}

	public void cameraPositionUpdate() {
		camera.moveX(player1.getX() - GameSettings.scaledSize * 5); // 플레이어가 화면 왼쪽에서 6칸 이상을 넘어갈 때, 카메라를 이동시킴
	}

	public void playerInputSend() {
		gameClient.SendButtonAction(controller.getUpPressed(), controller.getDownPressed(), controller.getLeftPressed(),
				controller.getRightPressed(), controller.getSpacePressed());
	}

	@Override
	public void updates() {
		if (playerController.isKeyPressedVal() || playerController.isKeyReleasedVal()) {
			playerInputSend();
			playerController.setKeyReleasedVal(false);
		}
		playersInputUpdate();
		cameraPositionUpdate();
		objectDynamicUpdate();
	}

	@Override
	public void draw(Graphics2D g) {
		g.translate(-camera.getX(), 0); // 그릴 맵 정보들의 원점을 현재 카메라 x좌표만큼 왼쪽으로 이동
		map.draw(g); // 바뀐 원점에서 그려서 카메라가 이동하는 것처럼 보임
		g.translate(camera.getX(), 0); // 원점 원상복구
	}
}
