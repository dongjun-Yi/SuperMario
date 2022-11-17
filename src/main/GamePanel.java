package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import client.GameClient;
import controller.Controller;
import controller.OthersController;
import controller.PlayerController;
import server.GameModelMsg;
import server.NetworkStatus;
import view.GameRunningView;
import view.GameStatusView;
import view.StartScreenView;

public class GamePanel extends JPanel implements Runnable {
	
	private static GamePanel instance = new GamePanel();
	
	private static final long serialVersionUID = 1L;
	private Thread gameThread;

	private Controller controller = new PlayerController(); 	  // 플레이어의 input값
	private Controller othersController = new OthersController(); // 다른 플레이어의 input data값

	private long timer = 0;
	private int playerNumber = 0; 			// 서버한테 받은 클라이언트 번호
	private GameStatusView gameStatusView;	// 시작화면, 인게임 화면 --> GameStatusView로 캡슐화
	private String gameStatus;
	
	private String ip_addr = "127.0.0.1";
	private String port = "30000";
	private GameClient c1;
	private int sendMsgCnt=0;
	private int sendMsgRunningCnt = 0;
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(GameSettings.screenWidth, GameSettings.screenHeight));
		this.setDoubleBuffered(true);
		this.addKeyListener(controller);
		this.setFocusable(true);
		// gameStatus == 시작 화면
		setGameStatusView(new StartScreenView(this, controller));
	}
	
	public long getTime() { return timer; }
	
	public void setGameStatusView(GameStatusView gameStatusView) {
		this.gameStatusView = gameStatusView;
	}
	
	public void gameStart() {
		gameThread = new Thread(this);
		gameThread.start();
		c1 = new GameClient("player", ip_addr, port);
	}

	public void gameRunning() {
		controller.initKey();
		// gameStatus == 게임 중
		setGameStatusView(new GameRunningView(controller, othersController, playerNumber));
	}
	
	public void update() {
		if(gameStatus == "startScreen") {
			setGameStatusView(new StartScreenView(this, controller));
		}
		if(gameStatus == "gameRunning" && sendMsgRunningCnt==0) { // 계속 그리는것을 방지하기 위해
			gameRunning();
			sendMsgRunningCnt++;
		}
		if(gameStatus == "gameReady" && sendMsgCnt==0) { //게임 준비 완료 메세지를 한번만 보내기 위해
			GameModelMsg gameReadMsg = new GameModelMsg("player", NetworkStatus.GAME_READY);
			c1.SendObject(gameReadMsg);
			sendMsgCnt++;
		}
		
		gameStatusView.updates();
		repaint();
	}

	@Override
	public void run() {
		double fps = 60.0;
		double interval = 1000000000 / fps;
		double delta = 0;

		long lastTime = System.nanoTime();
		long currentTime;
		

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
		gameStatusView.draw(g2);

		g2.dispose();
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}
	
	public static GamePanel getInstance() {
		return instance;
	}
}
