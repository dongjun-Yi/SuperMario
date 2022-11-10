package view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import controller.Controller;
import main.GamePanel;
import main.GameSettings;

public class StartScreenView implements GameStatusView {
	private ImageLoader imageLoader = ImageLoader.getImageLoader();
	
	private BufferedImage startScreenImage;
	private BufferedImage selectIconImage;
	private BufferedImage marioStartImage;
	private BufferedImage luigiStartImage;

	private int CURSOR_GAMESTART_LOCATION = 9;	// play 글씨 위치
	private int CURSOR_QUIT_LOCATION = 10; 		// quit
	private int row = CURSOR_GAMESTART_LOCATION;// 시작화면 선택창 표시하기 위한 행변
	
	private Controller controller;
	private GamePanel gamePanel;
	
	public StartScreenView(GamePanel gamePanel, Controller controller) {
		this.controller = controller;
		this.gamePanel = gamePanel;
		this.startScreenImage = imageLoader.getStartScreenImage();
		this.selectIconImage = imageLoader.getMushroomImage();
		this.marioStartImage = imageLoader.getPlayerStartImage(true);
		this.luigiStartImage = imageLoader.getPlayerStartImage(false);
	}
	
	@Override
	public void updates() {
		if (controller.getSpacePressed() && row == CURSOR_GAMESTART_LOCATION) {
			gamePanel.gameRunning();
		}
		if (controller.getDownPressed() == true && row == CURSOR_GAMESTART_LOCATION) {
			row = CURSOR_QUIT_LOCATION;
		}
		if (controller.getUpPressed() == true && row == CURSOR_QUIT_LOCATION) {
			row = CURSOR_GAMESTART_LOCATION;
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(startScreenImage, 0, 0, null); // 시작화면을 가운데로 맞추기 위해 x좌표 -80만큼
		g.drawImage(selectIconImage, 215, 48 * row + 15, GameSettings.tileSize, GameSettings.tileSize, null);
		g.drawImage(marioStartImage, 92, 48 * 12 , GameSettings.tileSize, GameSettings.tileSize, null);
		g.drawImage(luigiStartImage, 150, 48 * 12, GameSettings.tileSize, GameSettings.tileSize, null);
	}
}
