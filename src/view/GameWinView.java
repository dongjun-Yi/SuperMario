package view;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GameSettings;

public class GameWinView implements GameStatusView{

	@Override
	public void updates() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GameSettings.screenWidth, GameSettings.screenHeight);
		g.setColor(Color.white);
		g.drawString("You Win", 10, 50);
	}

}
