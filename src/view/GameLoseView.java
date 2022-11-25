package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import audio.Audio;
import controller.Controller;
import main.GamePanel;
import main.GameSettings;

public class GameLoseView implements GameStatusView {

	private String youLose = "YOU LOSE";
	private int fontSize = 15;
	private Font font = FontLoader.getInstance().loadMarioFont();
	private Audio audio = Audio.getInstance();

	private GamePanel gamePanel;
	private Controller controller;

	public GameLoseView(GamePanel gamePanel, Controller controller) {
		this.gamePanel = gamePanel;
		this.controller = controller;
		controller.initKey();
		audio.playBackground("smb_gameover");
	}

	@Override
	public void updates() {
		if (controller.getSpacePressed()) {
			gamePanel.gameRestart();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GameSettings.screenWidth, GameSettings.screenHeight);
		g.setColor(Color.white);
		g.setFont(font.deriveFont(20f));
		g.drawString(youLose, GameSettings.screenWidth / 2 - youLose.length() / 2 * fontSize,
				GameSettings.screenHeight / 2);
	}

}
