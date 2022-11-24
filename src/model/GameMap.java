package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import audio.Audio;
import main.GameSettings;
import view.ImageLoader;

public class GameMap {
	private BufferedImage background;
	private Vector<GameObject> deletedObjects = new Vector<GameObject>();
	private Vector<ObjectDynamic> objectDynamic = new Vector<ObjectDynamic>();
	private Vector<ObjectStatic> objectStatic = new Vector<ObjectStatic>();
	private Vector<Player> players = new Vector<Player>();
	private GameCamera camera;
	private Audio audio = Audio.getInstance();
	
	public GameMap() {
		ImageLoader imageLoader = ImageLoader.getImageLoader();
		background = imageLoader.getBackgroundImage();
		for (int i = 0; i < GameSettings.maxPlayerCount; i++) {
			players.add(new Player(0, 0, background.getWidth()));
		}
		players.get(0).setMario(true); // 첫번째 플레이어 = mario

		camera = new GameCamera(background.getWidth());
		objectDynamic.add(new EnemyGoomba(100, 500, background.getWidth()));
		objectDynamic.add(new EnemyKoopa(300, 300, background.getWidth()));

		objectDynamic.add(new ItemMushroom(500, 300, background.getWidth()));
		objectDynamic.add(new ItemCoin(550, 300, background.getWidth()));

		objectStatic.add(new BlockBrick(100, 500));
		objectStatic.add(new BlockHard(200, 529));
		objectStatic.add(new BlockItem(300, 300, 2));
		objectStatic.add(new BlockPipe(400, 300));
		objectStatic.add(new BlockBlocked(600, 300));

		objectStatic.add(new BlockHard(400, 577));
		objectStatic.add(new BlockHard(200, 577));
	}

	public GameCamera getCamera() {
		return camera;
	}

	public Vector<Player> getPlayers() {
		return players;
	}

	public Vector<ObjectDynamic> getObjectDynamic() {
		return objectDynamic;
	}

	public Vector<ObjectStatic> getObjectStatic() {
		return objectStatic;
	}

	public ObjectStatic createBlockedBlock(double x, double y) {
		return new BlockBlocked(x, y);
	}

	public ObjectDynamic createItem(int itemNum, double x, double y) {
		ObjectDynamic od = null;
		switch (itemNum) {
		case 1:
			od = new ItemMushroom(x, y, background.getWidth());
			break;

		case 2:
			od = new ItemCoin(x, y, background.getWidth());
			break;
		}
		return od;
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

	public void playerCollisionDetection() {

		for (Player p : players) {
			if (!p.hasCollision())
				continue;

			p.setCollided(false); // 현재 바닥과 충돌했는지 체크하는 변수

			// 정적인 오브젝트 (블록 등)과의 충돌 처리
			for (int i = 0; i < objectStatic.size(); i++) {

				ObjectStatic os = objectStatic.get(i);
				if (!p.isItInHitboxSpace(os.x, os.y)) // 플레이어 주변에 있을 때만 충돌 검사함
					continue;

				// 플레이어가 블록을 머리로 충돌했을 때
				if (p.getTopHitbox().intersects(os.getHitbox())) {
					p.setY(os.getY() + os.getHeight());
					p.setyVel(GameSettings.gravity);

					audio.play("smb_bump");
					int itemNum = os.touch();
					if (itemNum != 0) {
						objectStatic.set(i, createBlockedBlock(os.getX(), os.getY()));
						objectDynamic.add(createItem(itemNum, os.getX(), os.getY()));
					}
				}

				// 플레이어가 블록 위에 서 있을 때
				else if (p.getBottomHitbox().intersects(os.getHitbox())) {
					p.landing(os.getY() + 3);
					p.setCollided(true); // 현재 블록 바닥과 충돌함
				}

				// 플레이어가 블록 옆면에 충돌했을 때
				else if (p.getCenterHitbox().intersects(os.getHitbox())) {
					p.setxLeftVel(0);
					p.setxRightVel(0);
				}
			}

			// 한번도 블록과 충돌하지 않으면, 즉 공중에 떠있는 상태라면
			if (!p.isCollided()) {
				p.setyGround(625.0); // 기본 땅에 떨어지도록 yGround 설정
			}

			// 동적인 오브젝트 (적, 아이템 등)과의 충돌 처리
			for (ObjectDynamic od : objectDynamic) {

				if (!od.hasCollision() || !p.isItInHitboxSpace(od.x, od.y))
					continue;

				// 플레이어가 밟았을 때
				if (p.getBottomHitbox().intersects(od.getHitbox()) && !od.isItem()) {
					p.setyVel(0);
					if(od.getObjectNum() == 10)	// 쿠파(거북이)일 때
						p.kick(11);
					else
						p.stomp(11);
					od.attacked((int) p.getX());
				}
				// 플레이어와 닿았을 때
				else if (p.getCenterHitbox().intersects(od.getHitbox())) {
					switch (od.getObjectNum()) {
					case 1: // 버섯
						p.startSpeedUp();
						od.setDestroy(true);
						break;
					case 2: // 동전
						break;
					case 10: // 쿠파
						if (!od.isMoving()) {
							audio.play("smb_kick");
							od.attacked((int) p.getX() + p.getWidth() / 2);
						}
						else
							p.die();

						break;
					default: // 나머지
						p.die();
						break;
					}
				}
			}

			// 플레이어 간의 충돌 처리
			for (Player pJ : players) {
				if (p == pJ || !pJ.hasCollision())
					continue;

				// 다른 플레이어를 밟았을 때
				if (p.getBottomHitbox().intersects(pJ.getTopHitbox())) {
					p.setyVel(0);
					p.stomp(13);
					pJ.attacked(0);
				}
			}

			// 아이템 제거
			addDeletedObjects();
			clearDeletedObjects();
		}
	}

	public void objectDynamicCollisionDetection() {
		for (ObjectDynamic od : objectDynamic) {

			if (!od.hasCollision())
				continue;

			od.setCollided(false);

			for (ObjectStatic os : objectStatic) {

				if (!od.isItInHitboxSpace(os.x, os.y)) // 주변에 있을 때만 충돌 검사함
					continue;

				if (od.getHitbox().intersects(os.getHitbox())) {
					od.changeDir();
				}

				else if (od.getBottomHitbox().intersects(os.getHitbox())) {
					od.landing(os.getY() + 3);
					od.setCollided(true);
				}
			}

			if (!od.isCollided()) {
				od.setyGround(625.0);
			}
		}
	}

	public void dynamicObjectsUpdateCoordinate() {
		for (Player p : players) {
			p.updatesCoordinate();
		}
		for (ObjectDynamic od : objectDynamic) {
			od.updatesCoordinate();
		}
	}

	public void addDeletedObjects() {
		for (GameObject go : objectDynamic) {
			if (go.isDestroy()) {
				deletedObjects.add(go);
			}
		}
		for (GameObject go : objectStatic) {
			if (go.isDestroy()) {
				deletedObjects.add(go);
			}
		}
	}

	public void clearDeletedObjects() {
		if (deletedObjects.size() == 0)
			return;

		for (GameObject go : deletedObjects) {
			if (go instanceof ObjectDynamic) {
				objectDynamic.remove(go);
			} else {
				objectStatic.remove(go);
			}
		}
		deletedObjects.clear();
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
