package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import controller.Controller;
import controller.OthersController;
import controller.PlayerController;
import model.GameMap;
import model.Player;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private Thread gameThread;
	
	private Controller controller = new PlayerController();			// myclient's input data
	private Controller othersController = new OthersController();	// another client's input data
	
	private List<Player> players = new ArrayList<Player>();
	private int playerNumber = 0;	// server will send this
	
	private GameMap map = new GameMap();
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(GameSettings.screenWidth, GameSettings.screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(controller);
		this.setFocusable(true);
	}
	
	public void gameStart() {
		players = map.getPlayers();
		
		// players settings (controller and 1p 2p)
		for(int i = 0; i < GameSettings.maxPlayerCount; i++) {
			Player p = players.get(i);
			if(i == playerNumber) {
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
		for(Player p : players) {
			p.inputUpdate();
		}
	}
	
	public void update() {
		playersInputUpdate();
		
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
        
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / interval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			// draw every 1/60 second 
			if(delta >= 1) {
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
		
		Graphics2D g2 = (Graphics2D)g;
		
		
		map.draw(g2);
		
		g2.dispose();
	}
}
