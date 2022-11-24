package view;

import java.awt.Color;
import java.awt.Graphics2D;

import audio.Audio;
import main.GameSettings;

public class GameLoseView implements GameStatusView{

	private Audio audio = Audio.getInstance();
	
	public GameLoseView() {
		audio.playBackground("smb_gameover");
	}
	
	@Override
	public void updates() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {	
		g.setColor(Color.black);
		g.fillRect(0, 0, GameSettings.screenWidth, GameSettings.screenHeight);
		g.setColor(Color.white);
		g.drawString("You Lose", 10, 50);
	}

}
