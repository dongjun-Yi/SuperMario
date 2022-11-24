package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import audio.Audio;
import main.GameSettings;

public class GameWinView implements GameStatusView{

	private String youWin = "YOU WIN";
	private int fontSize = 15;
	private Font font = FontLoader.getInstance().loadMarioFont();
	private Audio audio = Audio.getInstance();
	
	public GameWinView() {
		audio.playBackground("smb_stage_clear");
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
		g.setFont(font.deriveFont(20f));
		g.drawString(youWin, GameSettings.screenWidth/2 - youWin.length()/2 * fontSize,  
				GameSettings.screenHeight/2);
	}
}
