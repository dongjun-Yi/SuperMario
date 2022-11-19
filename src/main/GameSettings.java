package main;

public class GameSettings {
	public static final int imageSize = 16; // 16x16 size
	public static final int scale = 3;
	public static final int scaledSize = imageSize * scale; // 48x48

	public static final int maxScreenCol = 16;
	public static final int maxScreenRow = 15;
	public static final int screenWidth = scaledSize * maxScreenCol;
	public static final int screenHeight = scaledSize * maxScreenRow - 24;

	public static final int maxPlayerCount = 2;
	public static final double gravity = 0.65;
}
