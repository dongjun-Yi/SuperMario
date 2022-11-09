package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
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

	private Controller controller = new PlayerController(); // myclient's input data
	private Controller othersController = new OthersController(); // another client's input data

	private List<Player> players = new ArrayList<Player>();
	private int playerNumber = 0; // server will send this

	private BufferedImage startScreenImage;
	private BufferedImage selectIconImage;
	private BufferedImage marioStartImage;
	private BufferedImage luigiStartImage;

	private int CURSOR_GAMESTART_LOCATION = 9; // play 글씨 위치
	private int CURSOR_QUIT_LOCATION = 10; //
	private int row = CURSOR_GAMESTART_LOCATION; // 시작화면 선택창 표시하기 위한 행변

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

		try {
			this.startScreenImage = ImageIO.read(getClass().getResource("../images/startscreen.png"));
			this.selectIconImage = ImageIO.read(getClass().getResource("../images/select-icon.png"));
			this.marioStartImage = ImageIO.read(getClass().getResource("../images/startscreen-mario.png"));
			this.luigiStartImage = ImageIO.read(getClass().getResource("../images/luigi.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				p.setController(controller);
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
			repaint();

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
			drawStartScreen(g);
		}
		if (gameStatus == GAME_START) {
			map.draw(g2);
			g2.dispose();
		}

	}

	public void drawStartScreen(Graphics g) {
		g.drawImage(startScreenImage, 0, 0, null); // 시작화면을 가운데로 맞추기 위해 x좌표 -80만큼
		g.drawImage(selectIconImage, 192, 48 * row - 20, null);
		g.drawImage(marioStartImage, 96, 48 * 11 + 15, null);
		g.drawImage(luigiStartImage, 144, 48 * 11 + 15, null);
	}
}
