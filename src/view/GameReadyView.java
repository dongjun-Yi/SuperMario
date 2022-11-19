package view;

import java.awt.Color;
import java.awt.Graphics2D;

import client.GameClient;
import main.GameSettings;
import server.GameModelMsg;
import server.NetworkStatus;

public class GameReadyView implements GameStatusView {

	GameClient gameClient;
	
	public GameReadyView(GameClient gameClient) {
		this.gameClient = gameClient;
		
		GameModelMsg gameReadMsg = new GameModelMsg("player", NetworkStatus.GAME_READY);
		gameClient.SendObject(gameReadMsg);
	}
	
	@Override
	public void updates() {
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GameSettings.screenWidth, GameSettings.screenHeight);
		g.setColor(Color.white);
		g.drawString("Waiting Other Players..", 10, 50);
	}

}
