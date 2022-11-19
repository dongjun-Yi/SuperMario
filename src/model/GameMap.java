package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.GameSettings;
import view.ImageLoader;

public class GameMap {
	private BufferedImage background;
	private List<GameObject> deletedObjects = new ArrayList<GameObject>();
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
		players.get(0).setMario(true);	// 첫번째 플레이어 = mario
		
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

	public List<Player> getPlayers() {
		return players;
	}

	public List<ObjectDynamic> getObjectDynamic() {
		return objectDynamic;
	}

	public List<ObjectStatic> getObjectStatic() {
		return objectStatic;
	}
	
	public ObjectStatic createBlockedBlock(double x, double y) {
		return new BlockBlocked(x, y);
	}
	
	public ObjectDynamic createItem(int itemNum, double x, double y) {
		ObjectDynamic od = null;
		switch(itemNum) {
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

			p.setCollided(false);	// 현재 바닥과 충돌했는지 체크하는 변수

			Rectangle centerHitbox = p.getCenterHitbox();
			Rectangle TopHitbox = p.getTopHitbox();
			Rectangle BottomHitbox = p.getBottomHitbox();

			// 정적인 오브젝트 (블록 등)과의 충돌 처리
			for (int i = 0; i < objectStatic.size(); i++) {

				ObjectStatic os = objectStatic.get(i);
				Rectangle osHitbox = os.getHitbox();

				// 플레이어가 블록을 머리로 충돌했을 때
				if (TopHitbox.intersects(osHitbox)) {
					p.setY(os.getY() + os.getHeight());
					p.setyVel(GameSettings.gravity);

					int itemNum = os.touch();
					if (itemNum != 0) {
						objectStatic.set(i, createBlockedBlock(os.getX(), os.getY()));
						objectDynamic.add(createItem(itemNum, os.getX(), os.getY()));
					}
				}

				// 플레이어가 블록 위에 서 있을 때
				else if (BottomHitbox.intersects(osHitbox)) {
					p.landing(os.getY() + 3);
					p.setCollided(true);	// 현재 블록 바닥과 충돌함
				}

				// 플레이어가 블록 옆면에 충돌했을 때
				else if (centerHitbox.intersects(osHitbox)) {
					p.setxLeftVel(0);
					p.setxRightVel(0);
				}
			}
			
			// 한번도 블록과 충돌하지 않으면, 즉 공중에 떠있는 상태라면 
			if (!p.isCollided()) {
				p.setyGround(625.0);	// 기본 땅에 떨어지도록 yGround 설정
			}

			// 동적인 오브젝트 (적, 아이템 등)과의 충돌 처리
			for (ObjectDynamic od : objectDynamic) {
				Rectangle osHitbox = od.getHitbox();

				// 플레이어가 밟았을 때
				if (BottomHitbox.intersects(osHitbox)) {
					if (!od.isItem()) {
						p.setyVel(0);
						p.jump(11);
						od.attacked((int) p.getX());
					}
				}
				// 플레이어와 닿았을 때
				else if (centerHitbox.intersects(osHitbox)) {
					if (od.isItem()) {
						if(od instanceof ItemMushroom)	{
							p.startSpeedUp();
							od.setDestroy(true);
						}
					} else if (od instanceof EnemyKoopa && ((EnemyKoopa) od).isHide()	// 등껍질이 움직이지 않은 상태면 친다
							&& !((EnemyKoopa) od).isMoving()) {
						od.attacked((int) p.getX() + p.getWidth() / 2);
					} else {
						p.die();
					}
				}
			}

			// 플레이어 간의 충돌 처리
			for (Player pJ : players) {
				if (p == pJ || !pJ.hasCollision())
					continue;

				Rectangle pJTopHitbox = pJ.getTopHitbox();

				// 다른 플레이어를 밟았을 때
				if (BottomHitbox.intersects(pJTopHitbox)) {
					p.setyVel(0);
					p.jump(13);
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
			
			if(!od.hasCollision()) 
				continue;
			
			od.setCollided(false);
			Rectangle odHitbox = od.getHitbox();
			Rectangle odBottomHitbox = od.getBottomHitbox();
			
			for (ObjectStatic os : objectStatic) {
				Rectangle osHitbox = os.getHitbox();

				if (odHitbox.intersects(osHitbox)) {
					od.changeDir();
				}
				
				else if (odBottomHitbox.intersects(osHitbox)) {
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
			if(go.isDestroy()) {
				deletedObjects.add(go);
			}
		}
		for (GameObject go : objectStatic) {
			if(go.isDestroy()) {
				deletedObjects.add(go);
			}
		}
	}
	
	public void clearDeletedObjects() {
		if(deletedObjects.size() == 0)	return;
		
		for(GameObject go : deletedObjects) {
			if(go instanceof ObjectDynamic) {
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
