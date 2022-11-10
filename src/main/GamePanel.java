package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import controller.Controller;
import controller.OthersController;
import controller.PlayerController;
import model.GameMap;
import model.Player;
import view.ImageLoader;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private Thread gameThread;

	private Controller controller = new PlayerController(); 	  // 플레이어의 input값
	private Controller othersController = new OthersController(); // 다른 플레이어의 input data값

	private List<Player> players = new ArrayList<Player>();
	private int playerNumber = 0; // 서버한테 받은 클라이언트 번호

	private BufferedImage startScreenImage;
	private BufferedImage selectIconImage;
	private BufferedImage marioStartImage;
	private BufferedImage luigiStartImage;

	private int CURSOR_GAMESTART_LOCATION = 9;	// play 글씨 위치
	private int CURSOR_QUIT_LOCATION = 10; 		// quit
	private int row = CURSOR_GAMESTART_LOCATION;// 시작화면 선택창 표시하기 위한 행변

	// >>GameStatus 클래스로 캡슐화 예정<<
	private String gameStatus;
	private final String START_SCREEN = "startScreen";
	private final String GAME_START = "gameStart";

	private GameMap map = new GameMap();
	private ImageLoader imageLoader = ImageLoader.getImageLoader();

	public GamePanel() {
		this.setPreferredSize(new Dimension(GameSettings.screenWidth, GameSettings.screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(controller);
		this.setFocusable(true);
		this.gameStatus = START_SCREEN;

		this.startScreenImage = imageLoader.getStartScreenImage();
		this.selectIconImage = imageLoader.getMushroomImage();
		this.marioStartImage = imageLoader.getPlayerStartImage(true);
		this.luigiStartImage = imageLoader.getPlayerStartImage(false);
	}

	public void gameStart() {
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

		gameThread = new Thread(this);
		gameThread.start();
	}

	public void playersInputUpdate() {
		for (Player p : players) {
			p.inputUpdate();
		}
	}

	public void update() {
		if (gameStatus == START_SCREEN) {
			if (controller.getSpacePressed() && row == CURSOR_GAMESTART_LOCATION) {
				gameStatus = GAME_START;
			}
			if (controller.getDownPressed() == true && row == CURSOR_GAMESTART_LOCATION) {
				row = CURSOR_QUIT_LOCATION;
			}
			if (controller.getUpPressed() == true && row == CURSOR_QUIT_LOCATION) {
				row = CURSOR_GAMESTART_LOCATION;
			}
		}
		if (gameStatus == GAME_START) {
			playersInputUpdate();
		}
		repaint();
	}

	@Override
	public void run() {
		double fps = 60.0;
		double interval = 1000000000 / fps;
		double delta = 0;

		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;

		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / interval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			// draw every 1/60 second
			if (delta >= 1) {
				update();
				delta--;
			}

			if (timer >= 1000000000) {
				// 1 second
				timer = 0;
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		if (gameStatus == START_SCREEN) {
			drawStartScreen(g2);
		}
		if (gameStatus == GAME_START) {
			map.draw(g2);
		}
		g2.dispose();
	}

	public void drawStartScreen(Graphics g) {
		g.drawImage(startScreenImage, 0, 0, null); // 시작화면을 가운데로 맞추기 위해 x좌표 -80만큼
		g.drawImage(selectIconImage, 215, 48 * row + 15, GameSettings.tileSize, GameSettings.tileSize, null);
		g.drawImage(marioStartImage, 92, 48 * 12 , GameSettings.tileSize, GameSettings.tileSize, null);
		g.drawImage(luigiStartImage, 150, 48 * 12, GameSettings.tileSize, GameSettings.tileSize, null);
	}
}
