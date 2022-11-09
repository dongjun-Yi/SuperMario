package main;

public class GameSettings {
	public static final int originalTileSize = 16;	// 16x16 size
	public static final int scale = 3;
	
	public static final int tileSize = originalTileSize * scale;	// 48x48
	public static final int maxScreenCol = 16;
	public static final int maxScreenRow = 15;
	public static final int screenWidth = tileSize * maxScreenCol; 
	public static final int screenHeight = tileSize * maxScreenRow - 24;
	
	public static final int maxPlayerCount = 2;
}
